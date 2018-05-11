package com.pluralsight;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.inject.Inject;
/**
 * Servlet implementation class HelloWorld
 */

public class CartController extends HttpServlet {
		private static final long serialVersionUID = 1L;
		private DBConnection dbConnection;

		@Inject
    private BookDAO bookDAO;

    public void init() {
			dbConnection = new DBConnection();
			bookDAO = new BookDAO(dbConnection.getConnection());
    }

		public void destroy() {
			dbConnection.disconnect();
		}

    public CartController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {
		String action = request.getPathInfo();

		try {
			switch(action) {
				case "/addcart":
					 addToCart(request);
           break;
        default:

           break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    response.sendRedirect("../ShoppingCart.jsp");
	}

  protected void addToCart(HttpServletRequest request) {
   HttpSession session = request.getSession();
   String idStr = request.getParameter("id");
   int id = Integer.parseInt(idStr);
   String quantityStr = request.getParameter("quantity");
   int quantity = Integer.parseInt(quantityStr);

   Book existingBook = bookDAO.getBook(id);
   //String strQuantity = request.getParameter("quantity");

   ShoppingCart cartBean = null;
   Object objCartBean = session.getAttribute("cart");

   if(objCartBean!=null) {
    cartBean = (ShoppingCart) objCartBean ;
   } else {
    cartBean = new ShoppingCart();
    session.setAttribute("cart", cartBean);
   }

   cartBean.addCartItem(existingBook, quantity);
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
