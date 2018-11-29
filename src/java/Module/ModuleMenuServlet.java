 package Module;

import Utilities.DbConnector;
import Utilities.MenuTools;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
            out.println("<h2>Moduler</h2>");
            if (request.isUserInRole("Foreleser")) {
                out.println("<form action=\"ModuleAddedServlet\" method=\"post\">\n" +
                          "<input type=\"Submit\" name=\"btnAdd\" value=\"Legg til modul\"> <br>  \n" +
                          "</form>");
                out.println("<form action=\"UncorrectedServlet\" method=\"post\">\n" +
                          "<input type=\"Submit\" name=\"btnUnC\" value=\"Urettede moduler\"> <br><br>  \n" +
                          "</form>");
            }
            ModuleTools mt = new ModuleTools();
            DbConnector db = new DbConnector();
            try (Connection conn = db.getConnection(out)) {
//modulnavn print start   
try (Statement st = conn.createStatement()) {
    
        String moduleQ = "select modul_id from modul";
        ResultSet rsModules = st.executeQuery(moduleQ);
        out.println("<div class=\"partedDiv moduleMenu\">");
        out.println("<table class=\"modulesTable\">"
        + "<thead>"
        + "<tr>");
        out.println("<th class=\"buttonCol title\">"
                + "."
                + "</th>");
        out.println("<th class=\"moduleCol title\">"
        + "<b>Modul</b>"
        + "</th>");
        out.println("<th class=\"deadlineCol title\">"
        + "<b>Frist</b>"
        + "</th>");
        out.println("<th class=\"deliveredCol title\">"
        + "<b>Levert</b>"
        + "</th>");
        out.println("<th class=\"totalCol title\">"
        + "<b>Totalt</b>"
        + "</th>");
        if (request.isUserInRole("Foreleser")) {
            out.println("<th class=\"evaluatedCol title\">"
            + "<b>Evaluert</b>"
            + "</th>");
            out.println("<th class=\"editBtnCol title\">"
                    + "</th>");
            out.println("<th class=\"deleteBtnCol title\">"
                    + "</th>");
        }
        out.println("</tr>" 
        + "</thead>"
        + "<tbody>");            
        while (rsModules.next()) {
            String modulID = rsModules.getString("modul_id");
            int intID = Integer.parseInt(modulID);
            out.println("<tr>");
            out.println("<td class=\"buttonCol\">"
            + "<form action=\"ModulePageServlet\" method=\"post\">"
            + "<input type=\"Submit\" name=\"module\" value=\""+ modulID + "\">"
            + "</form></td>");
            out.println("<td class=\"moduleCol\">"
            + mt.getModuleName(intID, out)
            + "</td>");
            out.println("<td class=\"deadlineCol\">"
            + mt.getDate(intID, out)
            + "</td>");
            out.println("<td class=\"deliveredCol\">"
            + mt.getDelivered(intID, out)
            + "</td>");
            out.println("<td class=\"totalCol\">"
            + mt.getTotalRegistered(out)
            + "</td>");
            if (request.isUserInRole("Foreleser")) {
                out.println("<td class=\"evaluatedCol\">"
                + mt.getEvaluated(intID, out)
                + "</td>");
                out.println("<td class=\"editBtnCol\">");
                out.println("<form action=\"ModuleStoreServlet\" method=\"post\">"
                            + "<input type=\"Submit\" name=\"module\" value=\"Endre\">"
                        + "</form>");
                out.println("</td>");
                out.println("<td class=\"deleteBtnCol\">");
                out.println("<a href=\"DeleteModuleServlet\" onclick=\"return confirm('Trykk OK for å slette hele prosjektet');\">"
                        + "<input type=\"Submit\" name=\"module\" value=\"Slett\">"
                        + "</a>");
                out.println("</td>");
            }
            out.println("</tr>"); 
        }
        out.println("</div>");
        out.println("</tbody></table>");
    }
}   
        
         String commentBtn = request.getParameter("btnComment");
            
            if(commentBtn.contains("Kommenter")){
                out.println(commentBtn);
            LocalDate date = LocalDate.now();
            String stringDate = date.toString();
            String commentContent = request.getParameter("commentText");
            String commenter = request.getRemoteUser();
             ModuleTools mT = new ModuleTools();
             String moduleID = request.getParameter("hdnId");
             int intModuleId = Integer.parseInt(moduleID);
            mT.commentModule(commentContent, stringDate, commenter, intModuleId, out);
            response.sendRedirect("ModuleMenuServlet");
            
            }
            }   
             if (request.isUserInRole("RegistrertStudent")){
                 out.print("<form action =\"StudentProgressServlet\" method =\"POST\">\n" +
                           "<input type =\"Submit\" name =\"btnProgress\" value =\"Få oversikt over progresjon\">\n" +
                           "</form>");
             }
            out.println("</body>");
            out.println("</html>");
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
