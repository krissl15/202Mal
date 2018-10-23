/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Doffen
 */
@WebServlet(name = "ModulForms", urlPatterns = {"/ModulForms"})
public class ModulForms extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ModulForms</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"ModulePage\" method=\"post\">\n" +
"                <input type=\"Submit\" name=\"module\" value=\"1\"> \n" +
"		 <input type=\"Submit\" name=\"module\" value=\"2\">\n" +
"		 <input type=\"Submit\" name=\"module\" value=\"3\"><br><br>  \n" +
"            </form>  ");
            
             out.println("<body>\n" +
"        <div>\n" +
"            <p>Registrer modul</p>\n" +
"\n" +
"            <form action=\"ModuleAdded\">\n" +
"		 <b>Modul ID</b><input type=\"text\" name=\"textmoduleID\" placeholder=\"Modul ID\" required> <br><br>  \n" +
"		 <b>Modul navn</b><input type=\"text\" name=\"textmoduleName\" placeholder=\"Modul Navn\"> <br><br>  \n" +
"                <b>Modul læringsmål</b> <input type=\"text\" name=\"textGoal\" placeholder=\"Legg til læringsmål\"> <br><br>  \n" +
"                <b>Modul tekst</b> <input type=\"text\" name=\"textModule\" placeholder=\"Legg til tekst\"> <br><br>  \n" +
"                <b>Modul status</b> <input type=\"text\" name=\"textStatus\" placeholder=\"Aktiv/inaktiv\"> <br><br>  \n" +
"                <b>Modul fristdato</b> <input type=\"text\" name=\"textDate\" placeholder=\"YYYYMMDD\"> <br><br>  \n" +
                     
"				\n" +
"\n" +
"                <input type=\"Submit\" name=\"btnAdd\" value=\"Legg til modul\"> <br><br>  \n" +
"            </form>\n" +
"              <br><br>\n" +
"        </div>\n" +
"    </body>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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