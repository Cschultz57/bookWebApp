//package edu.wctc.bookwebapp.model;
//
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.Vector;
//
///**
// *
// * @author Carson
// */
//public class MockAuthorDao implements IAuthorDao {
//
//    public MockAuthorDao() {
//       
//    }
//    
//    @Override
//    public List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException{
//        List<Author> list = Arrays.asList(
//        new Author(1, "John Snow", new Date()),
//        new Author(2, "Daenarys Targaryen", new Date())
//        );
//        
//        
//        return list;
//    }
//      @Override
//    public int deleteAuthorById(String tableName, String authorIdColName, Object authorId) throws ClassNotFoundException, SQLException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public int addNewAuthor(String tableName, List<String> colNames, List<Object> colValues) throws ClassNotFoundException, SQLException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public int updateAuthorById(String authorTableName, List<String> colNames, List<Object> colValues, String authorIdColName, Object authorId) throws SQLException, ClassNotFoundException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public Author retrieveAuthorById(String tableName, String idColName, String authorId) throws ClassNotFoundException, SQLException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//    
//
//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        IAuthorDao dao = new MockAuthorDao();
//        
//        List<Author> list = dao.getListOfAuthors();        
//        
//        for(Author a: list){
//            System.out.println(a.getAuthorId() + ", " + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
//        }
//    }
//
//  
//}
