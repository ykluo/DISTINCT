<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DISTINCT Database</title>
<link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>


<body>
<div id="container">
  <div id="header"> <a href="/Distinct/">Distinct People Repository</a> </div>
  <div id="menu">
  	<form id="searchBox" method="get" action="author">
	<input type="text" maxlength="200" size="100" height="50px" name="Search">
	<input id="searchBtn" type="image" value="Search" name="SearchBtn" src="img/search_btn_up.jpg" alt="submit">
	</form> &nbsp; &nbsp; &nbsp; &nbsp; <a href="/Distinct">ABOUT US</a> 
	&nbsp; &nbsp; &nbsp; &nbsp; <a href="contact.jsp">CONTACTS</a> 
	</div>
  
  <div id="main">
  
  <table>
  	<thead>
	<tr>
		<th>ID</th>
		<th>Author</th>
		<th>Paper Title</th>
	</tr>
	</thead>
	
	<tbody>
	<c:forEach var="result" items="${results}" varStatus="loop">
	<c:choose>
		<c:when test="${loop.index % 2 == 1 }" >
		<tr class="d0">
			<td>${result[0]}</td>
			<td>${result[1]}</td>
			<td>${result[3]}</td>
		</tr>
		</c:when>
		
		<c:otherwise>
		<tr class="d1">
			<td>${result[0]}</td>
			<td>${result[1]}</td>
			<td>${result[3]}</td>
		</tr>
		</c:otherwise>
	</c:choose>
	</c:forEach>
	</tbody>
	
	</table>
  
  </div>
  <div id="footer"> &copy;2014 Distinct People Repository &nbsp;<span class="separator">|</span>
  &nbsp; Design by <a href="http://www.realitysoftware.ca">Reality Software</a> 
  </div>
</div>
<div align=center>Website powered by <a href='http://all-free-download.com/free-website-templates/'>free website templates</a></div></body>
</html>