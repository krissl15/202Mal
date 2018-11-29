/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Announcement.AnnouncementTools;
import Members.MemberTools;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
 * @author Doffen
 */
@WebServlet(name = "MainPage", urlPatterns = {"/MainPage"})
public class MainPage extends HttpServlet {

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
            out.println(" <link href=\"css.css\" rel=\"stylesheet\" type=\"text/css\">");
            out.println("<title>Servlet MainPage</title>");            
            out.println("</head>");
            out.println("<div id=\"header\">");
            MenuTools men = new MenuTools();
            men.menuButtons(out);
            out.println("</div>");
            out.println("<body>");
            
            MemberTools memT = new MemberTools();
            String user = request.getRemoteUser();
            
            out.print("<div class=\"partedDiv\">");
            if(request.isUserInRole("UregistrertStudent")){
            out.println("Du er ikke registrert i dette faget.<br>"
                    + "Få en foreleser til å registrere deg");
            }else{
             out.print("<h2> Velkommen, " + user + "</h2>");
             out.println("<h3>IS-109 Objektorientert Programmering</h3>"); 
             out.println("<p>Forelesere: </p>");
             memT.printMembersByRole("Foreleser", out);
             out.println("<p>Hjelpel�rere: </p>");
             memT.printAssistants(out);
             out.print("</div>");
             out.print("<div class=\"partedDiv\">");
             out.print("<h4>L�ringsutbytte:</h4>");
             out.println("<li>Kjenne hovedelementene i et objektorientert programmeringsspråk, og kunne bruke det til å skrive enkle programmer som bruker klasser uten arv </li>"
                     + "<li>Kunne skrive metoder med og uten parametere, med returverdier</li>"
                     + "<li>Kunne bruke if-setninger, løkker og tilordning</li>"
                     + "<li>Kunne bruke lister og arrayer</li>"
                     + "<li>Kjenne til og følge god programmeringsskikk (f.eks. dokumentasjon, testing og kodestandarder)</li>");
            }
            out.print("</div>");
            out.print("<div class=\"partedDiv\">");
            out.print("<h3>Siste kunngj�ringer</h3>"
                    + "<hr>");
            
            AnnouncementTools aT = new AnnouncementTools();
            aT.showLastThreeAnnouncements(out);
            out.print("</div>");
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
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(MainPage.class.getName()).log(Level.SEVERE, null, ex);
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
