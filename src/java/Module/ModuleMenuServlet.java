 package Module;

import Utilities.DbConnector;
import Utilities.MenuTools;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * @author Doffen
 */
@WebServlet(name = "ModuleMenuServlet", urlPatterns = {"/ModuleMenuServlet"})
public class ModuleMenuServlet extends HttpServlet {

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
            out.println("<link href=\"css.css\" rel=\"stylesheet\" type=\"text/css\">");
            out.println("<title>Servlet ModuleMenu</title>");            
            out.println("</head>");
            out.println("<div id=\"header\">");
            MenuTools men = new MenuTools();
            men.menuButtons(out);
            out.println("</div>");
            out.println("<body>");
            out.println("<div id=\"content\">");
            out.println("<h2>Moduler</h2>");
            if (request.isUserInRole("Foreleser")) {
                out.print("<form action=\"ModuleAddedServlet\" method=\"post\">\n" +
                          "<input type=\"Submit\" name=\"btnAdd\" value=\"Legg til modul\"> <br>  \n" +
                          "</form>");
        ModuleTools mt = new ModuleTools();
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            //modulnavn print start   
            try (Statement st = conn.createStatement()) {
                 String moduleQ = "select modul_id from modul";
                ResultSet rsModules = st.executeQuery(moduleQ);
                    out.println("<div class=\"moduleCol title\">"
                            + "<b>Modul</b>"
                            + "</div>");
                    out.println("<div class=\"deadlineCol title\">"
                            + "<b>Frist</b>"
                            + "</div>");
                    out.println("<div class=\"deliveredCol title\">"
                            + "<b>Levert</b>"
                            + "</div>");
                    out.println("<div class=\"totalCol title\">"
                            + "<b>Totalt</b>"
                            + "</div>");
                    out.println("<div class=\"evaluatedCol title\">"
                            + "<b>Evaluert</b>"
                            + "</div>");
                    out.println("<br>");
                    out.println("<br>");
                while (rsModules.next()) {
                    String modulID = rsModules.getString("modul_id");
                    int intID = Integer.parseInt(modulID);
                    out.println("<form action=\"ModulePageServlet\" method=\"post\">"
                            + "<input type=\"Submit\" name=\"module\" value=\""+ modulID + "\">"
                                    + "</form>");
                    out.println("<div class=\"moduleCol\">"
                            + mt.getModuleName(intID, out)
                            + "</div>");
                    out.println("<div class=\"deadlineCol\">"
                            + mt.getDate(intID, out)
                            + "</div>");
                    out.println("<div class=\"deliveredCol\">"
                            + mt.getDelivered(intID, out)
                            + "</div>");
                    out.println("<div class=\"totalCol\">"
                            + mt.getTotalRegistered(out)
                            + "</div>");
                    out.println("<div class=\"evaluatedCol\">"
                            + mt.getEvaluated(intID, out)
                            + "</div>");
                       
                    out.println("<form action=\"ModuleStoreServlet\" method=\"post\">"
                            + "<input type=\"Submit\" name=\"module\" value=\"Endre\">");
    
                    /*out.println("<form action=\"DeleteModuleServlet\" method=\"post\">"
                            + "<input type=\"Submit\" name=\"module\" value=\"Slett\">"
                            + "</form>"*/
                    out.println("<a href=\"DeleteModuleServlet\" onclick=\"return confirm('Trykk OK for å slette hele prosjektet');\">"
                            + "<input type=\"Submit\" name=\"module\" value=\"Slett\">"
                            + "</a>");
                    out.println("</form>");
                }
            }
        }
                out.println("<h3>Urettede moduler</h3><br>");
                out.print("<form action=\"UncorrectedServlet\" method=\"post\">\n" +
                          "<input type=\"Submit\" name=\"btnUnC\" value=\"Moduler\"> <br><br>  \n" +
                          "</form>");
                
            }   
             else if (request.isUserInRole("RegistrertStudent")){
                 out.print("<form action =\"StudentProgressServlet\" method =\"POST\">\n" +
                           "<input type =\"Submit\" name =\"btnProgress\" value =\"Få oversikt over progresjon\">\n" +
                           "</form>");
             }
             out.println("</div>");
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
            Logger.getLogger(ModuleMenuServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModuleMenuServlet.class.getName()).log(Level.SEVERE, null, ex);
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
