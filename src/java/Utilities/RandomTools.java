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
public class RandomTools {
    
    public void homeButton(PrintWriter out){
        out.println("<form action=\"MainPage\" >\n" +
"<input type=\"Submit\" name=\"btnHome\" value=\"Hovedmeny\"> \n" +
"</form>  ");
    }//end homeButton
}
