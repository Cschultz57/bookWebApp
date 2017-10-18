package edu.wctc.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author Carson
 */
public class AuthorDao implements IAuthorDao {

    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private DataAccess db;

    private final String AUTHOR_DATE = "author_date";
    private final String AUTHOR_NAME = "author_name";
    private final String AUTHOR_TABLE = "authors";

    /**
     *
     * @param driverClass
     * @param url
     * @param userName
     * @param password
     * @param db
     */
    public AuthorDao(String driverClass, String url, String userName,
            String password, DataAccess db) {
        this.db = db;
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    /**
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException {
        List<Author> list = new Vector<>();
        List<Map<String, Object>> rawData
                = db.getAllRecords("author", 0);
        Author author = null;

        for (Map<String, Object> rec : rawData) {
            author = new Author();

            Object objRecId = rec.get("author_id");
            Integer recId = objRecId == null ? 0 : Integer.parseInt(objRecId.toString());
            author.setAuthorId(recId);

            Object objName = rec.get("author_name");
            String authorName = objName == null ? "" : objName.toString();
            author.setAuthorName(authorName);

            Object objRecAdded = rec.get("date_added");
            Date recAdded = objRecAdded == null ? null : (Date) objRecAdded;
            author.setDateAdded(recAdded);

            list.add(author);
        }
        return list;
    }

    /**
     *
     * @param tableName
     * @param idColName
     * @param authorId
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public Author retrieveAuthorById(String tableName, String idColName,
            String authorId) throws SQLException, ClassNotFoundException {
        db.openConnection();
        Map<String, Object> rec = db.getRecordById(tableName, idColName, authorId);
        Author author = new Author();
        Object obj = rec.get("author_id");
        Integer id = (Integer) obj;
        author.setAuthorId(id);

        Object objName = rec.get("author_name");
        String authorName = (objName != null) ? objName.toString() : "";
        author.setAuthorName(authorName);

        Object objDateAdded = rec.get("date_added");
        Date dateAdded = (objDateAdded != null) ? (Date) objDateAdded : null;
        author.setDateAdded(dateAdded);

        db.closeConnection();

        return author;
    }

    /**
     *
     * @param tableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public final int deleteAuthorById(String tableName, String authorIdColName,
            Object authorId) throws SQLException, ClassNotFoundException {
        db.openConnection();
        int recsDeleted = db.deleteById(tableName, authorIdColName, authorId);
        db.closeConnection();
        return recsDeleted;
    }

    /**
     *
     * @param authorTableName
     * @param colNames
     * @param colValues
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public final int updateAuthorById(String authorTableName, List<String> colNames,
            List<Object> colValues, String authorIdColName, Object authorId) throws SQLException, ClassNotFoundException {
        int authorRecordsUpdated = 0;
        db.openConnection();
        authorRecordsUpdated = db.updateById(userName, colNames, colValues, authorIdColName, authorId);
        db.closeConnection();

        return authorRecordsUpdated;
    }

    /**
     *
     * @param author
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public final void addNewAuthor(Author author) throws SQLException, ClassNotFoundException {
        if (author != null) {
            ArrayList<String> columnNames = new ArrayList<>();
            ArrayList<Object> values = new ArrayList<>();
            columnNames.add(AUTHOR_DATE);
            columnNames.add(AUTHOR_NAME);
            values.add(author.getDateAdded());
            values.add(author.getAuthorName());
            db.insertRecord(AUTHOR_TABLE, columnNames, values);
        }

//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        AuthorDao dao = new AuthorDao("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin",
//                new MySqlDataAccess("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/book", "root", "admin"));
//        List<Author> list = dao.getListOfAuthors();
//
//        for (Author a : list) {
//            System.out.println(a.getAuthorId() + ", " + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
//        }
//    }
    }
}
