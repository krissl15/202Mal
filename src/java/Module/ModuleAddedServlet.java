/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import Members.MemberTools;
import Utilities.MenuTools;
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
 * @author asmundfagstad
 */
@WebServlet(name = "ModuleAddedServlet", urlPatterns = {"/ModuleAddedServlet"})
public class ModuleAddedServlet extends HttpServlet {

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
            out.println("<title>Servlet ModuleAdded</title>");
            out.println("</head>");
            out.println("<div id=\"header\">");
            MenuTools men = new MenuTools();
            men.menuButtons(out);
            out.println("</div>");
            out.println("<body>");
            out.println("<body>\n"
                    + "        <div>\n"
                    + "            <h2>Opprett ny modul</h2>\n"
                    + "\n"
                    + "            <form>\n"
                    + "		 <b>Modul ID</b><br><input type=\"text\" name=\"moduleID\" placeholder=\"Modul ID\"> <br><br>  \n"
                    + "		 <b>Modulnavn</b><br><input type=\"text\" name=\"textmoduleName\" placeholder=\"Modulnavn\"> <br><br>  \n"
                    + "                <b>Læringsmål</b><br><textarea name=\"textGoal\" rows=\"5\" cols=\"50\" placeholder=\"Legg til læringsmål\"></textarea><br><br>  \n"
                    + "                <b>Beskrivelse</b><br><textarea name=\"textModule\" rows=\"5\" cols=\"50\" placeholder=\"Legg til beskrivelse av modulen\"></textarea> <br><br>  \n"
                     + "                <b>Makspoeng</b><br><input type=\"text\" name=\"textPoints\" placeholder=\"Makspoeng\"> <br><br>  \n"
                    + "                <b>Muntlig</b><input type=\"checkbox\" name=\"textType\" value=\"Muntlig\">  \n"
                    + "                <b>Skriftlig</b><input type=\"checkbox\" name=\"textType\" value=\"Skriftlig\"><br><br>  \n"

                    + "                <b>Status</b><br><input type=\"text\" name=\"textStatus\" placeholder=\"Aktiv/Inaktiv\"> <br><br>  \n"
                    + "                <b>Fristdato</b><br><input type=\"text\" name=\"textDate\" placeholder=\"YYYYMMDD\"> <br><br>  \n"
                    + "\n"
                    + "\n"
                    + "                <input type=\"Submit\" name=\"btnAdd\" value=\"Legg til modul\"> <br><br>  \n"
                    + "            </form>\n"
                    + "        </div>\n"
                    + "        <div>\n");
            
           String moduleName = request.getParameter("textmoduleName");
            String module_id = request.getParameter("moduleID");
            int intModuleId = Integer.parseInt(module_id);
            String modulGoal = request.getParameter("textGoal");
            String moduleText = request.getParameter("textModule");
            String moduleStatus = request.getParameter("textStatus");
            String modul_fristdato = request.getParameter("textDate");
            String modulPoints = request.getParameter("textPoints");
            String moduleType = request.getParameter("textType");

            
            int intDato = Integer.parseInt(modul_fristdato);

            ModuleTools mt = new ModuleTools();
            MemberTools mem = new MemberTools();

            mt.insertModule(intModuleId, moduleName, modulGoal, moduleText, modulPoints, moduleType, moduleStatus, intDato, out);
            mem.addAllToNewModulekanal(module_id, out);
            
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
            Logger.getLogger(ModuleAddedServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModuleAddedServlet.class.getName()).log(Level.SEVERE, null, ex);
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