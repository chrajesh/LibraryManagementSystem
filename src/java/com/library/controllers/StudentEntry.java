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
public class StudentEntry extends HttpServlet {

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
        String mcode, name, branch, batch, contact, emailId, admNo, address, gender, dob;
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            //   studentEntrypage(out);

            name = request.getParameter("name");
            branch = request.getParameter("branch");
            batch = request.getParameter("batch");
            contact = request.getParameter("contact");
            emailId = request.getParameter("emailId");
            admNo = request.getParameter("admNo");
            address = request.getParameter("address");
            gender = request.getParameter("gender");
            dob = request.getParameter("dob");



            System.out.println("name :" + name);
            System.out.println("branch :" + branch);
            System.out.println("contact:" + contact);
            System.out.println("emailId:" + emailId);
            System.out.println("admNo:" + admNo);
            System.out.println("address:" + address);
            System.out.println("gender" + gender);
            System.out.println("dob" + dob);


            String getStudentToEdit = request.getParameter("studentId");
            System.out.println("get edit student id ........" + getStudentToEdit);




            String submitEditStudent = request.getParameter("id");
            System.out.println("submitEditStudent id..." + submitEditStudent);
            if (submitEditStudent != null) {
                System.out.println("i am in submit of edited student details");
                submitEditStudent(request, out);
            }



