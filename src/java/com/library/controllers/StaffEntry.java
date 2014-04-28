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
public class StaffEntry extends HttpServlet {

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
        String mcode, name, contact, emailId, qualification, desig, department, address, gender, dob, doj;
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            name = request.getParameter("name");
            contact = request.getParameter("contact");
            emailId = request.getParameter("emailId");
            qualification = request.getParameter("qualification");
            desig = request.getParameter("desig");
            department = request.getParameter("department");
            address = request.getParameter("address");
            gender = request.getParameter("gender");
            dob = request.getParameter("dob");
            doj = request.getParameter("doj");



            System.out.println("name :" + name);
            System.out.println("contact:" + contact);
            System.out.println("emailId:" + emailId);
            System.out.println("qualification:" + qualification);
            System.out.println("desig :" + desig);
            System.out.println("department:" + department);
            System.out.println("address:" + address);
            System.out.println("gender" + gender);
            System.out.println("dob" + dob);
            System.out.println("doj" + doj);


            String getStaffToEdit = request.getParameter("staffId");
            System.out.println("get edit staff id ........" + getStaffToEdit);



            String submitEditStaff = request.getParameter("id");
            System.out.println("submitEditStaff id..." + submitEditStaff);
            if (submitEditStaff != null) {
                System.out.println("i am in submit of edited staff details");
                submitEditStaff(request, out);
            }



