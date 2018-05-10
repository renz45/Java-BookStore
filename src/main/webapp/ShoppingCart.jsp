<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Book Store</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>

<jsp:useBean id="cart" scope="session" class="com.pluralsight.ShoppingCart" />
<body>
	<ul>
	  <li><a href="list">Book Listing</a></li>
    <li><a href="admin">Admin</a></li>
    <li><a class="active" href="showcart">Cart</a></li>
	</ul>

    <div class="container">
	    <div class="booktable">
	        <table border="1" cellpadding="5">
	            <caption>List of Books</caption>
	            <tr>
	                <th>Title</th>
	                <th>Author</th>
	                <th>Price</th>
                  <th>Quantity</th>
                  <th><a href="new">Add Book</a></th>
	            </tr>

      	 			<c:forEach items="${cart.cartItems}" var="cartItem">
                  <tr>
                      <td> ${ cartItem.getTitle() } </td>
                      <td> ${ cartItem.getAuthor() } </td>
                      <td> <fmt:formatNumber value = "${ cartItem.getPrice() }" type = "currency"/>  </td>
                      <td> ${ cartItem.getQuantity() } </td>
                      <td> <a href="#updatecart">Update</a>  <a href="#deletecart">Delete</a> </td>
                  </tr>
              </c:forEach>
	        </table>
	    </div>
    </div>
</body>
</html>
