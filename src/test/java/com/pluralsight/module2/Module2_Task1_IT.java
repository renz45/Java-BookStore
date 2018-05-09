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

import org.powermock.reflect.Whitebox;
import java.lang.reflect.Method;

import java.io.*;

public class Module2_Task1_IT {

	private ControllerServlet controllerServlet;

  // @Before
  // public void setUp() throws Exception {
  //   controllerServlet = new ControllerServlet();
  // }

		// Verify the showEditForm() method exists in ControllerServlet
    @Test
    public void module2_task1() throws Exception {
      Method method = null;
      try {
        method = Whitebox.getMethod(ControllerServlet.class,
                  "showEditForm", HttpServletRequest.class, HttpServletResponse.class);
      } catch (Exception e) {}

      String errorMsg = "private void showEditForm() does not exist in ControllerServlet";
      assertNotNull(errorMsg, method);
    }
}
