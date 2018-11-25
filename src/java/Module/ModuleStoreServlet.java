/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

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
 * @author phuonghapham
 */
@WebServlet(name = "ModuleStoreServlet", urlPatterns = {"/ModuleStoreServlet"})
public class ModuleStoreServlet extends HttpServlet {

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
            out.println("<title>Servlet ModuleStore</title>");
            out.println("</head>");
            out.println("<div id=\"header\">");
            MenuTools men = new MenuTools();
            men.menuButtons(out);
            out.println("</div>");
            out.println("<body>");

            ModuleTools mT = new ModuleTools();

            String btnEdit = request.getParameter("btnEdit");
            String moduleNr = btnEdit.substring(btnEdit.lastIndexOf(" ") + 1); // "name" blir siste ordet i valuen av knappen (change
            int modulID = Integer.parseInt(moduleNr);

            String getName = mT.getModuleName(modulID, out);
            String getGoal = mT.getGoal(modulID, out);
            String getText = mT.getText(modulID, out);
            String getStatus = mT.getStatus(modulID, out);
            String getDate = mT.getDate(modulID, out);
            String getType = mT.getType(modulID, out);
            String getPoints = mT.getMaxPoints(modulID, out);

            out.println("<h2>Rediger modul " + modulID + "</h2>");
            out.println("<form action=\"ModuleEditServlet\" method=\"POST\">\n"

                    + "<b>Modul ID</b><br><input type=\"text\" name=\"textmoduleId1\" placeholder=\"Modul ID\" value=\"" + modulID + "\" readonly> <br><br>  \n"
                    + "		 <b>Modulnavn</b><br><input type=\"text\" name=\"textmoduleName1\" placeholder=\"Modulnavn\" value=\"" + getName + "\"><br><br>  \n"
                    + "                <b>Læringsmål</b><br><textarea type=\"text\" name=\"textGoal1\" rows=\"5\" cols=\"50\" placeholder=\"Læringsmål\">" + getGoal + "</textarea><br><br>  \n"
                    + "                <b>Beskrivelse</b><br><textarea name=\"textModule1\" rows=\"5\" cols=\"50\" placeholder=\"Beskrivelse\">" + getText + "</textarea> <br><br>  \n"
                     + "                <b>Makspoeng</b><br><input type=\"text\" name=\"textPoints1\" placeholder=\"Makspoeng\" value =\"" + getPoints + "\"> <br><br>  \n"
                    + "                <b>Innleveringstype</b><br><input type=\"text\" name=\"textType1\" value=\"" + getType + "\"><br><br>  \n"

                    + "                <b>Status</b><br><input type=\"text\" name=\"textStatus1\" placeholder=\"Aktiv/Inaktiv\" value=\"" + getStatus + "\"> <br><br>  \n"
                    + "                <b>Fristdato</b><br><input type=\"text\" name=\"textDate1\" placeholder=\"YYYYMMDD\" value=\"" + getDate + "\"> <br><br>  \n"
                    + "<input type=\"Submit\" name=\"btnAdd\" value=\"Oppdater modul " + modulID + "\">\n"
                    + "</form>\n"
                    + "<br>");
            out.println("<form action=\"ModuleMenuServlet\" method=\"POST\">\n"
                    + "<input type=\"Submit\" name=\"backBtn\" value=\"Tilbake\">\n"
                    + "</form>");

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
            Logger.getLogger(ModuleStoreServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModuleStoreServlet.class.getName()).log(Level.SEVERE, null, ex);
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
