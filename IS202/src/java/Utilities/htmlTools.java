/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import static java.lang.System.out;

/**
 *
 * @author Doffen
 */
public class htmlTools {
    
    public void header(){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet insertVlet</title>");            
            out.println("</head>");
            out.println("<body>");
    }
    
    public void headerEnd(){
        out.println("</body>");
            out.println("</html>");
    }
}
