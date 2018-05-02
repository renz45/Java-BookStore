package com.pluralsight;

import static org.junit.Assert.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;

public class SubProject1_Task2_IT extends Mockito{

	static StringWriter stringWriter = new StringWriter();
	static String tempID = "1";

    // @Test
    // public void controllerservlet_newmethods_getid() throws Exception {
    //   verify_getid("/edit");
    //   verify_getid("/delete");
    // }
    @Test
    public void verify_edit_getid() throws Exception {
       boolean called_getParameter = false;
       HttpServletRequest request = mock(HttpServletRequest.class);
       HttpServletResponse response = mock(HttpServletResponse.class);

       when(request.getPathInfo()).thenReturn("/edit");
       when(request.getParameter("id")).thenReturn(tempID);

       try {
        new ControllerServlet().doGet(request, response);
       } catch (Exception e) {}

       try {
          verify(request, atLeast(1)).getParameter("id");
          called_getParameter = true;
       } catch (Throwable e) {}

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
        new ControllerServlet().doGet(request, response);
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
