/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import Utilities.MenuTools;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author RetailAdmin
 */
@WebServlet(name = "ReplyServlet", urlPatterns = {"/ReplyServlet"})
public class ReplyServlet extends HttpServlet {

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
            String recipientField = request.getParameter("hdnName");
            String titleField = request.getParameter("hdnEmne");
            
            out.println("<form><h2>Svar på melding</h2><b><b>Mottaker</b><input type=\"text\" name=\"messageRecipient\" value=\"" + recipientField + "\"> <br><br>  \n"
                + "<b>Emne</b><input type=\"text\" name=\"messageTitle\" value=\"" + "RE: " + titleField + "\"> <br><br>  \n"
                + "<b>Melding</b> <input type=\"text\" name=\"messageContent\" placeholder=\"Skriv din melding her...\"> <br><br> </div> \n"
                + "<input type=\"Submit\" name=\"btnSendMessage\" value=\"Send\"> \n"
                + "</form>  ");
            
            String btnSendMessage = request.getParameter("btnSendMessage");
            if(btnSendMessage.contains("Send")){
                
            LocalDate date = LocalDate.now();
            String recipient = request.getParameter("messageRecipient");
            String title = request.getParameter("messageTitle");
            String message = request.getParameter("messageContent");
            String messageSender = request.getRemoteUser();
            String stringDate = date.toString();
            
            
            
            msg.insertMessage(stringDate, recipient, title, message, messageSender, out);
            response.sendRedirect("InboxServlet");
            out.println("</body>");
            out.println("</html>");
        }
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
            Logger.getLogger(ReplyServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ReplyServlet.class.getName()).log(Level.SEVERE, null, ex);
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

