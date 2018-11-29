/*
 * This servlet is for the lecturer to correct students deliveries. It is coupled closesly with correctingtools. 
 */
package Correcting;

import Delivery.DeliveryTools;
import Module.ModuleTools;
import Utilities.MenuTools;
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
            out.println("<link href=\"css.css\" rel=\"stylesheet\" type=\"text/css\">");
            out.println("<title>Servlet GradingServlet</title>");
            out.println("</head>");
            out.println("<div id=\"header\">");
            MenuTools men = new MenuTools();
            men.menuButtons(out);
            out.println("</div>");
            out.println("<body>");
            out.println("<h2>Gi tilbakemelding</h2>");
            out.println("<div id=\"grading\">"); //hele boksen 
            String student = request.getParameter("userName");
            String firstName = request.getParameter("firstName");
            String surname = request.getParameter("lastName");
            String moduleID = request.getParameter("moduleID");
            int intModuleID = Integer.parseInt(moduleID);
            ModuleTools mt = new ModuleTools();
            String maxPoints = mt.getMaxPoints(intModuleID, out);

            out.println("<div id=\"gradingPersonal\">"//personlig info 
                    + "<form method=\"post\">\n"
                    + " Brukernavn <br>" + "<input type=\"text\" name=\"txtStudent\" value=\"" + student + "\"readonly>\n<br><br>"
                    + "Fornavn <br>" + "<input type=\"text\" name=\"txtFirstName\" value=\"" + firstName + "\"readonly>\n<br><br>"
                    + "Etternavn <br>" + "<input type=\"text\" name=\"txtSurname\" value=\"" + surname + "\"readonly>\n<br><br>"
                    + "Modul ID <br> " + "<input type=\"text\" name=\"txtModuleId\" value=\"" + moduleID + "\"readonly>\n<br><br>"
                    + "</div>"//end personlig info 
                    + "<div id=\"gradingComments\">"
                    + "Kommentar <br>" + "<textarea name=\"txtComment\" rows=\"8\" cols=\"50\" placeholder=\"kommentar\"required></textarea><br><br>"
                    + "poeng <br>" + "<input type=\"text\" name=\"txtPoints1\" placeholder=\"poeng (punktum ved desimal\"required>\n" + "."
                    + "<input type=\"text\" name=\"txtPoints2\" placeholder=\"poeng (punktum ved desimal\"required>" + " / " + maxPoints + "\n<br><br><br><br>"
                    + "<input type=\"submit\" name=\"btnGrade\" value=\"Registrer karakter\">\n<br>"
                    + "</form>"
                    + "</div>"
                    + "</div>");

            String type = mt.getType(intModuleID, out);

            out.println("<div id=\"gradingModuleInfo\">");
            out.println("<div id=\"gradingGoal\">");
            out.println("Læringsmål for denne modulen: " + "<br><br>");
            out.println(mt.getGoal(intModuleID, out) + "<br><br>");
            out.println("</div>");
            out.println("<div id=\"gradingAnswer\">");
            if (type.contains("Skriftlig")) {

                out.println("Studentens svar: " + "<br><br>");
                DeliveryTools dt = new DeliveryTools();
                out.println(dt.getDeliveryTextByModuleUser(moduleID, student, out));

            } else {
                out.println("Denne modulen er muntlig");
            }
            out.println("</div>");
            out.println("</div>");

            String btn = request.getParameter("btnGrade");

            if (btn.contains("Registrer")) {
                CorrectingTools ct = new CorrectingTools();
                String points1 = request.getParameter("txtPoints1");
                String points2 = request.getParameter("txtPoints2");
                String points = points1 + "." + points2;
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
