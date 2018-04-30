<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form name="LoginForm" action="LoginServlet" method="POST">
        Username:
        <input type="text" name="username" />
        Password:
        <input type="text" name="password" />
        <input type="submit" value="OK" />
    </form>
</body>
</html>
