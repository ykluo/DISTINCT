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
  <div id="header"> <a href="/distinct/">Distinct People Repository</a> 
  	<a href="login.jsp"><img align="right" alt="Log in" src="img/login.png" height="40" width="80" style="margin-right: 30px"></a>
  </div>
  <div id="menu">
  	<form id="searchBox" method="get" action="author">
	<input type="text" maxlength="200" size="100" height="50px" name="search">
	<input id="searchBtn" type="image" value="search" name="SearchBtn" src="img/search_btn_up.jpg" alt="submit">
	</form> 
	&nbsp; &nbsp; &nbsp; &nbsp; <a href="/distinct">HOME</a> 
	&nbsp; &nbsp; &nbsp; &nbsp; <a href="request.jsp">REQUEST</a>
	&nbsp; &nbsp; &nbsp; &nbsp; <a href="contact.jsp">CONTACTS</a> 
	</div>
  
  <div id="sidebar">
    <h1>${author.getName() }</h1>
  </div>
  
  <div id="main">
  	<form id="author" method="get" action="paper">
  		<input type="hidden" id="req_type" name="type" value="">
  		<input type="hidden" id="element_id" name="id" value="">
  		<table id="side">
  			<thead>
  			<tr><th>Paper Published</th></tr>
  			</thead>
  			
  			<tbody>
  			<c:forEach var="pub" items="${publications }" varStatus="loop" >
  			<c:choose>
  				<c:when test="${loop.index % 2 == 1 }">
  				<tr class="d0">
  					<td onclick="document.getElementById('req_type').value='publication';document.getElementById('element_id').value='${pub.getKey()}';document.getElementById('author').submit()">${pub.getTitle() }</td>
  				</tr>
  				</c:when>
  				
  				<c:otherwise>
  				<tr class="d1">
  					<td onclick="document.getElementById('req_type').value='publication';document.getElementById('element_id').value='${pub.getKey()}';document.getElementById('author').submit()">${pub.getTitle() }</td>
  				</tr>
  				</c:otherwise>
  			</c:choose>
  			</c:forEach>
  			</tbody>
  		</table>
  	</form>
  </div>
  
  <div id="footer"> &copy;2014 Distinct People Repository &nbsp;<span class="separator">|</span>
  &nbsp; Design by <a href="http://www.realitysoftware.ca">Reality Software</a> 
  </div>
</div>
<div align=center>Website powered by <a href='http://all-free-download.com/free-website-templates/'>free website templates</a></div></body>


</html>