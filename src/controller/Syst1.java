package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by sergii on 12/24/16.
 */
public class Syst1 extends HttpServlet{

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //ServletContext context = config.getServletContext();
        //context.setAttribute("attr1","text for attr1");

        //BookDAO bookDAO = new BookDAOImpl();
        //List<Book> bookList = bookDAO.findAllBooks();
        //ServletContext context2 = config.getServletContext();
        //context2.setAttribute("bookList", bookList);
    }
    //public void init(ServletConfig config) throws ServletException {
        //super.init(config);
/*
        BookDAO bookDAO = new BookDAOImpl();
        List<Category> categoryList = bookDAO.findAllCategories();*/

        //BookDAO bookDAO = new BookDAOImpl();
        //List<Book> bookList = bookDAO.findAllBooks();
        //ServletContext context2 = config.getServletContext();
        //context2.setAttribute("bookList", bookList);
   // }
/*
    private void findAllBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Book> bookList = bookDAO.findAllBooks();
        request.setAttribute("bookList", bookList);
    }
*/

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setAttribute("attr1","text for attr1");
        //RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
        //requestDispatcher.forward(request, response);
    }
}
