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
import javax.naming.NamingException;
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
     * @throws javax.naming.NamingException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentProgress</title>");            
            out.println("</head>");
            out.println("<body>");
             String userName = request.getRemoteUser(); //navnet på brukeren som er logget inn. brukes for studenter
             String user = request.getParameter("userName"); //navnet på brukeren man vil se progresjon for. hentes fra URL, for foreleser
             String value = request.getParameter("value");
             ProgressTools proT = new ProgressTools();
              
             /***
              * KONTAKTINFORMASJON
              */
                 out.println("Informasjon om " + user);
                 out.println("<br>");
                 proT.printPerson(user, out);
                 out.println("<br>");
                 out.println("<br>");

            /***
             * PRINTER MODULER
             */
            DbConnector db = new DbConnector();
            try (Connection conn = db.getConnection(out)) { 
                try (Statement st = conn.createStatement()) {
                    String moduleQ = "select modul_id from modul";
                    ResultSet rsModules = st.executeQuery(moduleQ);
                
                //Generell start-tekst for progresjon over en registrert student
                if (userName.equalsIgnoreCase(user) && value.equals("registrertstudent") || request.isUserInRole("Foreleser") && value.equals("registrertstudent") || request.isUserInRole("Assistent") && value.equals("registrertstudent")){
                    out.println("Oversikt over progresjon");
                    out.println("<br>");
                    out.println("<br>");
                    out.println("Modul  Status  Poeng");
                }

                while (rsModules.next()) {
                    String modulID = rsModules.getString("modul_id");
                    int intID = Integer.parseInt(modulID);
                    out.println("<br>");
                    //Registrert student ser kun sin egen progresjon
                    if (request.isUserInRole("RegistrertStudent") && userName.equalsIgnoreCase(user) && value.equals("registrertstudent")){
                        proT.listModulesByUsername(userName, intID, out);
                        out.println("<form action=\"ModulePageServlet\" method=\"post\">"
                            + "<input type=\"Submit\" name=\"module\" value=\""+ intID + "\">");
                     
                    } // Gjemmer foreleser og assistent i progresjon
                    else if(value.equals("foreleser") || value.equals("uregistrertstudent") || value.equals("assistent")){
                        out.println("");
                    }
                    //Foreleser og assistent får tilgang til studentenes progresjon
                    else if (request.isUserInRole("Foreleser") || request.isUserInRole("Assistent")){
                        proT.listModulesByUsername(user, intID, out);
                         out.println("<form action=\"ModulePageServlet\" method=\"post\">"
                            + "<input type=\"Submit\" name=\"module\" value=\""+ intID + "\">");
                    }
                    out.println("</form>");
                    out.println("<br>");
                }
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
        } catch (SQLException | NamingException ex) {
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
        } catch (SQLException | NamingException ex) {
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
}