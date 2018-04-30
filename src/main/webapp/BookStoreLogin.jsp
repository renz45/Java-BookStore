<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Book Store</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<ul>
	  <li><a href="list">Book Listing</a></li>
	  <li><a href="new">Add Book</a></li>
	  <li><a class="active" href="login">Login</a></li>
	</ul>

<div class="container">
            
	<form name="login_form" method="post" action="authenticate">
	<h2>Login</h2>
	  <p><label>Username:</label>
	  <input type="text" name="username" /></p>
	  <p><label>Password:</label>
	  <input type="password" name="password" /></p>
	  <p><input type="submit" value="Submit"></p>
	</form>
	</div>
</body>
</html>