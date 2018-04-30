package com.pluralsight;

import static org.junit.Assert.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;

public class LoginServletIT extends Mockito{

	static StringWriter stringWriter = new StringWriter();
	static String tempUsername = "me";
	static String tempPassword = "secret";
	static boolean got_ParameterUsername = false;
	static boolean got_ParameterPassword = false;
	static HttpServletRequest request;

    @BeforeClass
    public static void setup() throws Exception {
    	request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("username")).thenReturn(tempUsername);
        when(request.getParameter("password")).thenReturn(tempPassword);

        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new LoginServlet().doPost(request, response);


//        writer.flush(); // it may not have been flushed yet...
    }

    @Test
    public void loginservlet_gets_username() throws Exception {
    	try {
	        verify(request, atLeast(1)).getParameter("username"); // only if you want to verify username was called...
	        got_ParameterUsername = true;
        } catch (Throwable e) {}

        assertTrue("Did not call getParameter(\"username\").", got_ParameterUsername);
    }

    @Test
    public void loginservlet_displays_username_and_password() throws Exception {

        assertTrue("The username and password parameters were not set.",
        		stringWriter.toString().contains("username = " + tempUsername + ", password = " + tempPassword));
    }


    @Test
    public void loginservlet_gets_password() throws Exception {
    	try {
	        verify(request, atLeast(1)).getParameter("password");
	        got_ParameterPassword = true;
    	} catch (Throwable e) {}

        assertTrue("Did not call getParameter(\"password\").", got_ParameterPassword);
    }
}
