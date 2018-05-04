package com.pluralsight;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;

import java.io.*;


@RunWith(PowerMockRunner.class)
@PrepareForTest({DriverManager.class, PreparedStatement.class, BookDAO.class})
public class SubProject1_Task3_IT {

    @Test
    public void deleteBook_method_exists() throws Exception {
      Method method = null;
      String sql = "DELETE FROM book WHERE id = ?";
      Class.forName("org.sqlite.JDBC");
      Connection spyConnection = Mockito.mock(Connection.class); //Mockito.spy(DriverManager.getConnection("jdbc:sqlite:book_store.db"));
      PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
      //PreparedStatement spyStatement = spy(spyConnection.prepareStatement(sql));
      BookDAO bookDAO = new BookDAO(spyConnection);
      BookDAO spyBookDAO = Mockito.spy(bookDAO);
      boolean called_setInt = false;
      boolean called_execute = false;
      boolean called_connect = false;
      boolean called_prepareStatement = false;

      //DriverManager.getConnection("jdbc:sqlite:book_store.db");
      //
      //PowerMockito.when(DriverManager.getConnection("jdbc:sqlite:book_store.db")).thenReturn(spyConnection);
      //PowerMockito.when(DriverManager.class, "getConnection",
      //                  "jdbc:sqlite:book_store.db").thenReturn(spyConnection);
      Mockito.when(spyConnection.prepareStatement(sql)).thenReturn(mockStatement);

      try {
         method =  BookDAO.class.getMethod("deleteBook", int.class);
      } catch (NoSuchMethodException e) {
         //e.printStackTrace();
      }

      String message = "The method deleteBook() doesn't exist in BookDAO.java.";
      assertNotNull(message, method);

      try {
        method.invoke(spyBookDAO, 0);
      } catch (Exception e) {}

      try {
        // Mockito.verify(spyBookDAO, Mockito.atLeast(1)).connect();
        // called_connect = true;
        Mockito.verify(spyConnection,Mockito.atLeast(1)).prepareStatement(sql);
        called_prepareStatement = true;
        Mockito.verify(mockStatement, Mockito.atLeast(1)).setInt(Mockito.anyInt(), Mockito.anyInt());
        called_setInt = true;
        Mockito.verify(mockStatement, Mockito.atLeast(1)).executeUpdate();
        called_execute = true;
      } catch (Throwable e) {}

      // message = "The method deleteBook() doesn't call connect().";
      // assertTrue(message, called_connect);

      message = "The method deleteBook() doesn't call prepareStatement().";
      assertTrue(message, called_prepareStatement);

      message = "The method deleteBook() doesn't call setInt().";
      assertTrue(message, called_setInt);

      message = "The method deleteBook() doesn't call executeUpdate().";
      assertTrue(message, called_execute);
    }
}
