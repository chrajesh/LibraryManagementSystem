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
public class BookEntry extends HttpServlet {

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
        String accno, title, author, subject, callno, edition, publication, price, isbn, status, totalCount, issuedCount;
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            //   studentEntrypage(out);

            title = request.getParameter("title");
            author = request.getParameter("author");
            subject = request.getParameter("subject");
            edition = request.getParameter("edition");
            publication = request.getParameter("publication");
            price = request.getParameter("price");
            isbn = request.getParameter("isbn");
            totalCount = request.getParameter("totalNoOfBooks");
            issuedCount = request.getParameter("issuesdBooksCount");

            System.out.println("title :" + title);
            System.out.println("author :" + author);
            System.out.println("subject :" + subject);
            System.out.println("edition:" + edition);
            System.out.println("publication:" + publication);
            System.out.println("price :" + price);
            System.out.println("isbn :" + isbn);
            System.out.println("totalCount :" + totalCount);
            System.out.println("issued Count :" + issuedCount);

            String getBookToEdit = request.getParameter("BookId");
            System.out.println("get edit Book id ........" + getBookToEdit);




            String submitEditBook = request.getParameter("id");
            System.out.println("submitEditBook id..." + submitEditBook);
            if (submitEditBook != null) {
                System.out.println("i am in submit of edited Book details");
                submitEditBook(request, out);
            }



