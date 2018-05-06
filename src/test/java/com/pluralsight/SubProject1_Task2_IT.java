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

public class SubProject1_Task2_IT extends Mockito{

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
    public void verify_edit_getid() throws Exception {
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

    @Test
    public void verify_delete_getid() throws Exception {
       boolean called_getParameter = false;
       HttpServletRequest request = mock(HttpServletRequest.class);
       HttpServletResponse response = mock(HttpServletResponse.class);

       when(request.getPathInfo()).thenReturn("/delete");
       when(request.getParameter("id")).thenReturn(tempID);

       try {
				 //ControllerServlet controllerServlet = new ControllerServlet();
				 //controllerServlet.init();
 				 controllerServlet.doGet(request, response);
				 //controllerServlet.destroy();
       } catch (Exception e) {}

       try {
          verify(request, atLeast(1)).getParameter("id");
          called_getParameter = true;
       } catch (Throwable e) {}

       String errorMsg = "After action \"" + "/delete" +
                         "\", did not call getParameter(\"id\").";
       assertTrue(errorMsg, called_getParameter);
    }
}
