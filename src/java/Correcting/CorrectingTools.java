/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Correcting;

import Utilities.DbConnector;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 *
 * @author Doffen
 */
public class CorrectingTools {
    
       public void setGrade(String comment, String points, String sender, String student, String moduleID, PrintWriter out) throws SQLException {
      
           String qGrade = "insert into tilbakemelding (tilbakemelding_innhold, tilbakemelding_poeng, tilbakemelding_dato, avsender, brukernavn, modul_id)\n" +
"values (?, ?, ?, ?, ?, ?);";
             String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()); //kanskje denne blir needed

           DbConnector db = new DbConnector();
           try (Connection conn = db.getConnection(out);
                   PreparedStatement psGrade = conn.prepareStatement(qGrade)) {

               psGrade.setString(1, comment);
               psGrade.setString(2, points);
               psGrade.setString(3, date);
               psGrade.setString(4, sender);
               psGrade.setString(5, student);
               psGrade.setString(6, moduleID);

                psGrade.executeUpdate();
                
        }  
    }
       
       
       
       public void setRettetAndLevert(String userName, String moduleID, PrintWriter out) throws SQLException{
           
           String qGradedDelivered = "update modulkanal set mk_status=\"Levert\",mk_rettet_status=\"Rettet\"  where modul_id=? and brukernavn=?;";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psGradedDelivered = conn.prepareStatement(qGradedDelivered)) {
            psGradedDelivered.setString(1, moduleID);
            psGradedDelivered.setString(2, userName);

            psGradedDelivered.executeUpdate();
        }
           
           
       }
}//class end
