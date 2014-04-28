
<%@page import="com.library.dao.JDBCConnection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%!            String username, password;
            Connection connection;
            Statement stmt;
            ResultSet rs;
            String query = null;
        %>
        <%

            JDBCConnection connection = JDBCConnection.getInstance();

            query = "select * from userspagesmapping where id=" + session.getAttribute("role").toString();
            System.out.println("query::::\n" + query);
            rs = connection.executeQuery(query);
            String pageids = "";
            while (rs.next()) {
                pageids += rs.getString("pageId") + ",";
            }
            pageids = pageids.substring(0, pageids.length() - 1);
            System.out.println("pageids........" + pageids);
            query = "select * from pages where id in(" + pageids + ")";
            System.out.println("query::::\n" + query);
            rs = connection.executeQuery(query);
            out.println(" <ul class=\"nav nav-pills\">");
            String requAtt = request.getParameter("pageId");
            String attach = "";
            if (requAtt != null) {
                attach = "class='active'";
            }
            while (rs.next()) {
                String pagename = rs.getString("pagename");
                String pageId = rs.getString("id");
                System.out.println("page name.." + pagename);
                if (pageId.equals(requAtt)) {
                    out.println(" <li " + attach + "><a href=\"TemplateServlet?pageId=" + pageId + "\" target=\"responsePage\">" + pagename.split("\\.")[0] + "</a></li>");
                }else{
                     out.println(" <li><a href=\"TemplateServlet?pageId=" + pageId + "\" target=\"responsePage\">" + pagename.split("\\.")[0] + "</a></li>");
                }

            }
            out.println("</ul>");

        %>
    </body>
</html>
