/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Correcting;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Doffen
 */
@WebServlet(name = "GradingServlet", urlPatterns = {"/GradingServlet"})
public class GradingServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GradingServlet</title>");
            out.println("</head>");
            out.println("<body>");

            String student = request.getParameter("userName");
            String firstName = request.getParameter("firstName");
            String surname = request.getParameter("lastName");
            String moduleID = request.getParameter("moduleID");

            out.println("<form>\n"
                    + " Brukernavn " + "<input type=\"text\" name=\"txtStudent\" value=\"" + student + "\"readonly>\n<br>"
                    + "Fornavn " + "<input type=\"text\" name=\"txtFirstName\" value=\"" + firstName + "\"readonly>\n<br>"
                    + "Etternavn " + "<input type=\"text\" name=\"txtSurname\" value=\"" + surname + "\"readonly>\n<br>"
                    + "Modul ID " + "<input type=\"text\" name=\"txtModuleId\" value=\"" + moduleID + "\"readonly>\n<br>"
                    + "<input type=\"text\" name=\"txtComment\" placeholder=\"kommentar\">\n<br>"
                    + "<input type=\"text\" name=\"txtPoints\" placeholder=\"poeng (punktum ved desimal\">\n<br>"
                    + "<input type=\"submit\" name=\"btnGrade\" value=\"Registrer karakter\">\n<br>"
                    + "</form>");

            String btn = request.getParameter("btnGrade");

            if (btn.contains("Registrer")) {
                CorrectingTools ct = new CorrectingTools();
                String points = request.getParameter("txtPoints");
                String comment = request.getParameter("txtComment");
                String txtStudent = request.getParameter("txtStudent");
                String txtModuleID = request.getParameter("txtModuleId");
                String teacher = request.getRemoteUser();
                out.println("comment: " + comment + "<br>");
                out.println("points: " + points + "<br>");
                out.println("txtstudent: " + txtStudent + "<br>");
                out.println("txtmoduleid: " + txtModuleID + "<br>");
                out.println("txtteacher: " + teacher + "<br>");

                ct.setGrade(comment, points, teacher, txtStudent, txtModuleID, out);
                ct.setRettetAndLevert(txtStudent, txtModuleID, out);
                response.sendRedirect("ModuleMenuServlet");
            }

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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(GradingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(GradingServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
