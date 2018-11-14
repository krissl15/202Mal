/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Members;

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
 * @author Doffen, Elias
 * @TODO: Fjern kodeduplisering
 * 
 */
@WebServlet(name = "MemberSearchServlet", urlPatterns = {"/MemberSearchServlet"})
public class MemberSearchServlet extends HttpServlet {

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
            out.println("<title>MemberSearchServlet</title>");
            out.println("</head>");
            out.println("<body>");
            MemberTools memt = new MemberTools();
            String searchCheck = request.getParameter("search");
            /* Knapp som returnerer til hovedmenyen */
            out.println("<form action=\"MemberListServlet\" method=\"post\">\n" +
            "<input type=\"Submit\" name=\"btnTilbake\" value=\"Tilbake\"> <br><br>  \n" +
            "</form>  \n");
            
            if (request.isUserInRole("Foreleser")) {
                out.println("Forelesere: <br><br>");
                memt.searchUser(searchCheck, "Foreleser", out);
                out.println("<br><br>Assistenter:<br><br>");
                memt.searchUser(searchCheck, "Assistent",out);
                out.print("<br><br>Registrerte studenter: <br><br>");
                memt.searchUser(searchCheck, "RegistrertStudent", out);
                out.println("<br><br>Uregistrerte studenter:<br><br>");
                memt.searchUser(searchCheck, "UregistrertStudent",out);
            
            String change = request.getParameter("member"); //alle knappene heter det samme ("member")
            //String aCheck = request.getParameter("addCheck");
                //String rCheck = request.getParameter("removeCheck");

            
            if (change.contains("Registrer")) { //Sjekker om knappen er en "fjern" eller "Registrer

                    String name = change.substring(change.lastIndexOf(" ") + 1); // "name" blir siste ordet i valuen av knappen (change).
                   // if (aCheck.contains(name)) { //checkbox check
                        memt.registerStudent(name, out); //registrert brukeren når knappen blir trykket
                        memt.addToModulKanal(name, out);
                        response.sendRedirect("MemberSearchServlet"); //Oppdaterer siden ved å directe brukeren til samme side
                    //}
                }

                if (change.contains("Fjern")) { //Sjekker om knappen er en "fjern" eller "Registrer
                    String name = change.substring(change.lastIndexOf(" ") + 1); // "name" blir siste ordet i valuen av knappen (change).
                    //if (rCheck.contains(name)) {
                        memt.unRegister(name, out);
                        memt.removeFromModulKanal(name, out);
                        response.sendRedirect("MemberSearchServlet");
                    //}
                }
                
                 if(change.contains("Ta bort assistent")){ //Sjekker om knappen er en "fjern" eller "Registrer
                 String name = change.substring(change.lastIndexOf(" ")+1); // "name" blir siste ordet i valuen av knappen (change).
                 memt.unRegister(name, out);
                 response.sendRedirect("MemberSearchServlet");
                 
             }
             
             if(change.contains("Assistent")){
                 String name = change.substring(change.lastIndexOf(" ")+1); // "name" blir siste ordet i valuen av knappen (change).
                 memt.registerAssistent(name, out);
                 response.sendRedirect("MemberSearchServlet");
             }
        }
                
            else if(request.isUserInRole("RegistrertStudent")){ //Studenter ser kun registrerte brukere, assistenter og forelesere
                out.print("Forelesere <br><br>");
                 memt.printMembersByRole("Foreleser", out);
                out.println("<br><br>Assistenter<br><br>");
                 memt.printAssistants(out);
                out.print("<br><br>Registrerte brukere <br><br>");
                 memt.printRegisteredMembers(out);
            }
            
            else if (request.isUserInRole("Assistent")) {
                out.print("Forelesere <br><br>");
                 memt.printMembersByRole("Foreleser", out);
                out.println("<br><br>Assistenter<br><br>");
                 memt.printAssistants(out);
                out.print("<br><br>Registrerte brukere <br><br>");
                 memt.printRegisteredMembers(out);
            
            

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
        } catch (SQLException | NamingException ex) {
            Logger.getLogger(MemberListServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MemberListServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(MemberListServlet.class.getName()).log(Level.SEVERE, null, ex);
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

