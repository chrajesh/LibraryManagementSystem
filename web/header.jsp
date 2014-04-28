
<jsp:include page="allLinks.jsp"/>
<html>
    <head>
        <title>Library Management System</title>
    </head>
    <body>
        <div style="height:50px;background-color: pink;padding-top:10px;">
             <marquee  bgcolor='pink'><font color="blue" >*Library Management System*</font></marquee>
        </div>
   


<%
    Object username = session.getAttribute("username");
    if (username != null && username != "") {
        out.println("<div style=\"padding-top:10px;padding-right:10px;float:right\"><b>" + username.toString() + "</b><a href=\"Logout.jsp\">&nbsp;&nbsp;LOGOUT</a></div>");
    }

%>