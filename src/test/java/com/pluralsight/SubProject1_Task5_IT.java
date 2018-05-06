package com.pluralsight;

import static org.junit.Assert.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.io.*;

public class SubProject1_Task5_IT extends Mockito{

	static StringWriter stringWriter = new StringWriter();
	static String tempID = "1";
  static int tempIntID = 1;

  @Mock
  private BookDAO mockBookDAO;

  @InjectMocks
  private ControllerServlet controllerServlet;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

    @Test
    public void verify_completed_deletebook() throws Exception {
       boolean called_getParameter = false;
       boolean called_getBook = false;
       boolean called_getRequestDispatcher = false;
       boolean called_setAttribute = false;
       boolean called_forward = false;
       HttpServletRequest request = mock(HttpServletRequest.class);
       HttpServletResponse response = mock(HttpServletResponse.class);
       RequestDispatcher mockRequestDispatcher = mock(RequestDispatcher.class);
       Book mockBook = mock(Book.class);

       when(request.getPathInfo()).thenReturn("/edit");
       when(request.getParameter("id")).thenReturn(tempID);
       when(mockBookDAO.getBook(tempIntID)).thenReturn(mockBook);
       when(request.getRequestDispatcher("/BookForm.jsp"))
                    .thenReturn(mockRequestDispatcher);

       try {
 				 controllerServlet.doGet(request, response);
       } catch (Exception e) {}

       try {
          verify(request, atLeast(1)).getParameter("id");
          called_getParameter = true;
       } catch (Throwable e) {}

       try {
          verify(mockBookDAO).getBook(anyInt());
          called_getBook = true;
          verify(request).getRequestDispatcher("/BookForm.jsp");
          called_getRequestDispatcher = true;
          verify(request).setAttribute("book", mockBook);
          called_setAttribute = true;
          verify(mockRequestDispatcher).forward(request, response);
          called_forward = true;
       } catch (Throwable e) {}

       String errorMsg = "In ControllerServlet showEditForm()," +
                         " did not call getParameter(\"id\").";
       assertTrue(errorMsg, called_getParameter);
       errorMsg = "In ControllerServlet showEditForm()," +
                         " did not call getBook(id).";
       assertTrue(errorMsg, called_getBook);
       errorMsg = "In ControllerServlet showEditForm()," +
            " did not call request.getRequestDispatcher(\"BookForm.jsp\").";
       assertTrue(errorMsg, called_getRequestDispatcher);
       errorMsg = "In ControllerServlet showEditForm()," +
                         " did not call request.setAttribute(\"book\", bookObject);.";
       assertTrue(errorMsg, called_setAttribute);
       errorMsg = "In ControllerServlet showEditForm()," +
                         " did not call dispatcher.forward(request, response);.";
       assertTrue(errorMsg, called_forward);
    }
}
