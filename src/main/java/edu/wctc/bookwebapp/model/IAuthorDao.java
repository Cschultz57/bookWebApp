
package edu.wctc.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Carson
 */
public interface IAuthorDao {

    /**
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public abstract List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException;

    /**
     *
     * @param tableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public abstract int deleteAuthorById(String tableName, String authorIdColName, Object authorId) throws ClassNotFoundException, SQLException;

    /**
     *
     * @param author
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public abstract void addNewAuthor(Author author) throws ClassNotFoundException, SQLException;

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
    public abstract int updateAuthorById(String authorTableName, List<String> colNames, List<Object> colValues, String authorIdColName, Object authorId) throws SQLException, ClassNotFoundException;

    /**
     *
     * @param tableName
     * @param idColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public abstract Author retrieveAuthorById(String tableName, String idColName, String authorId) throws ClassNotFoundException, SQLException;
}
