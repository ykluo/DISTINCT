<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
    <h1>Introduction</h1>
    <p>This is an introduction paragraph...
    </p>
  </div>
  <div id="main">
  
    <p><b>Distinct People Repository</b> is a test people and articles repository based on 
    <a target="_blank" href="http://dblp.uni-trier.de/db/">DBLP Database</a>. All data are from DBLP XML file and can be download from 
    <a target="_blank" href="http://dblp.uni-trier.de/xml/">here</a>.
    </p>
    
    <h2>About DISTINCT Algorithm</h2>
    <p>Something about the algorithm...</p>
    <h2>About this Repository</h2>
    <p>Introductions about this website...</p>
    
  </div>
  <div id="footer"> &copy;2014 Distinct People Repository &nbsp;<span class="separator">|</span>&nbsp; Design by <a href="http://www.realitysoftware.ca">Reality Software</a> </div>
</div>
<div align=center>Website powered by <a href='http://all-free-download.com/free-website-templates/'>free website templates</a></div></body>
</html>