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

@WebServlet(name = "AuthorController", urlPatterns = {"/authorController"})
public class AuthorController extends HttpServlet {

    /**
     *
     */
    public static final String ERROR = "ERROR: Invalid Parameter";
    private static String RESULTS_PAGE = "/authorList.jsp";
    private static String ERROR_PAGE = "/error.jsp";
    private static String HOME_PAGE = "/index.jsp";
    private static String ADD_EDIT_PAGE = "/addEditPage.jsp";
    private static String LIST_ACTION = "authorList";
    private static String HOME_ACTION = "home";
    private static String ACTION = "action";
    private static String EDIT = "edit";
    private static String DELETE = "delete";
    private static String ADD = "add";
    private static String ADD_EDIT_ACTION = "addEdit";
    private final String AUTHOR_TABLE = "authors";
    private final String AUTHOR_NAME = "author_name";
    private final String AUTHOR_DATE = "author_date";
    private final String AUTHOR_ID_COL = "author_id";
    private final String CHECK_BOX = "authorIdChk";

    private int MAX_RECORDS = 50;

    private DataAccess db;
    private AuthorService as;

    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/book";
    private String userName = "root";
    private String password = "admin";

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
        try {
            db = new MySqlDataAccess(driver, url, userName, password);
            IAuthorDao dao = new AuthorDao(driver, url, userName, password, db);
            as = new AuthorService(dao);

            String action = request.getParameter(ACTION);
            if (action.equalsIgnoreCase(LIST_ACTION)) {
                getAuthorList(request, response);
            } else if (action.equalsIgnoreCase(HOME_ACTION)) {
                getHomePage(request, response);
            } else if (action.equalsIgnoreCase(ADD_EDIT_ACTION)) {
                getAddEditPage(request, response);
            } else if (action.equalsIgnoreCase(EDIT)) {
                editAuthor(request, response);
            } else if (action.equalsIgnoreCase(DELETE)) {
                deleteAuthorById(request, response);
            } else if (action.equalsIgnoreCase(ADD)) {
                addNewAuthor(request, response);
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

    private void getAuthorList(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("authorList", as.getListOfAuthors("author", MAX_RECORDS));
        } catch (Exception e) {
            request.setAttribute(ERROR, e.getCause());
        }
    }

    private void getHomePage(HttpServletRequest request, HttpServletResponse response) {
        try {
            RESULTS_PAGE = HOME_PAGE;
        } catch (Exception e) {
            request.setAttribute(ERROR, e.getCause());
        }
    }

    private void getAddEditPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            RESULTS_PAGE = ADD_EDIT_PAGE;
        } catch (Exception e) {
            request.setAttribute(ERROR, e.getCause());
        }
    }

    private void editAuthor(HttpServletRequest request, HttpServletResponse response) {
        try {
            RESULTS_PAGE = ADD_EDIT_PAGE;
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
            refreshAuthorList(request);
        } catch (Exception e) {
            request.setAttribute(ERROR, e.getCause());
        }
    }

    private void deleteAuthorById(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] authorIds = request.getParameterValues(CHECK_BOX);
            if (authorIds != null) {
                for (String id : authorIds) {
                    as.deleteAuthorById(AUTHOR_TABLE, AUTHOR_ID_COL, id);
                }
            }
            refreshAuthorList(request);
        } catch (Exception e) {
            request.setAttribute(ERROR, e.getCause());
        }
    }

    private void addNewAuthor(HttpServletRequest request, HttpServletResponse response) {
        try {
            RESULTS_PAGE = ADD_EDIT_PAGE;
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

    private void refreshAuthorList(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        List<Author> authors = as.getListOfAuthors(AUTHOR_TABLE, MAX_RECORDS);
        request.setAttribute("authorList", authors);
    }

}
