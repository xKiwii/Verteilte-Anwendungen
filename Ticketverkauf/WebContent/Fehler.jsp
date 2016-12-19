<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kartenverkauf</title>
</head>
<body>
	<h2>Fehler</h2>
	<p>Die Operation konnte nicht ausgeführt werden.</p>
	<p>Ursache: <%=exception.getMessage() %> </p> 
	<a href="index.jsp">Zurück zur Hauptseite</a>
</body>
</html>

