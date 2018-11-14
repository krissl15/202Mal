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
        out.println("<div id=\"meny\">");
        out.println("<form action=\"MainPage\" method=\"post\">\n" +
            "<input type=\"Submit\" name=\"btnMainPage\" value=\"Hovedside\">  \n" +
            "</form>  \n");
        out.println("<form action=\"ModuleMenuServlet\" method=\"post\">\n" +
            "<input type=\"Submit\" name=\"btnModuleMenu\" value=\"Modulmeny\"> <br><br>  \n" +
            "</form>  \n");
        out.println("</div>");
    }
    
}
