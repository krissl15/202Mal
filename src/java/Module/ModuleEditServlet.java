/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import Members.MemberTools;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
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
 * @author BrageFagstad
 */
@WebServlet(name = "ModuleEditServlet", urlPatterns = {"/ModuleEditServlet"})
public class ModuleEditServlet extends HttpServlet {

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
            out.println("<title>Servlet ModuleEdit</title>");            
            out.println("</head>");
            
            
            out.println("<body>");
            String moduleID = request.getParameter("textmoduleId1");
            int intModuleId = Integer.parseInt(moduleID);
            String moduleName = request.getParameter("textmoduleName1");
            String moduleGoal = request.getParameter("textGoal1");
            String moduleText = request.getParameter("textModule1");
            String moduleDeliveryType = request.getParameter("textType1");
            String moduleStatus = request.getParameter("textStatus1");
            String moduleDate = request.getParameter("textDate1");
            String moduleMaxPoints = request.getParameter("textPoints1");
            
            String oldModuleId = request.getParameter("btnAdd");
            String oldId = oldModuleId.substring(oldModuleId.lastIndexOf(" ")+1); // "name" blir siste ordet i valuen av knappen (change).
            int intOldModuleId = Integer.parseInt(oldId);
            
            ModuleTools mt = new ModuleTools();
            mt.updateModule(intModuleId, moduleName, moduleGoal, moduleText, moduleDeliveryType, moduleStatus, moduleDate, intOldModuleId, moduleMaxPoints, out);
            
            out.println("Modulen er oppdatert");
            out.println("<form action=\"ModuleMenuServlet\" method=\"post\">\n"
                    + "                <input type=\"Submit\" name=\"btnBack\" value=\"Tilbake\"> <br>  \n"
                    + "               </form>");
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
            Logger.getLogger(ModuleEditServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModuleEditServlet.class.getName()).log(Level.SEVERE, null, ex);
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
