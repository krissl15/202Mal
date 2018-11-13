/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import Delivery.DeliveryTools;
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
 * @author Doffen
 */
@WebServlet(name = "ModulePageServlet", urlPatterns = {"/ModulePageServlet"})
public class ModulePageServlet extends HttpServlet {

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
            out.println("<title>Servlet ModulePage</title>");
            out.println("</head>");
            out.println("<body>");
            ModuleTools mT = new ModuleTools();
            String moduleNr = request.getParameter("module"); //alle knappene heter det samme ("module")
            int intModuleNr = Integer.parseInt(moduleNr);

            DbConnector db = new DbConnector();
            try (Connection conn = db.getConnection(out)) {
                try (Statement st = conn.createStatement()) {
                    String moduleQ = "select modul_id from modul";
                    ResultSet rsModules = st.executeQuery(moduleQ);
                    while (rsModules.next()) {
                        String modulID = rsModules.getString("modul_id");
                        if (moduleNr.equals(modulID)) { //Sjekker om knappene inneholder tallet på modulen i navnet
                            //modul1 her
                            int intID = Integer.parseInt(modulID);
                            int nr = intID;
                            out.println("Name of module: ");
                            mT.showModule(nr, out);

                        }//end if
                    }//end while
                }//end preparedstatement
            }//end connection

            if (request.isUserInRole("Foreleser")) {
                out.print("<form action=\"ModuleStoreServlet\" method=\"post\">\n"
                        + "                <input type=\"Submit\" name=\"btnEdit\" value=\"Rediger modul " + intModuleNr + "\"<br>  \n"
                        + "               </form>");
                out.print("<form action=\"DeleteModuleServlet\" method=\"post\">\n"
                        + "                <input type=\"Submit\" name=\"btnDelete\" value=\"Slett Modul " + intModuleNr + "\"><br>"
                        + "               </form>");
            }

            /*
            *Lever oppgave-knapp
             */
            if (request.isUserInRole("RegistrertStudent")) {
                out.println("<h3>Lever Modul</h3>");

                String moduleType = mT.getType(intModuleNr, out);

                if (moduleType.equals("Innlevering")) {
                    DeliveryTools dt = new DeliveryTools();
                    String userName = request.getRemoteUser(); //navnet på brukeren
                    String moduleStatus = dt.checkIfDelivered(userName, intModuleNr, out);
                    
                    String isModuleCorrected = dt.checkIfDeliveryCorrected(userName, intModuleNr, out);
                    
                    if (moduleStatus.equals("levert")) {
                        out.println("Modulen er levert" + "<br><br>");
                        out.println("Modulen er " + isModuleCorrected);
                    } else {
                        out.println("<form action=\"DeliveryServlet\" method=\"post\">\n"
                                + "                <input type=\"text\" name=\"deliveryText\" placeholder=\"Youtube link/svartekst\"> <br><br>\n"
                                + "					<input type=\"submit\" name=\"btnDeliver\"  value=\"Lever Modul " + intModuleNr + "\"><br>"
                                + "            </form>  ");
                    }
                } else {//om modulen er muntlig
                    out.println("Denne modulen er muntlig");
                }

                out.println("</body>");
                out.println("</html>");
            }//end student if
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
            Logger.getLogger(ModulePageServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModulePageServlet.class.getName()).log(Level.SEVERE, null, ex);
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
