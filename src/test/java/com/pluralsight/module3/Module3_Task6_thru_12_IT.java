package com.pluralsight;

import static org.junit.Assert.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.powermock.reflect.Whitebox;
import java.lang.reflect.Method;

import java.io.*;

public class Module3_Task6_thru_12_IT extends Mockito{

	static StringWriter stringWriter = new StringWriter();
	static String tempIDStr = "1";
  static int tempID = 1;
  static String tempTitle = "1984";
  static String tempAuthor = "George Orwell";
  static String tempPriceStr = "1.50";
  static float tempPrice = 1.50f;

	static boolean called_getId = false;
	static boolean called_getTitle = false;
	static boolean called_getAuthor = false;
	static boolean called_getPrice = false;
	static boolean called_updateBook = false;
	static boolean called_sendRedirect = false;
	static HttpServletRequest request;
	static HttpServletResponse response;
	static Book tempBook;

	@Mock
  private BookDAO mockBookDAO;

  @InjectMocks
  private ControllerServlet controllerServlet;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		tempBook = new Book(tempID, tempTitle, tempAuthor, tempPrice);

		when(request.getPathInfo()).thenReturn("/update");
		when(request.getParameter("id")).thenReturn(tempIDStr);
		when(request.getParameter("booktitle")).thenReturn(tempTitle);
		when(request.getParameter("bookauthor")).thenReturn(tempAuthor);
		when(request.getParameter("bookprice")).thenReturn(tempPriceStr);

		try {
		 controllerServlet.doGet(request, response);
		} catch (Exception e) {}
  }

		// Verify updateBook() exists in ControllerServlet
		@Test
		public void module1_task2() throws Exception {
			Method method = null;
			try {
				method = Whitebox.getMethod(ControllerServlet.class,
									"deleteBook", HttpServletRequest.class, HttpServletResponse.class);
			} catch (Exception e) {}

			String errorMsg = "private void deleteBook() does not exist in ControllerServlet";
			assertNotNull(errorMsg, method);
		}

    @Test
    public void module3_task7() throws Exception {
			try {
         verify(request).getParameter("id");
         called_getId = true;
       } catch (Exception e) {}

       String errorMsg = "After action \"" + "/update" +
                         "\", did not call getParameter(\"id\").";
       assertTrue(errorMsg, called_getId);
    }

		@Test
    public void module3_task8() throws Exception {
			try {
         verify(request).getParameter("booktitle");
         called_getTitle = true;
         verify(request).getParameter("bookauthor");
         called_getAuthor = true;
       } catch (Exception e) {}

       String errorMsg = "After action \"" + "/update" +
                         "\", did not call getParameter(\"booktitle\").";
       assertTrue(errorMsg, called_getTitle);
       errorMsg = "After action \"" + "/update" +
                         "\", did not call getParameter(\"bookauthor\").";
       assertTrue(errorMsg, called_getAuthor);
    }

		@Test
    public void module3_task9() throws Exception {
			try {
         verify(request).getParameter("bookprice");
         called_getPrice = true;
       } catch (Exception e) {}

       String errorMsg = "After action \"" + "/update" +
                         "\", did not call getParameter(\"bookprice\").";
       assertTrue(errorMsg, called_getPrice);
    }

		@Test
    public void module3_task10() throws Exception {
			try {
         verify(mockBookDAO).updateBook(any(Book.class));
         called_updateBook = true;
       } catch (Exception e) {}

       String errorMsg = "After action \"" + "/update" +
                         "\", did not udpateBook(newBookObject).";
       assertTrue(errorMsg, called_updateBook);
    }

		@Test
    public void module3_task11() throws Exception {
			try {
         verify(response).sendRedirect("list");
         called_sendRedirect = true;
       } catch (Exception e) {}

       String errorMsg = "In ControllerServlet updateBook()," +
                         " did not call sendRedirect(\"list\").";
       assertTrue(errorMsg, called_sendRedirect);
    }
}
