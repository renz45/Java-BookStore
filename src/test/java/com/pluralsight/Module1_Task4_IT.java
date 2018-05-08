package com.pluralsight;

import static org.junit.Assert.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.invocation.Invocation;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockingDetails;

import java.lang.reflect.Method;
import java.io.*;

public class Module1_Task4_IT extends Mockito{

	static StringWriter stringWriter = new StringWriter();
	static String tempID = "0";

  @Mock
  private BookDAO mockBookDAO;

  @InjectMocks
  private ControllerServlet controllerServlet;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

		// Verify deleteBook() in ControllerServlet is complete
    @Test
    public void module1_task4() throws Exception {
       boolean called_getParameter = false;
       boolean called_sendRedirect = false;
       boolean called_deleteBook = false;
       HttpServletRequest request = mock(HttpServletRequest.class);
       HttpServletResponse response = mock(HttpServletResponse.class);

       when(request.getPathInfo()).thenReturn("/delete");
       when(request.getParameter("id")).thenReturn(tempID);

       try {
 				 controllerServlet.doGet(request, response);
       } catch (Exception e) {}

       try {
          verify(request, atLeast(1)).getParameter("id");
          called_getParameter = true;
       } catch (Throwable e) {}

			 String errorMsg = "In ControllerServlet deleteBook()," +
			 									" did not call getParameter(\"id\").";
			 assertTrue(errorMsg, called_getParameter);

			 Method method = null;
			 try {
          method =  BookDAO.class.getMethod("deleteBook", int.class);
       } catch (NoSuchMethodException e) {
          //e.printStackTrace();
       }

       String message = "The method deleteBook() doesn't exist in BookDAO.java.";
       assertNotNull(message, method);

			 MockingDetails mockingDetails = Mockito.mockingDetails(mockBookDAO);

			 Collection<Invocation> invocations = mockingDetails.getInvocations();

			 List<String> methodsCalled = new ArrayList<>();
			 for (Invocation anInvocation : invocations) {
			   methodsCalled.add(anInvocation.getMethod().getName());
			 }
			 assertTrue(methodsCalled.contains("deleteBook"));

       try {
          //verify(mockBookDAO).deleteBook(anyInt());
          //called_deleteBook = true;
          verify(response, atLeast(1)).sendRedirect("list");
          called_sendRedirect = true;
       } catch (Throwable e) {}


       // errorMsg = "In ControllerServlet deleteBook()," +
       //                   " did not call deleteBook(id).";
       // assertTrue(errorMsg, called_deleteBook);
       errorMsg = "In ControllerServlet deleteBook()," +
                         " did not call sendRedirect(\"list\").";
       assertTrue(errorMsg, called_sendRedirect);
    }
}
