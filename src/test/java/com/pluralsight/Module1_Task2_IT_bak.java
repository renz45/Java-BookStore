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

public class Module1_Task2_IT_bak extends Mockito{

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

		// Verify the deleteBook() method exists in ControllerServlet
		// Since it's private need to verify the lines of code get called
		// through the /delete action in doGet()
    @Test
    public void module1_task2() throws Exception {
       boolean called_getParameter = false;
       HttpServletRequest request = mock(HttpServletRequest.class);
       HttpServletResponse response = mock(HttpServletResponse.class);

       when(request.getPathInfo()).thenReturn("/edit");
       when(request.getParameter("id")).thenReturn(tempID);

       try {
				controllerServlet.doGet(request, response);
				try {
           verify(request, atLeast(1)).getParameter("id");
           called_getParameter = true;
        } catch (Throwable e) {}
       } catch (Exception e) {}



       String errorMsg = "After action \"" + "/edit" +
                         "\", did not call getParameter(\"id\").";
       assertTrue(errorMsg, called_getParameter);
    }
}
