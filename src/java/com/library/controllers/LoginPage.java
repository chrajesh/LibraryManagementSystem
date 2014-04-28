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
public class LoginPage extends HttpServlet {

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
        RequestDispatcher forwardPage = null, header = null;
        String username = null, password = null;
        ResultSet rs;
        try {
            
            forwardPage = request.getRequestDispatcher("TemplateServlet");
            header = request.getRequestDispatcher("allLinks.jsp");
//            header = request.getRequestDispatcher("Header");
//            footer = request.getRequestDispatcher("Footer");
            username = request.getParameter("username");
            password = request.getParameter("password");
            
            if (username != null && !username.trim().equals("") && password != null && !password.trim().equals("")) {
                try {
                    JDBCConnection instance = JDBCConnection.getInstance();
                    String query = "select * from users where username='" + username + "' and password='" + password + "'";
                    System.out.println(query);
                    rs = instance.executeQuery(query);
                    Integer role = null;
                    Integer userId = null;
                    while (rs.next()) {
                        role = rs.getInt("role");
                        userId = rs.getInt("id");
                    }
                    if (role != null) {
                        HttpSession session = request.getSession(false);
                        session.setAttribute("username", username);
                        session.setAttribute("role", role);
                        session.setAttribute("userid", userId);
                        forwardPage.forward(request, response);
                    } else {
                        header.include(request, response);
                        construct(out);
                        out.println("<center><font color=\"red\">Invalid username/password</font></center>");
                        
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                
                 header.include(request, response);
                construct(out);
                
            }
        } finally {
            out.close();
        }
    }
    
    public void construct(PrintWriter out) {
        try {
            out.println("<html>");
            out.println("<head><title>Library Management System</title></head>");
            out.println("<body background='images/ibrary_800x480.jpg'>");
            out.println("<center>");
            out.println("<img src='images/header.jpg' align='center'/>");
            out.println("<div class='paddingtop'>");
            out.println("<form action=\"LoginPage\" method=\"POST\">");
            out.println("<table  align=\"center\">");
            out.println("<tr><td><font color='white'>user name :</font></td><td><input type=\"text\" class='form-control'   name=\"username\"/></td></tr>");
            out.println("<tr><td><font color='white'>password :</font></td><td><input type=\"password\" class='form-control'   name=\"password\"/></td></tr>");
            out.println("<tr><td colspan=\"2\" align='right'><input class='btn btn-success' type=\"submit\" value=\"Login\"/></td></tr>");
            out.println("</table>");
            out.println("</form>");
            out.println("<div>");
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
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
