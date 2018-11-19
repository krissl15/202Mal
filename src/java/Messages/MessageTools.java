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
    String brukernavn;
    String emne;
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
    
    public void showMottatt(String mottaker, PrintWriter out) throws SQLException{
        String innboks = "SELECT melding_dato, melding_emne, melding_innhold, brukernavn FROM melding WHERE melding_mottaker = ?;";
        
        DbConnector db = new DbConnector();
                try (Connection conn = db.getConnection(out)) {
                    try (PreparedStatement pstmt = conn.prepareStatement(innboks)) {
                        pstmt.setString(1, mottaker);
                        
                        ResultSet rsRegistered = pstmt.executeQuery();
                        while (rsRegistered.next()) { //iterator
                         String dato = rsRegistered.getString("melding_dato");
                         String emne = rsRegistered.getString("melding_emne");
                         String brukernavn = rsRegistered.getString("brukernavn");
                         String melding = rsRegistered.getString("melding_innhold");
                         out.format("<br>" + dato + "<br> Fra: " + brukernavn + "<br> Emne: " + emne + "<br> Innhold: " + melding + "<br>");
                         out.println("<form action=\"ReplyServlet\" method=\"post\">\n" + 
                            "<input type=\"hidden\" name=\"hdnName\" Value=\" " + brukernavn + "\">" + 
                            "<input type=\"hidden\" name=\"hdnEmne\" Value=\"" + emne + "\">" + 
                            "<input type=\"Submit\" name=\"btnReplyMessage\" value=\"Svar\"> <br> \n" +
                            "</form>  \n");
                         
                        }
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
    }

    public void showSendt(String avsender, PrintWriter out) throws SQLException{
        String utboks = "SELECT melding_dato, melding_emne, melding_innhold, melding_mottaker FROM melding WHERE brukernavn = ? ORDER BY melding_dato DESC;";
        DbConnector db = new DbConnector();
                try (Connection conn = db.getConnection(out)) {
                    try (PreparedStatement pstmt = conn.prepareStatement(utboks)) {
                        pstmt.setString(1, avsender);
                        
                        ResultSet rsRegistered = pstmt.executeQuery();
                        while (rsRegistered.next()) { //iterator
                         String dato = rsRegistered.getString("melding_dato");
                         String emne = rsRegistered.getString("melding_emne");
                         String brukernavn = rsRegistered.getString("melding_mottaker");
                         String melding = rsRegistered.getString("melding_innhold");
                        
                         out.format("<br>" + dato + "<br> Til: " + brukernavn + "<br> Emne: " + emne + "<br> Innhold: " + melding + "<br>");
                        
                    }
                    }catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
    }
}
