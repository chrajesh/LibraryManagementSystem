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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bharathi
 */
public class AllReports extends HttpServlet {

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
            constructReport(out);
            String reportType = null;
            reportType = request.getParameter("report");
            if (reportType != null && !reportType.equals("")) {
                System.out.println("report type..." + reportType);
                String query = null;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                Date time = calendar.getTime();
                String fromDate = format.format(time);
                String toDay = format.format(time);
                JDBCConnection connection = JDBCConnection.getInstance();
                String toDate = null;

                if (reportType.equals("2")) {
                    ResultSet rs = connection.executeQuery("select  date_sub(curdate(),interval 1 week) as dateIs");
                    while (rs.next()) {
                        toDate = rs.getString("dateIs");
                    }
                }
                if (reportType.equals("3")) {
                    ResultSet rs = connection.executeQuery("select  date_sub(curdate(),interval 1 month) as dateIs");
                    while (rs.next()) {
                        toDate = rs.getString("dateIs");
                    }
                }
                if (reportType.equals("4")) {
                    ResultSet rs = connection.executeQuery("select  date_sub(curdate(),interval 1 year) as dateIs");
                    while (rs.next()) {
                        toDate = rs.getString("dateIs");
                    }
                }

                if (reportType.equals("5")) {
                    fromDate = request.getParameter("fromDate");
                    toDate = request.getParameter("toDate");
                }
                System.out.println("toDate.........." + toDate + "...... from date...." + fromDate);
                if (reportType.equals("1")) {
                    query = "select * from issues where startdate='" + fromDate + "'";
                } else {
                    query = "select * from issues where  startdate between '" + toDate + "' and '" + fromDate + "'";
                }

                Integer returnOrNot = null;
                String status = null;
                String fine = "";
                ResultSet executeQuery = connection.executeQuery(query);
                if (executeQuery != null) {
                    out.println("<center>");
                    out.println("<table>");
                    int counter = 0;
                    while (executeQuery.next()) {
                        
                        
                        String id=executeQuery.getString("stuid");
                        String isStudent=executeQuery.getString("isStudent");
                        String bookid=executeQuery.getString("bookid");
                        
                        
                        counter++;
                        out.println("<tr>");
                        returnOrNot = executeQuery.getInt("status");
                        System.out.println("status is ........"+returnOrNot + " " +(returnOrNot == 1)+"..."+bookid+ " ....");
                        if (returnOrNot == 0) {
                            status = "returned";
                        } else {
                            status = "not returned";
                        }

                        //The Book Id : 123 is took by Student/Staff Id:
                        String construct = "<td>" + counter + "....</td><td>The Book Id : <a href='TemplateServlet?pageId=4&BookId=" + bookid + "'>" + bookid + "</a> is took by </td>";
                        if (isStudent.equals("1")) {
                            Integer diffBetweenDates = DiffBetweenDates(executeQuery.getString("startdate"), toDay);
                            System.out.print(status+"...for student...."+id+"...date1...."+executeQuery.getString("startdate")+"...today..."+toDay+"...difference........." + diffBetweenDates);

                            if (diffBetweenDates >= 15) {
                                if (status.equals("returned")) {
                                    fine = "<label class=\"text-success\">Fine...." + diffBetweenDates + " Paid</label>";
                                }else{
                                    fine = "<label class=\"text-danger\"  >Fine...." + diffBetweenDates + " Not Paid</label>";
                                }
                            } else {
                                fine = "";
                            }
                            construct += "<td>Student id : </td><td><a href='TemplateServlet?pageId=2&studentId=" + id + "'>" +id + "</a> </td><td>" + status + "</td><td> " + fine + "</td>";
                        } else {
                            construct += "<td>Staff id : </td><td><a href='TemplateServlet?pageId=3&staffId=" + id + "'>" + id + "</a> </td><td>" + status + "</td><td></td>";
                        }
                        out.println(construct);
                        out.println("</tr>");
                    }
                    if (counter == 0) {
                        out.println("<td><label class=\"text-danger\"  >No Records Exists</label></td>");
                    }

                    out.println("</table>");
                    out.println("</center>");
                }
            }





        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public static Integer DiffBetweenDates(String start, String end) {

        if (start != null && end != null && start.length() > 0 && end.length() > 0 && start.split("-").length == 3 && end.split("-").length == 3) {

            Calendar cal1 = new GregorianCalendar();
            Calendar cal2 = new GregorianCalendar();

            cal1.set(Integer.parseInt(start.split("-")[0]), (Integer.parseInt(start.split("-")[1]) - 1), Integer.parseInt(start.split("-")[2]));
            cal2.set(Integer.parseInt(end.split("-")[0]), (Integer.parseInt(end.split("-")[1]) - 1), Integer.parseInt(end.split("-")[2]));

            System.out.println("difference is ......." + (((cal2.getTime().getTime() - cal1.getTime().getTime()) / (1000 * 60 * 60 * 24))));
            return (int) (((cal2.getTime().getTime() - cal1.getTime().getTime()) / (1000 * 60 * 60 * 24)));

        } else {
            return -1;
        }

    }

    public void constructReport(PrintWriter out) {
        try {
            out.println("<form action=\"TemplateServlet?pageId=9\" method=\"POST\">");
            out.println("<center>");
            out.println("<h2><font color='green'>Reports</font></h2>");
            out.println("<table  align=\"center\"  class='table' style='width:50%;'>");
            out.println("<tr>");
            out.println("<td  colspan='2'>");
            out.println("<input type='radio' name='report otherRadio' class='report' value='1'/> Today's Report");
            out.println("</td>");

            out.println("</tr>");
            out.println("<tr>");
            out.println("<td  colspan='2'>");
            out.println("<input type='radio' name='report' class='report otherRadio'  value='2'/>  Weekly Report");
            out.println("</td>");

            out.println("</tr>");
            out.println("<tr>");
            out.println("<td  colspan='2'>");
            out.println("<input type='radio' name='report' class='report otherRadio'  value='3'/> Monthly Report");
            out.println("</td>");

            out.println("</tr>");
            out.println("<tr>");
            out.println("<td  colspan='2'>");
            out.println("<input type='radio' name='report' class='report otherRadio'  value='4'/> Year Report");
            out.println("</td>");

            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>");
            out.println("<input type='radio' name='report' class='report betweenDates'  value='5'/> Between Dates");
            out.println("</td>");
            out.println("<td>"
                    + "<table>"
                    + "<tr>"
                    + "<td>"
                    + "From :"
                    + "</td> "
                    + "<td>"
                    + "<input type='text' name='fromDate' id='fromDate' disabled value='YYYY-MM-DD'/>"
                    + "</td>"
                    + "<tr>"
                    + "<td> "
                    + "To : "
                    + "</td>"
                    + "<td>"
                    + "<input disabled type='text' name='toDate' id='toDate'  value='YYYY-MM-DD' />"
                    + "</td>"
                    + "</tr>"
                    + "</table>"
                    + "</td>");
            out.println("</tr>");
            out.println("<tr><td colspan='2' align='right'><input type='reset'  class='btn btn-warning'  name='cancel' value='Cancel'/> <input type='submit' class='GetReports  btn btn-success' name='sunmit' value='GetReport'/></td></tr>");
            out.println("</table>");

            out.println("<label class=\"text-danger\"  id='ReportsEL'></label>");

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
