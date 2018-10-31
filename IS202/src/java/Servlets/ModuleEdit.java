/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Utilities.ModuleTools;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author BrageFagstad
 */
@WebServlet(name = "ModuleEdit", urlPatterns = {"/ModuleEdit"})
public class ModuleEdit extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ModuleEdit</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<fieldset>");
            out.println("<body>\n" +

"        <div>\n" +
           
"            <h3><p>Rediger modul</p></h3>\n" +
"\n" +
"            <form>\n" +
"		 <b>Modul navn</b><input type=\"text\" name=\"textmoduleName\" placeholder=\"Modul Navn\"> <br><br>  \n" +
"                <b>Modul læringsmål</b> <input type=\"text\" name=\"textGoal\" placeholder=\"Oppdater læringsmål\"> <br><br>  \n" +
"                <b>Modul tekst</b> <input type=\"text\" name=\"textModule\" placeholder=\"Oppdater tekst\"> <br><br>  \n" +
"                <b>Modul status</b> <input type=\"text\" name=\"textStatus\" placeholder=\"Aktiv/inaktiv\"> <br><br>  \n" +
"                <b>Modul fristdato</b> <input type=\"text\" name=\"textDate\" placeholder=\"YYYYMMDD\"> <br><br>  \n" +                     
"\n" +
"\n" +
"                <input type=\"Submit\" name=\"btnEdit\" value=\"Oppdater modul\"> <br><br>  \n" +
"            </form>\n" +
"        </div>\n" + 

"        <div>\n" +
"            <form action=\"ModuleMenu\" method=\"post\">\n" +  
"                <input type=\"Submit\" value=\"Tilbake\"><br>  \n" +  
"            </form>\n" +                    
"        </div>\n" +                     
"    </body>");
            
            
            String modul_id = "select modul_id from modul";
            String modul_navn = request.getParameter("textmoduleName");
            String modul_læringsmål = request.getParameter("textGoal");
            String modul_tekst = request.getParameter("textModule");
            String modul_status;
            modul_status = request.getParameter("textStatus");
            String modul_fristdato = request.getParameter("textDate");
            
            int modulID = Integer.parseInt(modul_id);
            int intDato = Integer.parseInt(modul_fristdato);
            
                    ModuleTools mt = new ModuleTools();
                  
                    mt.editModule(modulID, modul_navn, modul_læringsmål, modul_tekst, modul_status, intDato, out);
                    
                   
                }
            out.println("</fieldset>");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
