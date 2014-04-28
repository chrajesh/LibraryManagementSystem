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
public class CreateClerk extends HttpServlet {

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
        String name, qualification, address, emailId, contactNo, username, password, gender, clerkId;
        try {



            name = request.getParameter("name");
            qualification = request.getParameter("qualification");
            address = request.getParameter("address");
            emailId = request.getParameter("emailId");
            contactNo = request.getParameter("contactNo");
            username = request.getParameter("txtUsername");
            password = request.getParameter("txtPassword");
            gender = request.getParameter("gender");

            clerkId = request.getParameter("clerkId");
            System.out.println("ckerk id ........" + clerkId);




            String editId = request.getParameter("id");
            System.out.println("edit id..." + editId);
            if (editId != null) {
                System.out.println("i am in edit of user");
                submitEditUser(request, response, out);
            }





            if (editId == null && clerkId == null && name != null && qualification != null && address != null && emailId != null && contactNo != null && username != null && password != null && gender != null) {
                System.out.println("i am in not null of all fields");
                if (name != "" && qualification != "" && address != "" && emailId != "" && contactNo != "" && username != "" && password != "" && gender != "") {
                    System.out.println("i am in not empty of all fields");
                    System.out.println("name :" + name);
                    System.out.println("qualification :" + qualification);
                    System.out.println("address :" + address);
                    System.out.println("emailId :" + emailId);
                    System.out.println("contactNo :" + contactNo);
                    System.out.println("username :" + username);
                    System.out.println("password :" + password);
                    System.out.println("gender :" + gender);
                    Integer clearkInfoId = null;
                    Integer userId = null;
                    try {
                        JDBCConnection connection = JDBCConnection.getInstance();
                        String query = "select max(id) as clearkInfoId from clerkinfo";
                        ResultSet rs = connection.executeQuery(query);
                        while (rs.next()) {
                            System.out.println("max Id in clerk table....." + rs.getString("clearkInfoId"));
                            if (rs.getString("clearkInfoId") != null) {
                                clearkInfoId = Integer.parseInt(rs.getString("clearkInfoId"));
                            }
                        }
                        query = "select max(id) as userId from users";
                        rs = connection.executeQuery(query);
                        while (rs.next()) {
                            System.out.println("max Id in user table....." + rs.getString("userId"));
                            if (rs.getString("userId") != null) {
                                userId = Integer.parseInt(rs.getString("userId"));
                            }
                        }

                        query = "";
                        query = "insert into users values(";
                        if (userId == null) {
                            userId = 1;
                            query += userId + ",'" + username + "','" + password + "',2);";
                            System.out.println("insert query::::\n" + query);
                        } else {
                            userId = userId + 1;
                            query += userId + ",'" + username + "','" + password + "',2);";
                            System.out.println("insert query::::\n" + query);
                        }
                        Integer usersUpdate = connection.executeUpdate(query);


                        query = "";
                        query = "insert into clerkinfo values(";
                        if (clearkInfoId == null) {
                            query += "1,'" + name + "','" + qualification + "','" + address + "','" + emailId + "','" + contactNo + "','" + gender + "'," + userId + ");";
                            System.out.println("insert query::::\n" + query);
                        } else {
                            query += (clearkInfoId + 1) + ",'" + name + "','" + qualification + "','" + address + "','" + emailId + "','" + contactNo + "','" + gender + "'," + userId + ");";
                            System.out.println("insert query::::\n" + query);
                        }

                        Integer clerkUpdate = connection.executeUpdate(query);


                        if (usersUpdate != 0 && clerkUpdate != 0) {
                            out.println("<center><font color=\"green\">clerk created successfully</font></center>");
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }




                } else {
                    //out.println("<center><font color=\"red\">Please Fill All Fields</font></center>");
                }
            } else {
                //out.println("<center><font color=\"red\">Please Fill All Fields</font></center>");
            }


            if (clerkId != null) {
                editClerk(out, clerkId);
            } else {
                createClerkPage(out);
            }


        } finally {
            out.close();
        }
    }

