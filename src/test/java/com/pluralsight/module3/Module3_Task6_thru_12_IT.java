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

import java.io.*;

public class Module3_Task6_thru_12_IT extends Mockito{

	static StringWriter stringWriter = new StringWriter();
	static String tempIDStr = "1";
  static int tempID = 1;
  static String tempTitle = "1984";
  static String tempAuthor = "George Orwell";
  static String tempPriceStr = "1.50";
  static float tempPrice = 1.50f;

	@Mock
  private BookDAO mockBookDAO;

  @InjectMocks
  private ControllerServlet controllerServlet;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

		// Verify updateBook() exists in ControllerServlet
    @Test
    public void module2_task6() throws Exception {
       boolean called_getId = false;
       boolean called_getTitle = false;
       boolean called_getAuthor = false;
       boolean called_getPrice = false;
       boolean called_updateBook = false;
       boolean called_sendRedirect = false;
       HttpServletRequest request = mock(HttpServletRequest.class);
       HttpServletResponse response = mock(HttpServletResponse.class);
       Book tempBook = new Book(tempID, tempTitle, tempAuthor, tempPrice);

       when(request.getPathInfo()).thenReturn("/update");
       when(request.getParameter("id")).thenReturn(tempIDStr);
       when(request.getParameter("booktitle")).thenReturn(tempTitle);
       when(request.getParameter("bookauthor")).thenReturn(tempAuthor);
       when(request.getParameter("bookprice")).thenReturn(tempPriceStr);

       try {
				controllerServlet.doGet(request, response);
				try {
           verify(request).getParameter("id");
           called_getId = true;
           verify(request).getParameter("booktitle");
           called_getTitle = true;
           verify(request).getParameter("bookauthor");
           called_getAuthor = true;
           verify(request).getParameter("bookprice");
           called_getPrice = true;
           verify(mockBookDAO).updateBook(any(Book.class));
           called_updateBook = true;
           verify(response).sendRedirect("list");
           called_sendRedirect = true;
        } catch (Throwable e) {}
       } catch (Exception e) {}

       String errorMsg = "After action \"" + "/update" +
                         "\", did not call getParameter(\"id\").";
       assertTrue(errorMsg, called_getId);
       errorMsg = "After action \"" + "/update" +
                         "\", did not call getParameter(\"booktitle\").";
       assertTrue(errorMsg, called_getTitle);
       errorMsg = "After action \"" + "/update" +
                         "\", did not call getParameter(\"bookauthor\").";
       assertTrue(errorMsg, called_getAuthor);
       errorMsg = "After action \"" + "/update" +
                         "\", did not call getParameter(\"bookprice\").";
       assertTrue(errorMsg, called_getPrice);
       errorMsg = "After action \"" + "/update" +
                         "\", did not udpateBook(newBookObject).";
       assertTrue(errorMsg, called_updateBook);
       errorMsg = "In ControllerServlet updateBook()," +
                         " did not call sendRedirect(\"list\").";
       assertTrue(errorMsg, called_sendRedirect);
    }
}