            if (submitEditBook == null && getBookToEdit == null && title != null && author != null && subject != null && totalCount != null && edition != null && publication != null && price != null && isbn != null && issuedCount != null) {
                System.out.println("i am in not null of all fields");
                if (title != "" && author != "" && subject != "" && totalCount != "" && edition != "" && publication != "" && price != "" && isbn != "" && issuedCount != "") {
                    System.out.println("i am in not empty of all fields");
                    System.out.println("title :" + title);
                    System.out.println("author :" + author);
                    System.out.println("subject:" + subject);
                    System.out.println("total count:" + totalCount);
                    System.out.println("edition:" + edition);
                    System.out.println("publication:" + publication);
                    System.out.println("price :" + price);
                    System.out.println("isbn :" + isbn);
                    System.out.println("issued count:" + issuedCount);
                    Integer BookId = null;
                    try {
                        JDBCConnection connection = JDBCConnection.getInstance();
                        String query = "select max(accNo) as BookId from bookentry";
                        ResultSet rs = connection.executeQuery(query);
                        while (rs.next()) {
                            System.out.println("max Id in book table....." + rs.getString("BookId"));
                            if (rs.getString("BookId") != null) {
                                BookId = Integer.parseInt(rs.getString("BookId"));
                            }
                        }

                        query = "";
                        if (BookId == null) {
                            query = "insert into bookentry values(";
                            query += "1,'" + title + "','" + author + "','" + subject + "','" + edition + "','" + publication + "','" + price + "','" + isbn + "','" + totalCount + "','" + issuedCount + "');";

                            System.out.println("insert query::::\n" + query);
                        } else {

                            query = "insert into bookentry values(";
                            query += (BookId + 1) + ",'" + title + "','" + author + "','" + subject + "','" + edition + "','" + publication + "','" + price + "','" + isbn + "','" + totalCount + "','" + issuedCount + "');";

                            System.out.println("insert query::::\n" + query);
                        }

                        Integer BookUpdate = connection.executeUpdate(query);


                        if (BookUpdate != 0) {
                            out.println("<center><font color=\"green\">Book created successfully</font></center>");
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }
            }
            if (getBookToEdit != null) {
                editBook(out, getBookToEdit);
            } else {
                createBook(out);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public void submitEditBook(HttpServletRequest request, PrintWriter out) {
        String accno, title, author, subject, callno, edition, publication, price, isbn, status, query = "", totalCount, issuedCount;
        Integer BookId = null;
        try {


            BookId = Integer.parseInt(request.getParameter("id"));
            title = request.getParameter("title");
            author = request.getParameter("author");
            subject = request.getParameter("subject");
            edition = request.getParameter("edition");
            publication = request.getParameter("publication");
            price = request.getParameter("price");
            isbn = request.getParameter("isbn");
            totalCount = request.getParameter("totalNoOfBooks");
            issuedCount = request.getParameter("issuesdBooksCount");
            if (title != null && author != null && subject != null && totalCount != null && edition != null && publication != null && price != null && isbn != null && issuedCount != null) {

                JDBCConnection connection = JDBCConnection.getInstance();
                String delBook = request.getParameter("delBook");
                System.out.println("del book......." + delBook);
                Integer BookUpdate = null, BookDelete = null;
                if (delBook != null) {
                    query = "delete from bookentry where accNo=" + BookId + "";
                    BookDelete = connection.executeUpdate(query);
                    if (BookDelete != 0) {
                        out.println("<center><font color=\"green\">Book deleted successfully</font></center>");
                    }
                } else {

                    query = "update bookentry set ";
                    query += "title='" + title + "',author='" + author + "',subject='" + subject + "',edition='" + edition + "',publication='" + publication + "',price='" + price + "', isbn='" + isbn + "',totalCount='" + totalCount + "',issuedCount='" + issuedCount + "' where accNo=" + BookId;

                    System.out.println("insert query::::\n" + query);

                    BookUpdate = connection.executeUpdate(query);

                    if (BookUpdate != 0) {
                        out.println("<center><font color=\"green\">Book updated successfully</font></center>");
                    }
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createBook(PrintWriter out) {
        try {
            out.println("<form action=\"TemplateServlet?pageId=4\" method=\"POST\">");
            out.println("<center>");
            out.println("<h2><font color='green'>Book Entry </font></h2>");
            out.println("<table  class='table table-bordered table-striped table-hover' style='width:50%'>");
            out.println("<tr><td>Title:</td><td><input type='text' class='form-control'  id='title' name='title'></td></tr>");
            out.println("<tr><td>Author:</td><td><input type='text' class='form-control'  id='author' name='author'></td></tr>");
            out.println("<tr><td>Subject:</td><td><input type='text' class='form-control'  id='subject' name='subject'></td></tr>");
            out.println("<tr><td>Edition:</td><td><input type='text' class='form-control'  name='edition' id='edition'></td></tr>");
            out.println("<tr><td>Publication:</td><td><input type='text' class='form-control'  name='publication' id='publication'></td></tr>");
            out.println("<tr><td>Price:</td><td><input type='text' class='form-control'  name='price' id='price'></td></tr>");
            out.println("<tr><td>ISBN:</td><td><input type='text' class='form-control'  name='isbn' id='isbn'></td></tr>");
            out.println("<tr><td>Total Books</td><td><input type='text' class='form-control' name='totalNoOfBooks' value='0' id='totalNoOfBooks'/></td></tr>");
            out.println("<tr><td>No. Of Issued</td><td><input type='text' class='form-control' name='issuesdBooksCount' readonly value='0' id='issuesdBooksCount'/></td></tr>");
            out.println("<tr><td colspan='2' align='right'><input type=\"submit\" class=\"CreateOrEditBook  btn btn-success\" value=\"Register\"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type='reset' class='btn btn-warning' value='Reset'></td></tr>");
            out.println("</table>");
             out.println("<label class=\"text-danger\" id=\"BookEL\"></label>");
            out.println("</center>");
            
            out.println("</form>");
            listOfBooks(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editBook(PrintWriter out, String BookId) {
        try {
            JDBCConnection connection = JDBCConnection.getInstance();
            String query = "select * from bookentry where accno=" + BookId;
            ResultSet rs = connection.executeQuery(query);
            while (rs.next()) {

                out.println("<form action=\"TemplateServlet?pageId=4&id=" + rs.getString("accno") + "\" method=\"POST\">");
                out.println("<center>");
                out.println("<h2><font color='green'>Edit Book</font> </h2>");
                out.println("<table  class='table table-bordered table-striped table-hover' style='width:50%'>");
                out.println("<tr><td>Title:</td><td><input type='text' class='form-control'  name='title' id='title' value=\"" + rs.getString("title") + "\"></td></tr>");
                out.println("<tr><td>Author:</td><td><input type='text' class='form-control'  name='author' id='author' value=\"" + rs.getString("author") + "\"></td></tr>");
                out.println("<tr><td>subject:</td><td><input type='text' class='form-control' size='30' name='subject' id='subject' value=\"" + rs.getString("subject") + "\"></td></tr>");
                out.println("<tr><td>Edition:</td><td><input type='text' class='form-control'  name='edition' id='edition' value=\"" + rs.getString("edition") + "\"></td></tr>");
                out.println("<tr><td>Publication:</td><td><input type='text' class='form-control'  name='publication'  id='publication' value=\"" + rs.getString("publication") + "\"></td></tr>");
                out.println("<tr><td>Price:</td><td><input type='text' class='form-control'  name='price'  id='price' value=\"" + rs.getString("price") + "\"></td></tr>");
                out.println("<tr><td>ISBN</td><td><input type='text' class='form-control'  name='isbn' id='isbn' value=\"" + rs.getString("isbn") + "\"></td></tr>");

                out.println("<tr><td>Total Books</td><td><input type='text' class='form-control' name='totalNoOfBooks' id='totalNoOfBooks' value=\"" + rs.getString("totalCount") + "\"/></td></tr>");
                out.println("<tr><td>No. Of Issued</td><td><input type='text' class='form-control' readonly name='issuesdBooksCount' id='issuesdBooksCount' value=\"" + rs.getString("issuedCount") + "\"/></td></tr>");

                out.println("<tr><td colspan='2' align='right'><input class=\"CreateOrEditBook  btn btn-info\" type=\"submit\" value=\"Update\"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"submit\" class=\"DeleteBook  btn btn-danger\" lang=\"Book\" name=\"delBook\" value=\"Delete\"/></td></tr>");
                out.println("</table>");
                out.println("<label class=\"text-danger\" id=\"BookEL\"></label>");
                out.println("</center>");
                out.println("</form>");


            }



            listOfBooks(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listOfBooks(PrintWriter out) {
        try {
            out.println("<table align=\"center\"  class='table' style='width:50%;'>");
            out.println("<tr>");
            out.println("<th>Title</th><th>Author</th><th>Subject</th><th>TotalBooks</th></tr>");
            JDBCConnection connection = JDBCConnection.getInstance();
            String query = "select * from bookentry";
            ResultSet rs = connection.executeQuery(query);
            int counter = 0;
            while (rs.next()) {
                counter++;
                out.println("<tr>");
                out.println("<td><a href=\"TemplateServlet?pageId=4&BookId=" + rs.getString("accNo") + "\">" + rs.getString("title") + "</a></td>");
                out.println("<td>" + rs.getString("author") + "</td>");
                out.println("<td>" + rs.getString("subject") + "</td>");
                out.println("<td>" + rs.getString("totalCount") + "</td>");
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
