package edu.wctc.bookwebapp.model;

//NOTES: validate getters and setters and add javadocs
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Vector;

public class MS_SqlServerDataAccess implements DataAccess {

    private final int ALL_RECORDS = 0;

    private Connection conn;
    private Statement stmt;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private ResultSet rs;
    private PreparedStatement preparedStatement;

    /**
     *
     * @param driverClass
     * @param url
     * @param userName
     * @param password
     */
    public MS_SqlServerDataAccess(String driverClass, String url,
            String userName, String password) {
       this.driverClass=driverClass;
       this.url=url;
       this.userName=userName;
       this.password=password;
    }

    /**
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public final void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    /**
     *
     * @throws SQLException
     */
    @Override
    public final void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

//    Returns records from a table. Requires an open connection.

    /**
     *
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public final List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> rawData = new Vector<>();
        String sql = "";

        if (maxRecords > ALL_RECORDS) {
            sql = "SELECT TOP " + maxRecords + " * from " + tableName;
        } else {
            sql = "SELECT * from " + tableName;
        }

        openConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        Map<String, Object> record = null;

        while (rs.next()) {
            record = new LinkedHashMap<>();
            for (int colNum = 1; colNum <= colCount; colNum++) {
                record.put(rsmd.getColumnName(colNum), rs.getObject(colNum));
            }
            rawData.add(record);
        }
        closeConnection();
        return rawData;
    }

    /**
     *
     * @param table
     * @param pkName
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public final Map<String, Object> getRecordById(String table, String pkName, Object id) throws SQLException {
        String sql = "SELECT * FROM " + table + " WHERE " + pkName + " = ? ";
        boolean results;

        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setObject(1, id);
        results = preparedStatement.execute(sql);

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        Map<String, Object> record = null;
        while (rs.next()) {
            record = new LinkedHashMap<>();
            for (int col = 1; col < colCount + 1; col++) {
                record.put(rsmd.getColumnName(col), rs.getObject(col));
            }
        }
        return record;
    }

    /**
     *
     * @param table
     * @param idColName
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public final int deleteById(String table, String idColName, Object id) throws SQLException {
        int recordsDeleted = 0;
        String sql = "DELETE FROM  " + table + " WHERE " + idColName + " = ?";

        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setObject(1, id);
        recordsDeleted = preparedStatement.executeUpdate();

        return recordsDeleted;
    }

    /**
     *
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     * @throws SQLException
     */
    @Override
    public final int insertRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException {
        String sql = "INSERT INTO " + tableName + " ";
        StringJoiner sj = new StringJoiner(",", "(", ")");
        int recordsInserted = 0;
        for (String col : colNames) {
            sj.add(col);
        }

        sql += sj.toString();
        sql += "VALUES ";
        sj = new StringJoiner(",", "(", ")");

        for (Object colValue : colValues) {
            sj.add("?");
        }

        sql += sj.toString();

        preparedStatement = conn.prepareStatement(sql);

        for (int i = 0; i < colValues.size(); i++) {
            preparedStatement.setObject(i + 1, colValues.get(i));
        }

        recordsInserted = preparedStatement.executeUpdate();

        return recordsInserted;

    }

    /**
     *
     * @param tableName
     * @param colNames
     * @param colValues
     * @param idColName
     * @param idColValue
     * @return
     * @throws SQLException
     */
    @Override
    public final int updateById(String tableName, List<String> colNames, List<Object> colValues, String idColName, Object idColValue) throws SQLException {

        int recordsUpdated = 0;

        String sql = "UPDATE " + tableName + " SET ";

        StringJoiner sj = new StringJoiner(",");

        for (String col : colNames) {
            sj.add(col + " = ?");
        }
        sql += sj.toString();

        sql += " WHERE " + idColName + " = " + " ? ";
        preparedStatement = conn.prepareStatement(sql);

        for (int i = 0; i < colNames.size(); i++) {
            preparedStatement.setObject(i + 1, colValues.get(i));
        }

        preparedStatement.setObject(colNames.size() + 1, idColValue);
        recordsUpdated = preparedStatement.executeUpdate();

        return recordsUpdated;
    }

   

//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        MS_SqlServerDataAccess db = new MS_SqlServerDataAccess("com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book", "root", "admin");
//        List<Map<String, Object>> list = db.getAllRecords("author", 0);
//
//        for (Map<String, Object> rec : list) {
//            System.out.println(rec);
//        }
//    }

}