    public void submitEditUser(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        String name, qualification, address, emailId, contactNo, username, password, gender, query, usersTableClerkId;
        Integer clerkId = null;
        try {



            clerkId = Integer.parseInt(request.getParameter("id"));
            name = request.getParameter("name");
            qualification = request.getParameter("qualification");
            address = request.getParameter("address");
            emailId = request.getParameter("emailId");
            contactNo = request.getParameter("contactNo");
            username = request.getParameter("txtUsername");
            password = request.getParameter("txtPassword");
            gender = request.getParameter("gender");
            usersTableClerkId = request.getParameter("usersTableClerkId");
            System.out.println("users table clerk id........" + usersTableClerkId);

            if (name != null && qualification != null && address != null && emailId != null && contactNo != null && username != null && password != null && gender != null) {

                JDBCConnection connection = JDBCConnection.getInstance();
                String delClerk = request.getParameter("delClerk");
                System.out.println("del clerk......." + delClerk);
                Integer usersUpdate = null, clerkDelete = null, userDelete = null, clerkUpdate = null;
                if (delClerk != null) {
                    query = "delete from clerkinfo where id=" + clerkId;
                    clerkDelete = connection.executeUpdate(query);
                    query = "delete from users where username='" + username + "'";
                    userDelete = connection.executeUpdate(query);
                    System.out.println("clerk:::   " + clerkDelete + " userDelete:::  " + userDelete);
                    if (clerkDelete != 0 && userDelete != 0) {
                        out.println("<center><font color=\"green\">clerk deleted successfully</font></center>");
                    }
                } else {

                    query = "update clerkinfo set ";
                    query += "fullName='" + name + "',qualification='" + qualification + "',address='" + address + "',emailId='" + emailId + "',contactNo='" + contactNo + "',gender='" + gender + "',usersTableClerkId=" + usersTableClerkId + " where id=" + clerkId;

                    System.out.println("insert query::::\n" + query);

                    clerkUpdate = connection.executeUpdate(query);

                    query = "update users set password='" + password + "' where username='" + username + "'";

                    usersUpdate = connection.executeUpdate(query);




                    if (clerkUpdate != 0 && usersUpdate != 0) {
                        out.println("<center><font color=\"green\">clerk updated successfully</font></center>");
                    }
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createClerkPage(PrintWriter out) {
        try {


            out.println("<form action=\"TemplateServlet?pageId=1\" method=\"POST\">");
            out.println("<center>");
            out.println("<h2><font color='green'>clerk creation </font></h2>");
            out.println("<table  class='table table-bordered table-striped table-hover' style='width:50%' >");
            out.println("<tr><td>Full Name:</td><td><input type='text' class='form-control'  name='name' id='name'></td></tr>");
            out.println("<tr><td>Qualification:</td><td><input type='text' class='form-control'  id='qualification'   name='qualification'></td></tr>");
            out.println("<tr><td>Address: </td><td><textarea id='address' name=\"address\" rows='3' cols='50'></textarea></td></tr>");
            out.println("<tr><td>Email-Id:</td><td><input type='text' class='form-control' id='emailId'  name='emailId'></td></tr>");
            out.println("<tr><td>ContactNo:</td><td><input type='text' class='form-control'  id='contactNo' name='contactNo'></td></tr>");
//            out.println("<tr><td>Upload Image:</td><td><input type='file'></td></tr>");
            out.println("<tr><td>Username:</td><td><input type='text' class='form-control' name='txtUsername' id='txtUsername' ></td></tr>");
            out.println("<tr><td>Password:</td><td><input type='password' class='form-control'   name='txtPassword' style='width:100%' id='txtPassword' ></td></tr>");
            out.println("<tr><td>Confirm password:</td><td><input type='password' class='form-control'   style='width:100%' id='txtConfirmPassword' name='txtConfirmPassword'></td></tr>");
            out.println("<tr><td>Gender:<br/></td><td><input type='radio' id='gender1' name='gender' value='m'>&nbsp;&nbsp;Male&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' id='gender2' name='gender' value='F'>&nbsp;&nbsp;Female</td></tr>");
            out.println("<tr><td colspan='2' align='right'><input type=\"submit\" class='CreateOrEditClerk btn btn-success' value=\"Register\"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type='reset' class='btn btn-warning' value='Reset'></td></tr>");
            out.println("</table>");
            out.println("<label  class=\"text-danger\"  id=\"ClerkEL\"></label><br/>");
            out.println("<label  class=\"text-danger\" id=\"PasswordEL\"></label><br/>");
            out.println("<label class=\"text-danger\"  id=\"GenderEL\"></label>");
            out.println("</center>");
            out.println("</form>");
            listOfClerks(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editClerk(PrintWriter out, String clerkId) {
        try {



            JDBCConnection connection = JDBCConnection.getInstance();
            String query = "select * from clerkinfo where id=" + clerkId;
            ResultSet rs = connection.executeQuery(query);

            System.out.println("resultset..." + rs);
            Integer userId = null;
            while (rs.next()) {
                userId = rs.getInt("usersTableClerkId");
            }
            String query2 = "select * from users where id=" + userId;
            ResultSet rs2 = connection.executeQuery(query2);
            String username = null, password = null;

            while (rs2.next()) {
                username = rs2.getString("username");
                password = rs2.getString("password");
            }
            rs = connection.executeQuery(query);
            while (rs.next()) {
                out.println("<form action=\"TemplateServlet?pageId=1&id=" + rs.getString("id") + "\" method=\"POST\">");
                out.println("<center>");
                out.println("<input type=\"hidden\" name=\"usersTableClerkId\" value=\"" + rs.getString("usersTableClerkId") + "\">");
                out.println("<h2><font color='green'>Edit Clerk </font></h2>");
                out.println("<table class='table table-bordered table-striped table-hover' style='width:50%'>");
                out.println("<tr><td>Full Name:</td><td><input type='text' class='form-control'  id='name' name='name' value=\"" + rs.getString("fullName") + "\"></td></tr>");
                out.println("<tr><td>Qualification:</td><td><input type='text' class='form-control'  id='qualification' name='qualification'  value=\"" + rs.getString("qualification") + "\"></td></tr>");
                out.println("<tr><td>Address: </td><td><textarea  id=\"address\" name=\"address\" rows='3' cols='50'>" + rs.getString("address") + "</textarea></td></tr>");
                out.println("<tr><td>Email-Id:</td><td><input type='text' class='form-control' id='emailId'  name='emailId'  value=\"" + rs.getString("emailId") + "\"></td></tr>");
                out.println("<tr><td>ContactNo:</td><td><input type='text' class='form-control'  id='contactNo' name='contactNo' value=\"" + rs.getString("contactNo") + "\"></td></tr>");
                out.println("<tr><td>Username:</td><td><input type='text' class='form-control' name='txtUsername' id='txtUsername' readonly value=\"" + username + "\"></td></tr>");
                out.println("<tr><td>Password:</td><td><input type='password' name='txtPassword'  style='width:100%'  id='txtPassword'   value=\"" + password + "\"></td></tr>");
                out.println("<tr><td>Password:</td><td><input type='password' name='txtConfirmPassword' id='txtConfirmPassword'  style='width:100%'  value=\"" + password + "\"></td></tr>");
                if (rs.getString("gender").equalsIgnoreCase("m")) {
                    out.println("<tr><td>Gender:<br/></td><td><input type='radio' id='gender1' name='gender' value='m' checked>&nbsp;&nbsp;Male&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' id='gender2' name='gender' value='F'>&nbsp;&nbsp;Female</td></tr>");
                } else {
                    out.println("<tr><td>Gender:<br/></td><td><input type='radio' id='gender1' name='gender' value='m'>&nbsp;&nbsp;Male&nbsp;&nbsp;&nbsp;&nbsp;<input type='radio' id='gender2' name='gender' value='F' checked>&nbsp;&nbsp;Female</td></tr>");
                }

                out.println("<tr><td colspan='2' align='right'><input type=\"submit\" class='CreateOrEditClerk btn btn-info' value=\"Update\"/>&nbsp;&nbsp;&nbsp;&nbsp;<input  class=\"DeleteClerk btn btn-danger\" lang=\"Clerk\" type=\"submit\" name=\"delClerk\" value=\"Delete\"/></td></tr>");
                out.println("</table>");
                out.println("<label class=\"text-danger\" id=\"ClerkEL\"></label><br/>");
                out.println("<label  class=\"text-danger\"  id=\"PasswordEL\"></label><br/>");
                out.println("<label  class=\"text-danger\"  id=\"GenderEL\"></label>");
                out.println("</center>");
                out.println("</form>");
            }



            listOfClerks(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listOfClerks(PrintWriter out) {
        try {
            out.println("<center>");
            out.println("<table align=\"center\"  class='table' style='width:50%;'>");
            out.println("<tr>");
            out.println("<th>Name</th><th>EmailId</th><th>PhNo.</th></tr>");
            JDBCConnection connection = JDBCConnection.getInstance();
            String query = "select * from clerkinfo";
            ResultSet rs = connection.executeQuery(query);
            int counter = 0;
            while (rs.next()) {
                counter++;
                out.println("<tr>");
                out.println("<td><a href=\"TemplateServlet?pageId=1&clerkId=" + rs.getString("id") + "\">" + rs.getString("fullName") + "</a></td>");
                out.println("<td>" + rs.getString("emailId") + "</td>");
                out.println("<td>" + rs.getString("contactNo") + "</td>");
                out.println("</tr>");
            }

            if (counter == 0) {
                out.println("<tr><td colspan=\"3\"><font color=\"red\">No Records Exists...</font></td></tr>");
            }

            out.println("</table>");
            out.println("</center>");
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
