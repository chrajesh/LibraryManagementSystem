/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controllers;

import com.library.dao.JDBCConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author bharathi
 */
public class TemplateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        RequestDispatcher header = null, footer = null, dummy = null, menus = null;
     
        ResultSet rs;
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            header = request.getRequestDispatcher("header.jsp");
            footer = request.getRequestDispatcher("footer.jsp");
            menus = request.getRequestDispatcher("menus.jsp");
            HttpSession session = request.getSession(false);

            dummy = request.getRequestDispatcher("dummy.jsp?username=" + session.getAttribute("username").toString());
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Library Management</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div>");
            header.include(request, response);
            out.println("</div>");
//            out.println("<div style=\"background-image:url('images/body6.jpg')\">");
            out.println("<div style=\"background-color:white\">");
            out.println("<div style='float:left;width:200px;padding-top:40px;padding-left:60px;' >");
            menus.include(request, response);
            out.println("</div>");
            out.println("<div style='float:rigt;padding-left:200px;'>");

            String requestParam = request.getParameter("pageId");
            System.out.println("request param........" + requestParam);
            if (requestParam != null) {
                try {
                    JDBCConnection instance = JDBCConnection.getInstance();
                    String query = "select * from pages where id in(" + requestParam + ")";
                    System.out.println(query);
                    rs = instance.executeQuery(query);
                    String role = null;
                    while (rs.next()) {
                        role = rs.getString("pagename");
                    }
                    if (role != null) {
                        dummy = request.getRequestDispatcher(role);
                        dummy.include(request, response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                dummy.include(request, response);
            }
            out.println("</div>");
            out.println("<div>");
            //footer.include(request, response);
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
