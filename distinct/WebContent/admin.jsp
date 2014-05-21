<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.distinct.database.*" %>
<%@ page import="java.util.List" %>
<%
	List<String[]> ls = DBConnect.getAuthors();
%>
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

	<div id="main">
	<form id="admin" method="post" action="confirm">
		<table>
			<thead>
				<tr>
					<th>Person 1 ID</th>
					<th>Person 2 ID</th>
					<th>Person 1 Name</th>
					<th>Person 2 Name</th>
					<th>Decision</th>
				</tr>
			</thead>
			<tbody>
				<%if (ls != null) {
				for (int i = 0; i < ls.size(); i ++) { 
					if (i % 2 == 0) {
				%>
					<tr class="d0">
						<td><%=ls.get(i)[0] %></td>
						<td><%=DBConnect.searchAuthorName(ls.get(i)[0]) %></td>
						<td><%=ls.get(i)[1] %></td>
						<td><%=DBConnect.searchAuthorName(ls.get(i)[1]) %></td>
						<td><input type="checkbox" name=<%=ls.get(i)[2] %> value="confirm"><label>Confirm</label>
						<input type="checkbox" name=<%=ls.get(i)[2] %> value="decline"><label>Decline</label>
						<input type="checkbox" name=<%=ls.get(i)[2] %> value="ignore"><label>Ignore</label></td>
					</tr>
				<%} else { %>
					
					<tr class="d1">
						<td><%=ls.get(i)[0] %></td>
						<td><%=DBConnect.searchAuthorName(ls.get(i)[0]) %></td>
						<td><%=ls.get(i)[1] %></td>
						<td><%=DBConnect.searchAuthorName(ls.get(i)[1]) %></td>
						<td><input type="checkbox" name=<%=ls.get(i)[2] %> value="confirm"><label>Confirm</label>
						<input type="checkbox" name=<%=ls.get(i)[2] %> value="decline"><label>Decline</label>
						<input type="checkbox" name=<%=ls.get(i)[2] %> value="ignore"><label>Ignore</label></td>
					</tr>
				
				<%} 
				}
				}%>
			</tbody>
		
		</table>
		<input type="submit" value="Submit">
		<input type="reset" value="Reset">
	
	</form>
	</div>
  
  <div id="footer"> &copy;2014 Distinct People Repository &nbsp;<span class="separator">|</span>
  &nbsp; Design by <a href="http://www.realitysoftware.ca">Reality Software</a> 
  </div>
</div>
<div align=center>Website powered by <a href='http://all-free-download.com/free-website-templates/'>free website templates</a></div></body>


</html>