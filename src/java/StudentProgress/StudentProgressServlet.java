/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StudentProgress;

import Utilities.DbConnector;
import Utilities.MenuTools;
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
            out.println("<link href=\"css.css\" rel=\"stylesheet\" type=\"text/css\">");
            out.println("<title>Oversikt over progresjon</title>");
            out.println("</head>");
            out.println("<div id=\"header\">");
            MenuTools men = new MenuTools();
            men.menuButtons(out);
            out.println("</div>");
            out.println("<body>");

            String userName = request.getRemoteUser(); //navnet p� brukeren som er logget inn. brukes for studenter
            String user = request.getParameter("userName"); //navnet p� brukeren man vil se progresjon for. hentes fra URL, for foreleser
            String value = request.getParameter("value");
            ProgressTools proT = new ProgressTools();

            /**
             * *
             * KONTAKTINFORMASJON
             */
            String profilePic = "https://www.itutor-et.com/images/account.png";
            out.println("<div class=\"partedDiv profile\">");
            if (user == null) {
                out.println("<h2>Informasjon om " + userName + "</h2>");
                out.println("<div class=\"infoBox\">");
                out.println("<div class=\"pbDiv\">");
                out.println("<img src=" + profilePic + " height=\"100\" width=\"100\">");
                out.println("</div>");
                out.println("<div class=\"pbText\">");
                proT.printPerson(userName, out);
                out.println("</div>");
                out.println("</div");
            } else if (user != null) {
                out.println("<h2>Informasjon om " + user + "</h2>");
                out.println("<div class=\"infoBox\">");
                out.println("<div class=\"pbDiv\">");
                out.println("<img src=" + profilePic + " height=\"100\" width=\"100\">");
                out.println("</div");
                out.println("<div class=\"pbText\">");
                proT.printPerson(user, out);
                out.println("</div");
                out.println("</div");
                out.println("<br>");
            }
            out.println("</div>"
                    + "</div>");

            /**
             * *
             * PRINTER MODULER
             */
            DbConnector db = new DbConnector();
            try (Connection conn = db.getConnection(out)) {
                try (Statement st = conn.createStatement()) {
                    String moduleQ = "select modul_id from modul";
                    ResultSet rsModules = st.executeQuery(moduleQ);

                    //Generell start-tekst for progresjon over en registrert student
                    if ((user == null) || request.isUserInRole("Foreleser") && value.equals("registrertstudent") || request.isUserInRole("Assistent") && value.equals("registrertstudent") || request.isUserInRole("RegistrertStudent") && userName.equalsIgnoreCase(user) && value.equals("registrertstudent")){
                        out.print("<div class=\"partedDiv progress\">");
                        out.print("<h3>Oversikt over progresjon</h3>");
                        out.print("<table class=\"progressTable\">"
                                + "<thead>"
                                + "<tr>"
                                + "<th class=\"buttonCol title\">.</th>"
                                + "<th class=\"moduleCol progTitle\">Modul</th>"
                                + "<th class=\"deliveredCol title\">Status</th>"
                                + "<th class=\"correctedCol title\">Rettet</th>"
                                + "<th class=\"pointsCol title\">Poeng</th>"
                                + "<th class=\"maxPointsCol title\">/Maks poeng</th>"
                                + "</tr>"
                                + "</thead>");
                    }
                    
                    out.println("<tbody>");
                    while (rsModules.next()) {
                        out.println("<tr>");

                        String modulID = rsModules.getString("modul_id");
                        int intID = Integer.parseInt(modulID);

                        //Registrert student ser kun sin egen progresjon
                        if (request.isUserInRole("RegistrertStudent") && userName.equalsIgnoreCase(user) && value.equals("registrertstudent") && user != null) {
                            out.println("<td class=\"buttonCol prog\">");
                            out.println("<form action=\"ModulePageServlet\" method=\"post\">"
                                    + "<input type=\"Submit\" name=\"module\" value=\"" + intID + "\">");
                            out.println("</td>");
                            proT.printModulesByUserName(userName, intID, out);
                        } else if (request.isUserInRole("RegistrertStudent") && user == null) {
                            out.println("<td class=\"buttonCol prog\">");
                            out.println("<form action=\"ModulePageServlet\" method=\"post\">"
                                    + "<input type=\"Submit\" name=\"module\" value=\"" + intID + "\">");
                            out.println("</td>");
                            proT.printModulesByUserName(userName, intID, out);
                        } // Gjemmer foreleser og assistent i progresjon
                        else if (value.equals("foreleser") || value.equals("uregistrertstudent") || value.equals("assistent")) {
                            out.println("");
                        } //Foreleser og assistent f�r tilgang til studentenes progresjon
                        else if (request.isUserInRole("Foreleser") || request.isUserInRole("Assistent")) {
                            out.println("<td class=\"buttonCol prog\">");
                            out.println("<form action=\"ModulePageServlet\" method=\"post\">"
                                    + "<input type=\"Submit\" name=\"module\" value=\"" + intID + "\">");
                            out.println("</td>");
                            proT.printModulesByUserName(user, intID, out);
                        }
                        out.println("</form>");
                                out.println("</tr>");
                    }
                    out.println("</tbody>"
                            + "</table>");
                }
            }
            out.println("</div>");
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
