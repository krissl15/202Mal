/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import Utilities.DbConnector;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Elias
 */
public class MessageTools {
  
    public void insertMessage(String date, String messageRecipient, String messageTitle,
            String messageContent, String messageSender, PrintWriter out) throws SQLException {
            
            String messaging = "INSERT INTO melding(melding_dato, melding_mottaker, melding_emne, melding_innhold, brukernavn)"
                    + "VALUES(?, ?, ?, ?, ?);";

                DbConnector db = new DbConnector();
                try (Connection conn = db.getConnection(out)) {
                    try (PreparedStatement pstmt = conn.prepareStatement(messaging)) {
                        pstmt.setString(1, date);
                        pstmt.setString(2, messageRecipient);
                        pstmt.setString(3, messageTitle);
                        pstmt.setString(4, messageContent);
                        pstmt.setString(5, messageSender);

                        pstmt.executeUpdate();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
    }
    
    public void showRecieved(String recipient, PrintWriter out) throws SQLException{
        String qInbox = "SELECT melding_dato, melding_emne, melding_innhold, brukernavn FROM melding WHERE melding_mottaker = ?;";
        
        DbConnector db = new DbConnector();
                try (Connection conn = db.getConnection(out)) {
                    try (PreparedStatement psInbox = conn.prepareStatement(qInbox)) {
                        psInbox.setString(1, recipient);
                        
                        ResultSet rsInbox = psInbox.executeQuery();
                        while (rsInbox.next()) { //iterator
                         String date = rsInbox.getString("melding_dato");
                         String topic = rsInbox.getString("melding_emne");
                         String userName = rsInbox.getString("brukernavn");
                         String message = rsInbox.getString("melding_innhold");
                         out.format("<br>" + date + "<br> Fra: " + userName + "<br> Emne: " + topic + "<br> Innhold: " + message + "<br>");
                         out.println("<form action=\"ReplyServlet\" method=\"post\">\n" + 
                            "<input type=\"hidden\" name=\"hdnName\" Value=\" " + userName + "\">" + 
                            "<input type=\"hidden\" name=\"hdnEmne\" Value=\"" + topic + "\">" + 
                            "<input type=\"Submit\" name=\"btnReplyMessage\" value=\"Svar\"> <br> \n" +
                            "</form>  \n");
                         
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
    }

    public void showSendt(String sender, PrintWriter out) throws SQLException{
        String qOutBox = "SELECT melding_dato, melding_emne, melding_innhold, melding_mottaker FROM melding WHERE brukernavn = ? ORDER BY melding_dato DESC;";
        DbConnector db = new DbConnector();
                try (Connection conn = db.getConnection(out)) {
                    try (PreparedStatement pstmt = conn.prepareStatement(qOutBox)) {
                        pstmt.setString(1, sender);
                        
                        ResultSet rsRegistered = pstmt.executeQuery();
                        while (rsRegistered.next()) { //iterator
                         String messageDate = rsRegistered.getString("melding_dato");
                         String topic = rsRegistered.getString("melding_emne");
                         String userName = rsRegistered.getString("melding_mottaker");
                         String message = rsRegistered.getString("melding_innhold");
                        
                         out.format("<br>" + messageDate + "<br> Til: " + userName + "<br> Emne: " + topic + "<br> Innhold: " + message + "<br>");
                        
                    }
                    }catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
    }
}
