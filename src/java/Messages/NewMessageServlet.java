/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

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
 * @author Elias
 */
@WebServlet(name = "NewMessageServlet", urlPatterns = {"/NewMessageServlet"})
public class NewMessageServlet extends HttpServlet {

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
            MessageTools msg = new MessageTools();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link href=\"css.css\" rel=\"stylesheet\" type=\"text/css\">");           
            out.println("</head>");
            out.println("<div id=\"header\">");
            MenuTools men = new MenuTools();
            men.menuButtons(out);
            out.println("</div>");
            out.println("<body>");
            out.println("<h2>Ny melding</h2>");
            out.println("<div class=\"partedDiv\">");
            out.println("<form>"
                    + "<b><b>Mottaker</b><br><input type=\"text\" name=\"messageRecipient\" placeholder=\"Mottaker av meldingen...\"> <br><br>  \n"
                + "<b>Emne</b><br><input type=\"text\" name=\"messageTitle\" placeholder=\"Emne på meldingen...\"> <br><br>  \n"
                + "<b>Melding</b><br><textarea name=\"messageContent\" rows=\"5\" cols=\"50\" placeholder=\"Skriv din melding her..\"></textarea> <br><br> </div> \n"
                + "<input type=\"Submit\" name=\"btnSendMessage\" value=\"Send\"> \n"
                + "</form>  ");
            
            String btnSend = request.getParameter("btnSendMessage");
            if(btnSend.contains("Send")){
                
            LocalDate date = LocalDate.now();
            String recipient = request.getParameter("messageRecipient");
            String title = request.getParameter("messageTitle");
            String message = request.getParameter("messageContent");
            String messageSender = request.getRemoteUser();
            String stringDate = date.toString();
            
            
            
            msg.insertMessage(stringDate, recipient, title, message, messageSender, out);
            response.sendRedirect("InboxServlet");
            out.println("</div>");
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
            Logger.getLogger(NewMessageServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(NewMessageServlet.class.getName()).log(Level.SEVERE, null, ex);
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
