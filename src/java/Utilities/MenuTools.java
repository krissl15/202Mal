/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.PrintWriter;

/**
 *
 * @author Doffen
 */
public class MenuTools {
    public void menuButtons(PrintWriter out){
        out.println("<link href=\"css.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("<div id=\"sideMenu\">");
        out.println("<form action=\"MainPage\" method=\"POST\">\n" +
                    "<button id=\"logo\" style=\"border:none\"></button>" +
                    "  <button id=\"btnMainPage\" style=\"border:none\">Hovedside</button>\n" +
                    "  </form>");
        out.println("  <form action=\"StudentProgressServlet\" method=\"POST\">\n" +
                    "    <button id=\"btnProfile\" style=\"border:none\">Profil</button>\n" +
                    "    </form>");
        out.println("<form action=\"ModuleMenuServlet\" method=\"POST\">\n" +
                    "    <button id=\"btnModuleMenu\" style=\"border:none\">Moduler</button>\n" +
                    "    </form>");
        out.println("  <form action=\"InboxServlet\" method=\"POST\">\n" +
                    "    <button id=\"btnInbox\" style=\"border:none\">Innboks</button>\n" +
                    "    </form>");
        out.println("  <form action=\"MemberListServlet\" method=\"POST\">\n" +
                    "    <button id=\"btnMemberList\" style=\"border:none\">Medlemmer</button>\n" +
                    "    </form>");
        out.println("<form action=\"index.html\" method=\"POST\">"
                + "<button id=\"btnSignOut\" style=\"border:none\">Logg ut</button>"
                + "</form>");
        out.println("</div>");
    }
    
}
