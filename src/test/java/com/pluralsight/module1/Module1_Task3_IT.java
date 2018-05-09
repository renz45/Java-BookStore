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

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.exceptions.*;

import java.io.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ControllerServlet.class)
public class Module1_Task3_IT extends Mockito{
    static String tempID = "0";

		// Verify the deleteBook() method exists in ControllerServlet
		// Since it's private need to verify the lines of code get called
		// through the /delete action in doGet()
    @Test
    public void module1_task3() throws Exception {
			 ControllerServlet controllerServlet = PowerMockito.spy(new ControllerServlet());
       boolean called_deleteBook = false;
       HttpServletRequest request = mock(HttpServletRequest.class);
       HttpServletResponse response = mock(HttpServletResponse.class);

       try {
         when(request.getPathInfo()).thenReturn("/delete");
         //PowerMockito.doNothing().when(controllerServlet, "deleteBook", request, response);
         when(request.getParameter("id")).thenReturn(tempID);
       } catch (MethodNotFoundException e) {}

       // try {
				// controllerServlet.doGet(request, response);
				// try {
       //     PowerMockito.verifyPrivate(controllerServlet)
       //                 .invoke("deleteBook", request, response);
       //     called_deleteBook = true;
       //  } catch (Throwable e) {}
       // } catch (Exception e) {}

       String errorMsg = "After action \"" + "/delete" +
                         "\", did not call deleteBook().";
       assertTrue(errorMsg, called_deleteBook);
    }
}
