/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Utilities.StudentTools;
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
 * @author Doffen
 */
@WebServlet(name = "Registered", urlPatterns = {"/Registered"})
public class Registered extends HttpServlet {

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
            out.println("<title>Servlet Registered</title>");
            out.println("</head>");
            out.println("<body>");

            String firstName = request.getParameter("textFName");
            String surName = request.getParameter("textSName");
            String username = request.getParameter("userName");
            String password = request.getParameter("textPass");
            String passwordCheck = request.getParameter("passCheck");
            
            int lengthFirstName = firstName.length();
            int lengthSurName = surName.length();
            int lengthUserName = username.length();
            int lengthPassword = password.length();
            int passCheckLength = passwordCheck.length();

            if (lengthFirstName < 1 || lengthPassword < 1 || lengthSurName <1 || passCheckLength < 1 || lengthUserName < 1) { //noe er ikke fyllt ut 
                out.println("<h3> Du må fylle ut <FONT COLOR=\"RED\"> alle </FONT> boksene for å kunne registrere deg" + "</h3>");
            } else if (password.equals(passwordCheck) != true) { //passordene er ikke like
                    out.println("<br> <h3> <FONT COLOR=\"RED\">Error</FONT>, du må ha likt passord!!!" + "</h3>");
                    out.println("<form action='http://localhost:8084/hello/'>");
                    out.println("<br> <input type= \"submit\" value= \"Tilbake\" name= \"Tilbake\">");
                    out.println("</form>");
                } else if (password.equals(passwordCheck)) { //alt er OK
                    out.println("<br> <h3> Navn: " + firstName + " " + surName + "</h3>");
                    
                    StudentTools st = new StudentTools();
                  
                    st.registerStudent(firstName, surName, password, out);
                    
                    
                    
                    
                } 

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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Registered.class.getName()).log(Level.SEVERE, null, ex);
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Registered.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
