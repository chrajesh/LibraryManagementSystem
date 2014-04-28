<%-- 
    Document   : Logout
    Created on : Mar 26, 2014, 8:24:19 PM
    Author     : bharathi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body background="images/body6.jpg">
    <center>
        <%
            session.removeAttribute("username");
            session.removeAttribute("role");
            session.invalidate();

        %>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <font color="white">You are logged out successfully<br/><br/>
        <a href="LoginPage" style="text-decoration: none"><font color="white">click here</font></a> to login again</font>
    </center>
</body>
</html>
