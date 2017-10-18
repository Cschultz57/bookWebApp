package edu.wctc.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author chris.roller
 */
public class AuthorService {

    private IAuthorDao authorDao;

    /**
     *
     * @param authorDao
     */
    public AuthorService(IAuthorDao authorDao) {
        this.authorDao = authorDao;

    }

    /**
     *
     * @param tableName
     * @param idColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public final Author retrieveAuthorById(String tableName, String idColName, String authorId) throws ClassNotFoundException, SQLException {
        return authorDao.retrieveAuthorById(tableName, idColName, authorId);
    }

    /**
     *
     * @param tableName
     * @param maxRecords
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public final List<Author> getListOfAuthors(String tableName, int maxRecords)
            throws ClassNotFoundException, SQLException {
        return authorDao.getListOfAuthors();
    }

    /**
     *
     * @param tableName
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public final int deleteAuthorById(String tableName, String authorIdColName,
            String authorId) throws ClassNotFoundException, SQLException {
        return authorDao.deleteAuthorById(tableName, authorIdColName, authorId);
    }

    /**
     *
     * @param author
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public final void addNewAuthor(Author author) throws ClassNotFoundException,
            SQLException {
        authorDao.addNewAuthor(author);
    }
    
    /**
     *
     * @param tableName
     * @param colNames
     * @param colValues
     * @param authorIdColName
     * @param authorId
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public final int updateAuthor(String tableName,List<String> colNames, List<Object> colValues, String authorIdColName, Object authorId) throws SQLException, ClassNotFoundException{
        return authorDao.updateAuthorById(tableName, colNames, colValues, authorIdColName, authorId);
}

    /**
     *
     * @return
     */
    public final IAuthorDao getAuthorDao() {
        return authorDao;
    }

    //needs validation

    /**
     *
     * @param authorDao
     */
    public final void setAuthorDao(IAuthorDao authorDao) {

        this.authorDao = authorDao;
    }

//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        AuthorService as = new AuthorService(new AuthorDao(new MySqlDbAccessor(), "com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book", "root", "admin"));
//        List<Author> authors = as.getAllAuthors("author", 50);
//        for (Author a : authors) {
//            System.out.println(a);
//        }
//    }
}