            if (submitEditStaff == null && getStaffToEdit == null && name != null && contact != null && emailId != null && qualification != null && desig != null && department != null && address != null && gender != null && dob != null && doj != null) {
                System.out.println("i am in not null of all fields");
                if (name != "" && contact != "" && emailId != "" && qualification != "" && desig != "" && department != "" && address != "" && gender != "" && dob != "" && doj != "") {
                    System.out.println("i am in not empty of all fields");
                    System.out.println("name :" + name);
                    System.out.println("contact:" + contact);
                    System.out.println("emailId:" + emailId);
                    System.out.println("qualification:" + qualification);
                    System.out.println("desig:" + desig);
                    System.out.println("department:" + department);
                    System.out.println("address:" + address);
                    System.out.println("gender" + gender);
                    System.out.println("dob" + dob);
                    System.out.println("doj" + doj);
                    Integer staffId = null;
                    try {
                        JDBCConnection connection = JDBCConnection.getInstance();
                        String query = "select max(mcode) as staffId from staffentry";
                        ResultSet rs = connection.executeQuery(query);
                        while (rs.next()) {
                            System.out.println("max Id in staffentry table....." + rs.getString("staffId"));
                            if (rs.getString("staffId") != null) {
                                staffId = Integer.parseInt(rs.getString("staffId"));
                            }
                        }

                        query = "";
                        if (staffId == null) {
                            query = "insert into staffentry values(";
                            query += "1,'" + name + "','" + contact + "','" + emailId + "','" + qualification + "','" + desig + "','" + department + "','" + address + "','" + gender + "','" + dob + "','" + doj + "');";

                            System.out.println("insert query::::\n" + query);
                        } else {

                            query = "insert into staffentry values(";
                            query += (staffId + 1) + ",'" + name + "','" + contact + "','" + emailId + "','" + qualification + "','" + desig + "','" + department + "','" + address + "','" + gender + "','" + dob + "','" + doj + "');";

                            System.out.println("insert query::::\n" + query);
                        }

                        Integer staffUpdate = connection.executeUpdate(query);


                        if (staffUpdate != 0) {
                            out.println("<center><font color=\"green\">staff created successfully</font></center>");
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
            if (getStaffToEdit != null) {
                editStaff(out, getStaffToEdit);
            } else {
                createStaff(out);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public void submitEditStaff(HttpServletRequest request, PrintWriter out) {
        String name, branch, contact, emailId, qualification, desig, department, address, gender, dob, doj, query = "";
        Integer staffId = null;
        try {


            staffId = Integer.parseInt(request.getParameter("id"));
            name = request.getParameter("name");
            contact = request.getParameter("contact");
            emailId = request.getParameter("emailId");
            qualification = request.getParameter("qualification");
            desig = request.getParameter("desig");
            department = request.getParameter("department");
            address = request.getParameter("address");
            gender = request.getParameter("gender");
            dob = request.getParameter("dob");
            doj = request.getParameter("doj");



            System.out.println("name :" + name);
            System.out.println("contact:" + contact);
            System.out.println("emailId:" + emailId);
            System.out.println("qualification:" + qualification);
            System.out.println("desig:" + desig);
            System.out.println("department:" + department);
            System.out.println("address:" + address);
            System.out.println("gender" + gender);
            System.out.println("dob" + dob);
            System.out.println("doj" + doj);
            if (name != null && contact != null && emailId != null && qualification != null && desig != null && department != null && address != null && gender != null && dob != null && doj != null) {

                JDBCConnection connection = JDBCConnection.getInstance();
                String delStaff = request.getParameter("delStaff");
                System.out.println("del satff......." + delStaff);
                Integer staffUpdate = null, staffDelete = null;
                if (delStaff != null) {
                    query = "delete from staffentry where mcode=" + staffId + "";
                    staffDelete = connection.executeUpdate(query);
                    if (staffDelete != 0) {
                        out.println("<center><font color=\"green\">Staff deleted successfully</font></center>");
                    }
                } else {

                    query = "update staffentry set ";
                    query += "Name='" + name + "',Contact='" + contact + "',Email='" + emailId + "',Qualification='" + qualification + "',desig='" + desig + "', Department='" + department + "', Address='" + address + "', Gender='" + gender + "',DOB='" + dob + "',DOJ='" + doj + "' where mcode=" + staffId;

                    System.out.println("insert query::::\n" + query);

                    staffUpdate = connection.executeUpdate(query);

                    if (staffUpdate != 0) {
                        out.println("<center><font color=\"green\">Staff updated successfully</font></center>");
                    }
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createStaff(PrintWriter out) {
        try {
            out.println("<form action=\"TemplateServlet?pageId=3\" method=\"POST\">");
            out.println("<center>");
            out.println("<h2><font color='green'>Create Staff</font></h2>");
            out.println("<table  class='table table-bordered table-striped table-hover' style='width:50%' >");
            out.println("<tr><td>Name:</td><td><input type='text' class='form-control'  id='name' name='name'></td></tr>");
            out.println("<tr><td>Contact:</td><td><input type='text' class='form-control' id='contact' name='contact'></td></tr>");
            out.println("<tr><td>Email-Id:</td><td><input type='text' class='form-control'  id='emailId' name='emailId'></td></tr>");
            out.println("<tr><td>Qualification:</td><td><input type='text' class='form-control'  id='qualification' name='qualification'></td></tr>");
            out.println("<tr><td>Designation:</td><td><input type='text' class='form-control' id='desig' name='desig'></td></tr>");
            out.println("<tr><td>Department:</td><td><input type='text' class='form-control'  id='department' name='department'></td></tr>");
            out.println("<tr><td>Address: </td><td><textarea name=\"address\" id='address' rows='3' cols='50'></textarea></td></tr>");
            //out.println("<tr><td>Upload Image:</td><td><input type='file'></td></tr>");
            out.println("<tr><td>Gender:<br></td><td><input type=radio id='gender1' name='gender' value='m'>&nbsp;&nbsp;Male&nbsp;&nbsp;&nbsp;&nbsp;<input id='gender2' type=radio name='gender' value='F'>&nbsp;&nbsp;Female</td></tr>");
            out.println("<tr><td>DOB:</td><td><input type='text' class='form-control'  id='dob' name='dob'></td></tr>");
            out.println("<tr><td> DOJ:</td><td><input type='text' class='form-control'  id='doj' name='doj'></td></tr>");
            out.println("<tr><td colspan='2' align='right'><input type=\"submit\" class=\"CreateOrEditStaff  btn btn-success\" value=\"Register\"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type='reset'  class='btn btn-warning'  value='Reset'></td></tr>");
            out.println("</table>");
            out.println("<label class=\"text-danger\" id=\"StaffEL\"></label><br/>");
            out.println("<label class=\"text-danger\" id=\"GenderEL\"></label>");
            out.println("</center>");
            out.println("</form>");
            listOfStaff(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editStaff(PrintWriter out, String staffId) {
        try {
            JDBCConnection connection = JDBCConnection.getInstance();
            String query = "select * from staffentry where mcode=" + staffId;
            ResultSet rs = connection.executeQuery(query);
            while (rs.next()) {

                out.println("<form action=\"TemplateServlet?pageId=3&id=" + rs.getString("mcode") + "\" method=\"POST\">");
                out.println("<center>");
                out.println("<h2><font color='green'>Edit Staff</font> </h2>");
                out.println("<table  class='table table-bordered table-striped table-hover' style='width:50%' >");
                out.println("<tr><td>Name:</td><td><input type='text' class='form-control' id='name' name='name' value=\"" + rs.getString("Name") + "\"></td></tr>");
                out.println("<tr><td>Contact:</td><td><input type='text' class='form-control'  id='contact' name='contact' value=\"" + rs.getString("Contact") + "\"></td></tr>");
                out.println("<tr><td>Email-Id:</td><td><input type='text' class='form-control' id='emailId' name='emailId' value=\"" + rs.getString("Email") + "\"></td></tr>");
                out.println("<tr><td>Qualificatin:</td><td><input type='text' class='form-control' ' id='qualification' name='qualification' value=\"" + rs.getString("qualification") + "\"></td></tr>");
                out.println("<tr><td>Designation:</td><td><input type='text' class='form-control'  name='desig' id='desig' value=\"" + rs.getString("Desig") + "\"></td></tr>");
                out.println("<tr><td>Department:</td><td><input type='text' class='form-control' ' name='department' id='department' value=\"" + rs.getString("Department") + "\"></td></tr>");
                out.println("<tr><td>Address: </td><td><textarea name=\"address\" id='address' rows='3' cols='50'>" + rs.getString("Address") + "</textarea></td></tr>");
                if (rs.getString("Gender").equalsIgnoreCase("m")) {
                    out.println("<tr><td>Gender:<br/></td><td><input id='gender1' type='radio' name='gender' value='m' checked>&nbsp;&nbsp;Male&nbsp;&nbsp;&nbsp;&nbsp;<input id='gender2' type='radio' name='gender' value='F'>&nbsp;&nbsp;Female</td></tr>");
                } else {
                    out.println("<tr><td>Gender:<br/></td><td><input type='radio' id='gender1' name='gender' value='m'>&nbsp;&nbsp;Male&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' id='gender2' name='gender' value='F' checked>&nbsp;&nbsp;Female</td></tr>");
                }
                out.println("<tr><td>DOB:</td><td><input type='text' class='form-control'  name='dob' id='dob' value=\"" + rs.getString("DOB") + "\"></td></tr>");
                out.println("<tr><td>DOJ</td><td><input type='text' class='form-control'  name='doj' id='doj' value=\"" + rs.getString("DOJ") + "\"></td></tr>");
                out.println("<tr><td colspan='2' align='right'><input type=\"submit\" class=\"CreateOrEditStaff  btn btn-info\" value=\"Update\"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"submit\"  class=\"DeleteStaff  btn btn-danger\" lang=\"Staff Member\"  name=\"delStaff\" value=\"Delete\"/></td></tr>");
                out.println("</table>");
                out.println("<label class=\"text-danger\" id=\"StaffEL\"></label><br/>");
                out.println("<label class=\"text-danger\" id=\"GenderEL\"></label>");
                out.println("</center>");
                out.println("</form>");


            }



            listOfStaff(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listOfStaff(PrintWriter out) {
        try {
            out.println("<table  align=\"center\"  class='table' style='width:50%;'>");
            out.println("<tr>");
            out.println("<th>Name</th><th>Designation</th><th>Department</th><th>Contact</th></tr>");
            JDBCConnection connection = JDBCConnection.getInstance();
            String query = "select * from staffentry";
            ResultSet rs = connection.executeQuery(query);
            int counter = 0;
            while (rs.next()) {
                counter++;
                out.println("<tr>");
                out.println("<td><a href=\"TemplateServlet?pageId=3&staffId=" + rs.getString("mcode") + "\">" + rs.getString("Name") + "</a></td>");
                out.println("<td>" + rs.getString("desig") + "</td>");
                out.println("<td>" + rs.getString("Department") + "</td>");
                out.println("<td>" + rs.getString("Contact") + "</td>");
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