            if (submitEditStudent == null && getStudentToEdit == null && name != null && branch != null && contact != null && emailId != null && admNo != null && batch != null && address != null && gender != null && dob != null) {
                System.out.println("i am in not null of all fields");
                if (name != "" && branch != "" && contact != "" && emailId != "" && admNo != "" && batch != "" && address != "" && gender != "" && dob != "") {
                    System.out.println("i am in not empty of all fields");
                    System.out.println("name :" + name);
                    System.out.println("branch :" + branch);
                    System.out.println("contact:" + contact);
                    System.out.println("emailId:" + emailId);
                    System.out.println("admNo:" + admNo);
                    System.out.println("address:" + address);
                    System.out.println("gender" + gender);
                    System.out.println("dob" + dob);
                    Integer studentId = null;
                    try {
                        JDBCConnection connection = JDBCConnection.getInstance();
                        String query = "select max(mcode) as studentId from studententry";
                        ResultSet rs = connection.executeQuery(query);
                        while (rs.next()) {
                            System.out.println("max Id in student table....." + rs.getString("studentId"));
                            if (rs.getString("studentId") != null) {
                                studentId = Integer.parseInt(rs.getString("studentId"));
                            }
                        }

                        query = "";
                        if (studentId == null) {
                            query = "insert into studententry values(";
                            query += "1,'" + name + "','" + branch + "','" + contact + "','" + batch + "','" + emailId + "','" + admNo + "','" + address + "','" + gender + "','" + dob + "');";

                            System.out.println("insert query::::\n" + query);
                        } else {

                            query = "insert into studententry values(";
                            query += (studentId + 1) + ",'" + name + "','" + branch + "','" + contact + "','" + batch + "','" + emailId + "','" + admNo + "','" + address + "','" + gender + "','" + dob + "');";

                            System.out.println("insert query::::\n" + query);
                        }

                        Integer studentUpdate = connection.executeUpdate(query);


                        if (studentUpdate != 0) {
                            out.println("<center><font color=\"green\">student created successfully</font></center>");
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }
            }
            if (getStudentToEdit != null) {
                editStudent(out, getStudentToEdit);
            } else {
                createStudent(out);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public void submitEditStudent(HttpServletRequest request, PrintWriter out) {
        String name, branch, batch, contact, emailId, admNo, address, gender, dob, query = "";
        Integer studentId = null;
        try {


            studentId = Integer.parseInt(request.getParameter("id"));
            name = request.getParameter("name");
            branch = request.getParameter("branch");
            batch = request.getParameter("batch");
            contact = request.getParameter("contact");
            emailId = request.getParameter("emailId");
            admNo = request.getParameter("admNo");
            address = request.getParameter("address");
            gender = request.getParameter("gender");
            dob = request.getParameter("dob");

            if (name != null && branch != null && contact != null && emailId != null && admNo != null && batch != null && address != null && gender != null && dob != null) {

                JDBCConnection connection = JDBCConnection.getInstance();
                String delStudent = request.getParameter("delStudent");
                System.out.println("del clerk......." + delStudent);
                Integer studentUpdate = null, studentDelete = null;
                if (delStudent != null) {
                    query = "delete from studententry where mcode=" + studentId + "";
                    studentDelete = connection.executeUpdate(query);
                    if (studentDelete != 0) {
                        out.println("<center><font color=\"green\">Student deleted successfully</font></center>");
                    }
                } else {

                    query = "update studententry set ";
                    query += "Name='" + name + "',Branch='" + branch + "',Contact='" + contact + "',batch='" + batch + "',emailId='" + emailId + "',admNo='" + admNo + "',Address='" + address + "', Gender='" + gender + "',DOB='" + dob + "' where mcode=" + studentId;

                    System.out.println("insert query::::\n" + query);

                    studentUpdate = connection.executeUpdate(query);

                    if (studentUpdate != 0) {
                        out.println("<center><font color=\"green\">Student updated successfully</font></center>");
                    }
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createStudent(PrintWriter out) {
        try {
            out.println("<form action=\"TemplateServlet?pageId=2\" method=\"POST\">");
            out.println("<center>");
            out.println("<h2 align='center' ><font color='green'>Create Student</font> </h2>");
            out.println("<table  class='table table-bordered table-striped table-hover' style='width:50%' >");
            out.println("<tr><td>Name:</td><td><input type='text'  class='form-control' id='name' name='name'></td></tr>");
            out.println("<tr><td>Branch:</td><td><input type='text' class='form-control'  id='branch' name='branch'></td></tr>");
            out.println("<tr><td>batch:</td><td><input type='text' class='form-control'  name='batch' id='batch'></td></tr>");
            out.println("<tr><td>Contact:</td><td><input type='text' class='form-control' ' name='contact' id='contact'></td></tr>");
            out.println("<tr><td>Email-Id:</td><td><input type='text' class='form-control'  name='emailId' id='emailId'></td></tr>");
            out.println("<tr><td>Admition No:</td><td><input type='text' class='form-control'  name='admNo' id='admNo'></td></tr>");
            out.println("<tr><td>Address: </td><td><textarea name=\"address\" id='address' rows='3' cols='50'></textarea></td></tr>");
            out.println("<tr><td>Gender:<br></td><td><input type=radio name='gender' id='gender1' value='m'>&nbsp;&nbsp;Male&nbsp;&nbsp;&nbsp;&nbsp;<input type=radio id='gender2' name='gender' value='F'>&nbsp;&nbsp;Female</td></tr>");
            out.println("<tr><td>DOB:</td><td><input type='text' class='form-control'  id='dob' name='dob'></td></tr>");
            out.println("<tr><td colspan='2' align='right'><input type=\"submit\" class=\"CreateOrEditStudent  btn btn-success\" value=\"Register\"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type='reset'  class='btn btn-warning'  value='Reset'></td></tr>");
            out.println("</table>");
            out.println("<label class=\"text-danger\" id=\"StudentEL\"></label><br/>");
            out.println("<label class=\"text-danger\" id=\"GenderEL\"></label>");
            out.println("</center>");
            out.println("</form>");
            listOfStudents(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editStudent(PrintWriter out, String studentId) {
        try {
            JDBCConnection connection = JDBCConnection.getInstance();
            String query = "select * from studententry where mcode=" + studentId;
            ResultSet rs = connection.executeQuery(query);
            while (rs.next()) {

                out.println("<form action=\"TemplateServlet?pageId=2&id=" + rs.getString("mcode") + "\" method=\"POST\">");
                out.println("<center>");
                out.println("<h2 align='center' font-size:20px><font color='green'>Edit Student</font> </h2>");
                out.println("<table class='table table-bordered table-striped table-hover' style='width:50%' >");
                out.println("<tr><td>Name:</td><td><input type='text' class='form-control' size='20' name='name' id='name' value=\"" + rs.getString("Name") + "\"></td></tr>");
                out.println("<tr><td>Branch</td><td><input type='text' class='form-control' size='15' name='branch' id='branch' value=\"" + rs.getString("Branch") + "\"></td></tr>");
                out.println("<tr><td>batch:</td><td><input type='text' class='form-control' size='30' name='batch' id='batch' value=\"" + rs.getString("batch") + "\"></td></tr>");
                out.println("<tr><td>Contact:</td><td><input type='text' class='form-control' size='15' name='contact' id='contact' value=\"" + rs.getString("Contact") + "\"></td></tr>");
                out.println("<tr><td>Email-Id:</td><td><input type='text' class='form-control' size='20' name='emailId' id='emailId' value=\"" + rs.getString("emailId") + "\"></td></tr>");
                out.println("<tr><td>Admition No:</td><td><input type='text' class='form-control' size='15' name='admNo' id='admNo' readonly value=\"" + rs.getString("admNo") + "\"></td></tr>");
                out.println("<tr><td>Address: </td><td><textarea name=\"address\" id='address' rows='3' cols='50'>" + rs.getString("Address") + "</textarea></td></tr>");
                if (rs.getString("Gender").equalsIgnoreCase("m")) {
                    out.println("<tr><td>Gender:<br/></td><td><input type='radio' id='gender1' name='gender' value='m' checked>&nbsp;&nbsp;Male&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='gender' id='gender2' value='F'>&nbsp;&nbsp;Female</td></tr>");
                } else {
                    out.println("<tr><td>Gender:<br/></td><td><input type='radio' name='gender' id='gender1' value='m'>&nbsp;&nbsp;Male&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' name='gender' value='F' id='gender2' checked>&nbsp;&nbsp;Female</td></tr>");
                }
                out.println("<tr><td>DOB:</td><td><input type='text' class='form-control' name='dob' id='dob'  value=\"" + rs.getString("DOB") + "\"></td></tr>");
                out.println("<tr><td colspan='2' align='right'> <input type=\"submit\" class=\"CreateOrEditStudent btn btn-info\" value=\"Update\"/>&nbsp;&nbsp;&nbsp;&nbsp;<input  class=\"DeleteStudent  btn btn-danger\" lang=\"Student\"  type=\"submit\" name=\"delStudent\" value=\"Delete\"/></td></tr>");
                out.println("</table>");
                out.println("<label class=\"text-danger\" id=\"StudentEL\"></label><br/>");
                out.println("<label class=\"text-danger\" id=\"GenderEL\"></label>");
                out.println("</center>");
                out.println("</form>");


            }



            listOfStudents(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listOfStudents(PrintWriter out) {
        try {
            out.println("<table align=\"center\"  class='table' style='width:50%;'>");
            out.println("<tr>");
            out.println("<th>Name</th><th>AdmissionNo</th><th>Branch</th><th>Batch</th></tr>");
            JDBCConnection connection = JDBCConnection.getInstance();
            String query = "select * from studententry";
            ResultSet rs = connection.executeQuery(query);
            int counter = 0;
            while (rs.next()) {
                counter++;
                out.println("<tr>");
                out.println("<td><a href=\"TemplateServlet?pageId=2&studentId=" + rs.getString("mcode") + "\">" + rs.getString("Name") + "</a></td>");
                out.println("<td>" + rs.getString("admNo") + "</td>");
                out.println("<td>" + rs.getString("Branch") + "</td>");
                out.println("<td>" + rs.getString("batch") + "</td>");
                out.println("</tr>");
            }

            if (counter == 0) {
                out.println("<tr><td colspan=\"4\"><font color=\"red\">No Records Exists...</font></td></tr>");
            }

            out.println("</table>");
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
