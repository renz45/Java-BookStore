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
public class Module2_Task5_IT {

    // Verify updateBook() method exists in BookDAO
    @Test
    public void module2_Task5() throws Exception {
      Method method = null;
      String sql = "UPDATE book SET title = ?, author = ?, price = ?" +
                   " WHERE book_id = ?";
      Connection spyConnection = Mockito.mock(Connection.class);
      PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
      BookDAO bookDAO = new BookDAO(spyConnection);
      BookDAO spyBookDAO = Mockito.spy(bookDAO);
      boolean called_prepareStatement = false;
      boolean called_setTitle = false;
      boolean called_setAuthor = false;
      boolean called_setPrice = false;
      boolean called_setId = false;
      boolean called_executeUpdate = false;
      boolean called_close = false;

      Mockito.when(spyConnection.prepareStatement(sql)).thenReturn(mockStatement);

      try {
         method =  BookDAO.class.getMethod("updateBook", Book.class);
      } catch (NoSuchMethodException e) {
         //e.printStackTrace();
      }

      String message = "The method updateBook() doesn't exist in BookDAO.java.";
      assertNotNull(message, method);

      Book tempBookObject = new Book(1, "1984", "George Orwell", 1.50f);
      try {
        method.invoke(spyBookDAO, tempBookObject);
      } catch (Exception e) {}

      try {
        Mockito.verify(spyConnection).prepareStatement(sql);
        called_prepareStatement = true;
        Mockito.verify(mockStatement).setString(1, "1984");
        called_setTitle = true;
        Mockito.verify(mockStatement).setString(2, "George Orwell");
        called_setAuthor = true;
        Mockito.verify(mockStatement).setFloat(3, 1.50f);
        called_setPrice = true;
        Mockito.verify(mockStatement).setInt(4, 1);
        called_setId = true;
        Mockito.verify(mockStatement).executeUpdate();
        called_executeUpdate = true;
        Mockito.verify(mockStatement).close();
        called_close = true;
      } catch (Throwable e) {}

      message = "The method updateBook() doesn't call prepareStatement() correctly.";
      assertTrue(message, called_prepareStatement);

      message = "The method updateBook() doesn't call setString() for the title.";
      assertTrue(message, called_setTitle);

      message = "The method updateBook() doesn't call setString() for the author.";
      assertTrue(message, called_setAuthor);

      message = "The method updateBook() doesn't call setFloat() for the price.";
      assertTrue(message, called_setPrice);

      message = "The method updateBook() doesn't call setInt() for the id.";
      assertTrue(message, called_setId);

      message = "The method updateBook() doesn't call executeUpdate().";
      assertTrue(message, called_executeUpdate);

      message = "The method updateBook() doesn't call PreparedStatement close().";
      assertTrue(message, called_close);
    }
}
