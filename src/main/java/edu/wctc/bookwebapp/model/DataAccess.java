package edu.wctc.bookwebapp.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public interface DataAccess {
    
    /**
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public abstract void openConnection() throws SQLException, ClassNotFoundException;
    
    /**
     *
     * @throws SQLException
     */
    public abstract void closeConnection() throws SQLException;

    /**
     *
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException, ClassNotFoundException;

    /**
     *
     * @param table
     * @param pkName
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Map<String, Object> getRecordById(String table, String pkName, Object id) throws SQLException, ClassNotFoundException;

    /**
     *
     * @param table
     * @param idColName
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int deleteById(String table, String idColName, Object id) throws SQLException, ClassNotFoundException;

    /**
     *
     * @param tableName
     * @param colNames
     * @param colValues
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int insertRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException, ClassNotFoundException;

    /**
     *
     * @param tableName
     * @param colNamesToSet
     * @param colValues
     * @param idColName
     * @param idColValue
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int updateById(String tableName, List<String> colNamesToSet, List<Object> colValues, String idColName, Object idColValue) throws SQLException, ClassNotFoundException;   

}
