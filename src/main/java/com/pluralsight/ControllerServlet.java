package com.pluralsight;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWorld
 */

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookDAO bookDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */

    public void init() {
    		bookDAO = new BookDAO();
    		bookDAO.connect();
				bookDAO.disconnect();
    }

    public ControllerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		String action = request.getPathInfo();

		try {
			if (action.equals("/admin")) {
				showBookAdmin(request, response);
			}
			else if (action.equals("/new")) {
				showNewForm(request, response);
			}
			else if (action.equals("/insert")) {
				insertBook(request, response);
			}
			else if (action.equals("/edit")) {
				showEditForm(request, response);
			}
			else if (action.equals("/delete")) {
				deleteBook(request, response);
			}
			else {
				listBooks(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showBookAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ServletException, IOException {
		ArrayList<Book> books_list = bookDAO.listAllBooks();

		request.setAttribute("books", books_list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/BookAdmin.jsp");
		dispatcher.forward(request, response);
	}

	private void listBooks(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ServletException, IOException {
		ArrayList<Book> books_list = bookDAO.listAllBooks();

		request.setAttribute("books", books_list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/BookList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/BookForm.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("This is the showEditForm() method.");
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("This is the deleteBook() method.");
	}

	private void insertBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		String title = request.getParameter("booktitle");
		String author = request.getParameter("bookauthor");
		String priceString = request.getParameter("bookprice");

		Book newBook = new Book(title, author, Float.parseFloat(priceString));

		bookDAO.insertBook(newBook);
		response.sendRedirect("list");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.println("This is the doPost() method!");
		doGet(request, response);

	}

}
