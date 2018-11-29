/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Announcement;


import Announcement.AnnouncementTools;
import Utilities.MenuTools;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
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
 * @author brage
 */
@WebServlet(name = "NewAnnouncementServlet", urlPatterns = {"/NewAnnouncementServlet"})
public class NewAnnouncementServlet extends HttpServlet {

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
            AnnouncementTools at = new AnnouncementTools();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewAnnouncement</title>");            
            out.println("</head>");
            out.println("<div id=\"header\">");
            MenuTools men = new MenuTools();
            men.menuButtons(out);
            out.println("</div>");
            out.println("<h2>Ny kunngjøring</h2>");
            out.println("<div class=\"partedDiv\">");
            out.println("<body>");
            out.println("<form>"
                    + "<b>Tittel</b><br><input type=\"text\" name=\"AnnouncementTitle\" placeholder=\"Legg til tittel\"> <br><br>  \n"
                    + "<b>Beskjed</b><br><textarea name=\"AnnouncementText\" rows=\"5\" cols=\"50\" placeholder=\"Skriv beskjeden din her..\"></textarea> <br><br>  \n"
                    + "<input type=\"Submit\" name=\"btnSendMessage\" value=\"Publiser\"> \n"
                    + "</form>");
         
            String btnSend = request.getParameter("btnSendMessage");
            if(btnSend.contains("Publiser")){
                
            String announceTitle = request.getParameter("AnnouncementTitle");
            String announceText = request.getParameter("AnnouncementText");
            LocalDate announceDate = LocalDate.now();
            String strAnnounceDate = announceDate.toString();
            String userName = request.getRemoteUser();
            
            at.insertAnnouncement(announceTitle, announceText, strAnnounceDate, userName, out);
            response.sendRedirect("AnnouncementOverviewServlet");
            out.print("</div>");
            
            
            }
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
            Logger.getLogger(NewAnnouncementServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NewAnnouncementServlet.class.getName()).log(Level.SEVERE, null, ex);
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
