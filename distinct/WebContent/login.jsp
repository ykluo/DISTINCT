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

	<div id="main">
	<h2>Administrator Login:</h2>
	<form method="post" action="login">
            <center>
            <table>
                <thead>
                    <tr>
                        <th colspan="2">Login Here</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>User Name</td>
                        <td><input type="text" name="uname" value="" /></td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="pass" value="" /></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Login" /></td>
                        <td><input type="reset" value="Reset" /></td>
                    </tr>
                    <!-- <tr>
                        <td colspan="2">Yet Not Registered!! <a href="reg.jsp">Register Here</a></td>
                    </tr> -->
                </tbody>
            </table>
            </center>
        </form>
	</div>
  
  <div id="footer"> &copy;2014 Distinct People Repository &nbsp;<span class="separator">|</span>
  &nbsp; Design by <a href="http://www.realitysoftware.ca">Reality Software</a> 
  </div>
</div>
<div align=center>Website powered by <a href='http://all-free-download.com/free-website-templates/'>free website templates</a></div></body>


</html>