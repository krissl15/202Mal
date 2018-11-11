/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StudentProgress;

import Utilities.DbConnector;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author phuonghapham
 */
@WebServlet(name = "StudentProgressServlet", urlPatterns = {"/StudentProgressServlet"})
public class StudentProgressServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentProgress</title>");            
            out.println("</head>");
            out.println("<body>");
            
            String userName = request.getRemoteUser(); //navnet på brukeren
            out.println("Progresjon for " + userName + "<br>");
            
            ProgressTools proT = new ProgressTools();
            proT.printStudent(userName, out);
            out.println("<br>");
            out.println("<br>");

                    DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {

            //modulnavn print start   
            try (Statement st = conn.createStatement()) {
                 String moduleQ = "select modul_id from modul";
                ResultSet rsModules = st.executeQuery(moduleQ);
                while (rsModules.next()) {
                    String modulID = rsModules.getString("modul_id");
                    int intID = Integer.parseInt(modulID);
                    out.println("Modul  Status  Poeng");
                    out.println("<form action=\"ModulePageServlet\" method=\"post\">"
                            + "<input type=\"Submit\" name=\"module\" value=\""+ intID + "\">");
                    proT.listModulesByUsername(userName, intID, out);
                    out.println("</form>");
                    out.println("<br>");
                }//registrerte brukere slutt
            }
        }
            
            
            out.println("<br>");
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
            Logger.getLogger(StudentProgressServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(StudentProgressServlet.class.getName()).log(Level.SEVERE, null, ex);
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


