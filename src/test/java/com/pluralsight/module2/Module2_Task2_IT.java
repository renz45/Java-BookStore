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
public class Module2_Task2_IT extends Mockito{
    static String tempID = "0";

		// Verify the showEditForm() method exists in ControllerServlet
		// Since it's private need to verify the lines of code get called
		// through the /delete action in doGet()
    @Test
    public void module1_task2() throws Exception {
			 ControllerServlet controllerServlet = PowerMockito.spy(new ControllerServlet());
       boolean called_showEditForm = false;
       HttpServletRequest request = mock(HttpServletRequest.class);
       HttpServletResponse response = mock(HttpServletResponse.class);

       try {
         when(request.getPathInfo()).thenReturn("/edit");
         //PowerMockito.doNothing().when(controllerServlet, "showEditForm", request, response);
         when(request.getParameter("id")).thenReturn(tempID);
       } catch (MethodNotFoundException e) {}

       // try {
				// controllerServlet.doGet(request, response);
				// try {
       //     PowerMockito.verifyPrivate(controllerServlet)
       //                 .invoke("showEditForm", request, response);
       //     called_showEditForm = true;
       //  } catch (Throwable e) {}
       // } catch (Exception e) {}

       String errorMsg = "After action \"" + "/edit" +
                         "\", did not call showEditForm().";
       assertTrue(errorMsg, called_showEditForm);
    }
}
