/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controllers;

import com.library.dao.JDBCConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bharathi
 */
public class Issues extends HttpServlet {

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
        try {
            String mcode, accno, personType;


            mcode = request.getParameter("mcode");
            accno = request.getParameter("accno");
            personType = request.getParameter("personType");


            if (mcode != null && accno != null && personType != null) {
                if (!mcode.equals("") && !accno.equals("") && !personType.equals("")) {

                    Integer issueId = null;

                    JDBCConnection connection = JDBCConnection.getInstance();
                    String query = "select max(id) as issueId from issues";
                    ResultSet rs = connection.executeQuery(query);
                    while (rs.next()) {
                        System.out.println("max Id in issues table....." + rs.getString("issueId"));
                        if (rs.getString("issueId") != null) {
                            issueId = Integer.parseInt(rs.getString("issueId"));
                        }
                    }
                    query = "";
                    query = "select totalCount,issuedCount from bookentry where accNo=" + accno;
                    System.out.println("query\n:" + query);
                    rs = null;
                    rs = connection.executeQuery(query);
                    Integer totalCount = null, issuedCount = null;
                    boolean isBookThere = false;
                    while (rs.next()) {
                        isBookThere = true;
                        totalCount = rs.getInt("totalCount");
                        issuedCount = rs.getInt("issuedCount");
                    }


                    boolean isUserExists = false;
                    query = "";
                    if (personType.equals("1")) {

                        query = "select * from studententry where mcode=" + mcode;
                        System.out.println("query\n:" + query);
                        rs = null;
                        rs = connection.executeQuery(query);
                        while (rs.next()) {
                            isUserExists = true;
                        }
                    } else {
                        query = "select * from staffentry where mcode=" + mcode;
                        System.out.println("query\n:" + query);
                        rs = null;
                        rs = connection.executeQuery(query);
                        while (rs.next()) {
                            isUserExists = true;
                        }
                    }


                    if (isUserExists) {
                        if (isBookThere) {

                            System.out.println("Before ::::: total Count....." + totalCount + " issuedCount...." + issuedCount);
                            issuedCount = issuedCount + 1;
                            System.out.println("After ::::: total Count....." + totalCount + " issuedCount...." + issuedCount);

                            if (totalCount >= issuedCount) {

                                query = "";
                                Integer StuBookCount = null;
                                Integer StaffBookCount = null;
                                if (personType.equals("1")) {

                                    query = "select count(*) as count from issues where stuid=" + mcode + " and status=1 and isStudent=1";
                                    System.out.println("query :\n" + query);
                                    rs = connection.executeQuery(query);
                                    while (rs.next()) {
                                        StuBookCount = rs.getInt("count");
                                    }
                                } else {
                                    query = "select count(*) as count from issues where stuid=" + mcode + " and status=1 and isStudent=0";
                                    System.out.println("query :\n" + query);
                                    rs = connection.executeQuery(query);
                                    while (rs.next()) {
                                        StaffBookCount = rs.getInt("count");
                                    }
                                }


                                System.out.println("Student Book Count :::" + StuBookCount);
                                System.out.println("staff  Book Count :::" + StaffBookCount);

                                boolean issueBook = false;
                                if (StuBookCount != null && StuBookCount < 3) {
                                    issueBook = true;
                                }
                                if (StaffBookCount != null && StaffBookCount < 5) {
                                    issueBook = true;
                                }
                                System.out.println("condition ::::" + (StuBookCount != null && StuBookCount > 3));
                                System.out.println("condition ::::" + (StaffBookCount != null && StaffBookCount > 5));
                                System.out.println("issues book status :::" + issueBook);

                                if (issueBook) {
                                    query = "";
                                    boolean isBookAlreadyExisted = false;
                                    if (personType.equals("1")) {

                                        query = "select * from issues where stuid=" + mcode + " and isStudent=1 and status=1 and bookid=" + accno;
                                        System.out.println("query :\n" + query);
                                        rs = connection.executeQuery(query);
                                        while (rs.next()) {
                                            isBookAlreadyExisted = true;
                                        }
                                    } else {
                                        query = "select *  from issues where stuid=" + mcode + " and isStudent=0 and status=1 and bookid=" + accno;
                                        System.out.println("query :\n" + query);
                                        rs = connection.executeQuery(query);
                                        while (rs.next()) {
                                            isBookAlreadyExisted = true;
                                        }
                                    }



                                    if (!isBookAlreadyExisted) {

                                        query = "";
                                        query = "update bookentry  set issuedCount=" + issuedCount + " where accNo=" + accno;
                                        System.out.println("query:\n" + query);
                                        Integer bookUpdate = connection.executeUpdate(query);


                                        Integer clerkId = Integer.parseInt(request.getSession(false).getAttribute("userid").toString());
                                        query = "";
                                        query = "insert into issues values(";
                                        if (issueId == null) {

                                            query += "1," + mcode + "," + accno + ",1," + personType + "," + clerkId + ",now(),null,0);";

                                        } else {
                                            query += (issueId + 1) + "," + mcode + "," + accno + ",1," + personType + "," + clerkId + ",now(),null,0);";
                                        }
                                        System.out.println("query:\n" + query);
                                        Integer issueUpdate = connection.executeUpdate(query);


                                        if (issueUpdate != 0 && bookUpdate != 0) {
                                            out.println("<center><font color=\"green\">Book Issued successfully</font></center>");
                                        }
                                    } else {
                                        out.println("<center><font color=\"red\">This Book is already issued to you</font></center>");
                                    }



                                } else {
                                    out.println("<center><font color=\"red\">Maxmium book count is reached for you</font></center>");
                                }

                            } else {
                                out.println("<center><font color=\"red\">The books are over with this book id</font></center>");
                            }
                        } else {
                            out.println("<center><font color=\"red\">The Book Doesn't exists</font></center>");
                        }
                    } else {
                        out.println("<center><font color=\"red\">The user doesn't exists</font></center>");
                    }



                }
            }

            issuesPage(out);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public void issuesPage(PrintWriter out) {
        try {
            out.println("<form action=\"TemplateServlet?pageId=5\" method=\"POST\">");
            out.println("<center>");
            out.println("<h2><font color='green'>Book Issues</font></h2>");
            out.println("<table   align=\"center\"  class='table' style='width:50%;'>");
            out.println("<tr><td>Member Id:</td><td><input type='text' class='form-control' id='mcode' size='20' name='mcode'></td></tr>");

            JDBCConnection connection = JDBCConnection.getInstance();
            ResultSet rs = connection.executeQuery("select accNo,title from bookentry");
            out.println("<tr><td>Book Id:</td><td><select  name='accno' id='accno'>");
            out.println("<option value='none'>Select</option>");
            while (rs.next()) {
                out.println("<option value="+rs.getString("accNo")+">"+rs.getString("title")+"</option>");
            }
            out.println("</select></td></tr>");
            //out.println("<tr><td>Book Id:</td><td><input type='text' class='form-control' size='20' name='accno' id='accno'></td></tr>");
            out.println("<tr><td>Member Type :</td><td><select id='personType' name='personType'><option value=\"none\">select</option><option value=\"1\">Student</option><option value=\"0\">Staff</option></select></td></tr>");
            out.println("<tr><td colspan=\"2\" align=\"right\"><input class=\"IssueBook  btn btn-success\" type='submit' value='submit'</td></tr>");
            out.println("</table>");
            out.println("<label class=\"text-danger\" id=\"IssueBookEL\"></label>");
            out.println("</center>");
            out.println("</form>");
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
