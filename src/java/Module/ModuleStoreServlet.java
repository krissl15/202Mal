/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

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
            out.println("<title>Servlet ModuleStore</title>");            
            out.println("</head>");        
            out.println("<body>");
            
            ModuleTools mT = new ModuleTools();
            
            
            String btnEdit = request.getParameter("btnEdit");
            String moduleNr = btnEdit.substring(btnEdit.lastIndexOf(" ")+1); // "name" blir siste ordet i valuen av knappen (change
            int modulID = Integer.parseInt(moduleNr);
            
            String getName = mT.getModuleName(modulID, out);
            String getGoal = mT.getGoal(modulID, out);
            String getText = mT.getText(modulID, out);
            String getStatus = mT.getStatus(modulID, out);
            String getDate = mT.getDate(modulID, out);
            String getType = mT.getType(modulID, out);


            out.println("Registrer modul " + modulID);
            out.println("<form action=\"ModuleEditServlet\" method=\"POST\">\n" +
            "Modul ID <input type=\"text\" name=\"textmoduleId1\" placeholder=\"Modulnavn\" value=\""+ modulID + "\"><br>" +
            "Modul navn <input type=\"text\" name=\"textmoduleName1\" placeholder=\"Modulnavn\" value=\""+ getName + "\"><br>" +
            "Modul læringsmål <input type=\"text\" name=\"textGoal1\" placeholder=\"Oppdater læringsmål\" value=\""+ getGoal + "\"><br>" +
"Modul tekst <input type=\"text\" name=\"textModule1\" placeholder=\"Oppdater tekst på modulen\" value=\""+ getText + "\"><br>" +
        "Modul status <input type=\"text\" name=\"textType1\" placeholder=\"Aktiv/inaktiv\" value=\""+ getType + "\"><br>" +

"Modul status <input type=\"text\" name=\"textStatus1\" placeholder=\"Aktiv/inaktiv\" value=\""+ getStatus + "\"><br>" +
"Modul fristdato <input type=\"text\" name=\"textDate1\" placeholder=\"YYYYMMDD\" value=\""+ getDate + "\"><br>" +
"\n" +
"<input type=\"Submit\" name=\"btnAdd\" value=\"Oppdater modul " + modulID +"\">\n" +
"</form>\n" +
"<br>");
            out.println("<form action=\"ModuleMenuServlet\" method=\"POST\">\n" +
"<input type=\"Submit\" name=\"backBtn\" value=\"Tilbake\">\n" +
"</form>");

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
