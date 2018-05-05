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

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;

import java.io.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({BookDAO.class})

public class SubProject1_Task4_IT extends Mockito{

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

    @Test
    public void verify_completed_deletebook() throws Exception {
       boolean called_getParameter = false;
       boolean called_sendRedirect = false;
       boolean called_deleteBook = false;
       HttpServletRequest request = mock(HttpServletRequest.class);
       HttpServletResponse response = mock(HttpServletResponse.class);

       //Connection spyConnection = Mockito.mock(Connection.class);
       //DBConnection mockDBConnection = Mockito.mock(DBConnection.class);
       //PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
       //BookDAO mockBookDAO = Mockito.mock(BookDAO.class);
       //BookDAO bookDAO = new BookDAO(spyConnection);
       //BookDAO spyBookDAO = Mockito.spy(bookDAO);

			 //PowerMockito.whenNew(BookDAO.class).withArguments(mock, “msg”).thenReturn(spyBookDAO);
       when(request.getPathInfo()).thenReturn("/delete");
       when(request.getParameter("id")).thenReturn(tempID);

       try {
         //ControllerServlet controllerServlet = new ControllerServlet();
         //ControllerServlet spyControllerServlet = Mockito.spy(controllerServlet);
         //controllerServlet.init();
 				 controllerServlet.doGet(request, response);
       } catch (Exception e) {}

       try {
          verify(request, atLeast(1)).getParameter("id");
          called_getParameter = true;
       } catch (Throwable e) {}

       try {
          verify(mockBookDAO).deleteBook(anyInt());
          called_deleteBook = true;
          verify(response, atLeast(1)).sendRedirect("list");
          called_sendRedirect = true;
       } catch (Throwable e) {}

       String errorMsg = "In ControllerServlet deleteBook," +
                         " did not call getParameter(\"id\").";
       assertTrue(errorMsg, called_getParameter);
       errorMsg = "In ControllerServlet deleteBook," +
                         " did not call deleteBook(id).";
       assertTrue(errorMsg, called_deleteBook);
       errorMsg = "In ControllerServlet deleteBook," +
                         " did not call sendRedirect(\"list\").";
       assertTrue(errorMsg, called_sendRedirect);
    }
}
