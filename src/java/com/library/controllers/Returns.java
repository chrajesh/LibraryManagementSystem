/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controllers;

import com.library.dao.JDBCConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bharathi
 */
public class Returns extends HttpServlet {

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

            String mcode, accno, personType, query, fine;
            ResultSet rs = null;
            JDBCConnection connection = JDBCConnection.getInstance();

            mcode = request.getParameter("mcode");
            accno = request.getParameter("accno");
            personType = request.getParameter("personType");
            fine = request.getParameter("fine");

            issuesPage(out);
            if (mcode != null && accno != null && personType != null) {
                if (!mcode.equals("") && !accno.equals("") && !personType.equals("")) {
                    Date d = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String todayDate = format.format(d);
                    query = "";
                    Integer differenceBwDates = 0;
                    Boolean bookAreadyReturned = isBookAreadyReturned(mcode, accno, personType, connection);


                    if (bookAreadyReturned) {
                        out.println("<center><font color='green'>Book is already returned successfully</font></center>");
                    } else {
                        if (personType.equals("1")) {
                            System.out.println("student is going to submit the book");
                            query = "select datediff('" + todayDate + "',startdate) as days from issues where stuid=" + mcode + " and status=1 and isStudent=1 and bookid=" + accno;
                            System.out.println("query :\n" + query);
                            rs = connection.executeQuery(query);
                            while (rs.next()) {
                                differenceBwDates = rs.getInt("days");
                            }
                            if (differenceBwDates >= 15) {
                                System.out.println("we have error message for student regarding how much fine he has");

                                Integer fineAmt = Integer.parseInt(fine);
                                if (fineAmt != 0) {
                                    System.out.println("student paid the fine");
                                    query = "";
                                    query = "update issues  set fine=" + fineAmt + ",enddate=now(),status=0 where stuid=" + mcode + " and isStudent=1 and status=1 and bookid=" + accno;
                                    System.out.println("query :\n" + query);
                                    Integer executeUpdate = connection.executeUpdate(query);
                                    if (executeUpdate != 0) {
                                        out.println("<center><font color='green'>Book Retured Successfully</font></center>");
                                    }
                                } else {
                                    out.println("<center><font color='red'>You have a fine of :" + differenceBwDates + "</font></center>");
                                }
                            } else {
                                System.out.println("i am in student doesn't have the fine");
                                query = "";
                                query = "update issues  set fine=0,enddate=now(),status=0 where stuid=" + mcode + " and isStudent=1 and status=1  and bookid=" + accno;
                                System.out.println("query :\n" + query);
                                Integer executeUpdate = connection.executeUpdate(query);
                                if (executeUpdate != 0) {
                                    out.println("<center><font color='green'>Book Retured Successfully</font></center>");
                                }
                            }
                        } else {
                            System.out.println("i am lecturer book submit");
                            query = "";
                            query = "update issues  set fine=0,enddate=now(),status=0 where stuid=" + mcode + " and isStudent=0 and status=1  and bookid=" + accno;
                            System.out.println("query :\n" + query);
                            Integer executeUpdate = connection.executeUpdate(query);
                            if (executeUpdate != 0) {
                                out.println("<center><font color='green'>Book Retured Successfully</font></center>");
                            }
                        }
                    }



                    System.out.println("difference Between Dates::::   " + differenceBwDates);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public Boolean isBookAreadyReturned(String mcode, String accno, String personType, JDBCConnection connection) {
        Boolean status = false;
        String query = null;
        try {
            System.out.println("i am in isBookAreadySubmitted");
            query = "select * from issues where stuid=" + mcode + "  and isStudent=" + personType + " and bookid=" + accno;
            ResultSet executeQuery = connection.executeQuery(query);
            if (executeQuery.next()) {
                String bkstatus = executeQuery.getString("status");
                if (bkstatus.equals("0")) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    
    

    public void issuesPage(PrintWriter out) {
        try {
            out.println("<form action=\"TemplateServlet?pageId=6\" method=\"POST\">");
            out.println("<center>");
            out.println("<h2 ><font color='green'>Book Returns</font></h2>");
            out.println("<table  align=\"center\"  class='table' style='width:50%;'>");
            out.println("<tr><td>Member Id:</td><td><input type='text' class='form-control' size='20' name='mcode' id='mcode'></td></tr>");
            out.println("<tr><td>Book Id:</td><td><input type='text' class='form-control' size='20' name='accno' id='accno'></td></tr>");
            out.println("<tr><td>Fine:</td><td><input type='text' class='form-control' name='fine' value='0' id='fine'/></td></tr>");
            out.println("<tr><td>Member Type :</td><td><select id='personType' name='personType'><option value=\"none\">select</option><option value=\"1\">Student</option><option value=\"0\">Staff</option></select></td></tr>");
            out.println("<tr><td colspan=\"2\" align=\"right\"><input   class=\"ReturnBook  btn btn-success\"  type='submit' value='submit'</td></tr>");
            out.println("</table>");
            out.println("<label class=\"text-danger\" id=\"ReturnBookEL\"></label>");
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
