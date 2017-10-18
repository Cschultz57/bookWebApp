package edu.wctc.bookwebapp.controller;

import edu.wctc.bookwebapp.model.Author;
import edu.wctc.bookwebapp.model.AuthorDao;
import edu.wctc.bookwebapp.model.AuthorService;
import edu.wctc.bookwebapp.model.DataAccess;
import edu.wctc.bookwebapp.model.IAuthorDao;
import edu.wctc.bookwebapp.model.MySqlDataAccess;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Carson Schultz
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/authorController"})
public class AuthorController extends HttpServlet {

    /**
     *
     */
    public static final String ERROR = "ERROR: Invalid Parameter";
    private static String RESULTS_PAGE = "/authorList.jsp";
    private static String ERROR_PAGE = "/error.jsp";
    private static String LIST_ACTION = "authorList";
    private static String ACTION = "action";
    private static String EDIT = "edit";
    private static String DELETE = "delete";
    private static String ADD = "add";
    private final String AUTHOR_TABLE = "authors";
    private final String AUTHOR_NAME = "author_name";
    private final String AUTHOR_DATE = "author_date";
    private final String AUTHOR_ID_COL = "author_id";

    private int MAX_RECORDS = 50;

    private DataAccess db;
    private AuthorService as;

    private String driverClass;
    private String url;
    private String userName;
    private String password;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        db = new MySqlDataAccess(driverClass, url, userName, password);
        IAuthorDao dao = new AuthorDao(driverClass, url, userName, password, db);
        as = new AuthorService(dao);

        try {
            String action = request.getParameter(ACTION);
            if (action.equalsIgnoreCase(LIST_ACTION)) {
                GetAuthorListPage(request, response);
            } else if (action.equalsIgnoreCase(EDIT)) {
                EditAuthor(request, response);
            } else if (action.equalsIgnoreCase(DELETE)) {
                DeleteAuthorById(request, response);
            } else if (action.equalsIgnoreCase(ADD)) {
                AddNewAuthor(request, response);
            }
        } catch (Exception e) {
            RESULTS_PAGE = ERROR_PAGE;
            request.setAttribute(ERROR, e.getMessage());
        }
        RequestDispatcher view = request.getRequestDispatcher(RESULTS_PAGE);
        view.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void GetAuthorListPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            //fix me
            request.setAttribute("authors", as.getListOfAuthors(AUTHOR_TABLE, MAX_RECORDS));

        } catch (Exception e) {
            request.setAttribute(ERROR, e.getCause());
        }
    }

    private void EditAuthor(HttpServletRequest request, HttpServletResponse response) {
        try {
            String authorId = (request.getParameter("authorId"));
            Author authorToEdit = as.retrieveAuthorById(AUTHOR_TABLE, AUTHOR_ID_COL, authorId);
            authorToEdit.setAuthorName(request.getParameter("authorName" + authorId));
            DateFormat format = new SimpleDateFormat("MM-dd-yy");
            Date dateAdded = format.parse(request.getParameter("addedDate" + authorId));
            authorToEdit.setDateAdded(dateAdded);
            ArrayList<String> colNames = new ArrayList<>();
            ArrayList<Object> colValues = new ArrayList<>();
            colNames.add(AUTHOR_NAME);
            colNames.add(AUTHOR_DATE);
            colValues.add(authorToEdit.getAuthorName());
            colValues.add(dateAdded);
            as.updateAuthor(AUTHOR_TABLE, colNames, colValues, AUTHOR_ID_COL, authorId);
            request.setAttribute("authors", RefreshAuthorList());
        } catch (Exception e) {
            request.setAttribute(ERROR, e.getCause());
        }
    }

    private void DeleteAuthorById(HttpServletRequest request, HttpServletResponse response) {
        try {
            String authorId = (request.getParameter("authorId"));
            as.deleteAuthorById(AUTHOR_TABLE, AUTHOR_ID_COL, authorId);
            request.setAttribute("authors", RefreshAuthorList());
        } catch (Exception e) {
            request.setAttribute(ERROR, e.getCause());
        }
    }

    private void AddNewAuthor(HttpServletRequest request, HttpServletResponse response) {
        try {

            Author newAuthor = new Author();
            newAuthor.setAuthorName(request.getParameter("addAuthorName"));
            DateFormat format = new SimpleDateFormat("MM-dd-YYYY");
            Date dateAdded = format.parse(request.getParameter("addAuthorDate"));
            newAuthor.setDateAdded(dateAdded);

            as.addNewAuthor(newAuthor);
        } catch (Exception e) {
            request.setAttribute(ERROR, e.getCause());
        }
    }

    private List<Author> RefreshAuthorList() throws SQLException, ClassNotFoundException {
        return as.getListOfAuthors(AUTHOR_TABLE, MAX_RECORDS);
    }

    @Override
    public void init() throws ServletException {
        driverClass = getServletContext().getInitParameter("db.driver.class");
        url = getServletContext().getInitParameter("db.url");
        userName = getServletContext().getInitParameter("db.username");
        password = getServletContext().getInitParameter("db.password");
    }

}
