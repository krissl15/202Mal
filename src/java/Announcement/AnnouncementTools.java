/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Announcement;

import Utilities.DbConnector;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author MartinEileraas
 */
public class AnnouncementTools {
    
    public void showAnnouncement(String StringID, PrintWriter out) throws SQLException {
        String sql = "select beskjed_tittel, beskjed_innhold, beskjed_dato, brukernavn from beskjeder where beskjed_id = ?;";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, StringID);
                
                ResultSet rsRegistered = pstmt.executeQuery();
                while (rsRegistered.next()) { //iterator
                String beskjedTittel = rsRegistered.getString("beskjed_tittel");
                String beskjedText = rsRegistered.getString("beskjed_innhold");
                String brukernavn = rsRegistered.getString("brukernavn");
                String beskjed_dato = rsRegistered.getString("beskjed_dato");
                out.println("<br> " + beskjedTittel + "<br>" + beskjedText + "<br><br>" + brukernavn + "<br>" + beskjed_dato);
                }
    }
        }
    }
    
    public void showAllAnnouncement(PrintWriter out) throws SQLException {
        String sql = "select beskjed_id, beskjed_tittel, beskjed_innhold, beskjed_dato, brukernavn from beskjeder order by beskjed_id desc";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                ResultSet rsRegistered = pstmt.executeQuery();
                while (rsRegistered.next()) { //iterator
                    String beskjedID = rsRegistered.getString("beskjed_id");
                    String beskjedTittel = rsRegistered.getString("beskjed_tittel");
                    String brukernavn = rsRegistered.getString("brukernavn");
                    String beskjed_dato = rsRegistered.getString("beskjed_dato");
                    out.format("<h3>" + beskjedTittel + "</h3>"+ "Publisert av: " + brukernavn + "<br>" + beskjed_dato + "<br>");
                    out.println("<form action=\"AnnouncementPageServlet\" method=\"post\">\n" + 
                            "<input type=\"hidden\" name=\"hdnID\" Value=\" " +beskjedID+ "\">" + 
                            "<input type=\"Submit\" name=\"btnButtonBeskjed\" value=\"se mer\"> <br><br>\n" +
                            "</form>  \n");
                   
                    
                }
            }
        }
    }

    public void show3Announcement(PrintWriter out) throws SQLException {
        String sql = "select beskjed_id, beskjed_tittel, beskjed_innhold, beskjed_dato, brukernavn from beskjeder order by beskjed_id desc limit 3;";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                ResultSet rsRegistered = pstmt.executeQuery();
                while (rsRegistered.next()) { //iterator
                    String beskjedTittel = rsRegistered.getString("beskjed_tittel");
                    String beskjedText = rsRegistered.getString("beskjed_innhold");
                    String brukernavn = rsRegistered.getString("brukernavn");
                    String beskjed_dato = rsRegistered.getString("beskjed_dato");
                    out.format("<h3>" + beskjedTittel + "</h3>" + beskjedText + "<br> Publisert av: " + brukernavn + "<br>" + beskjed_dato + "<br> <br>");
                    
                }
            }
        }
    }

    /**
     *
     * @param beskjed_tittel
     * @param beskjed_innhold
     * @param beskjed_dato
     * @param brukernavn
     * @param out
     * @throws SQLException
     */
    public void insertAnnouncement(String beskjed_tittel, String beskjed_innhold,
            String beskjed_dato, String brukernavn, PrintWriter out) throws SQLException {
        String sql = "INSERT INTO beskjeder(beskjed_tittel, beskjed_innhold, beskjed_dato, brukernavn) VALUES(?, ?, ?, ?);";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, beskjed_tittel);
                pstmt.setString(2, beskjed_innhold);
                pstmt.setString(3, beskjed_dato);
                pstmt.setString(4, brukernavn);

                pstmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }

    }
    
    public void deleteAnnouncement (int AnnouncementID, PrintWriter out) throws SQLException {
      String deleteAnnouncement = "delete from beskjeder where beskjed_id=?;";
      
      DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
              PreparedStatement psDeleteAnnouncement = conn.prepareStatement(deleteAnnouncement)) {
            psDeleteAnnouncement.setInt(1, AnnouncementID);
            psDeleteAnnouncement.executeUpdate();
        }
    }
    public String getTitle(int AnnouncementID, PrintWriter out) throws SQLException {
        String psAnnouncementTitle = "select beskjed_tittel from beskjeder where beskjed_id=?";
        String announcementTitle = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(psAnnouncementTitle)) {
            psStatus.setInt(1, AnnouncementID);

            try (ResultSet rsAnnouncementTitle = psStatus.executeQuery()) {
                while (rsAnnouncementTitle.next()) {
                    announcementTitle = rsAnnouncementTitle.getString("beskjed_tittel");
                }
            }
        }//end connection
        return announcementTitle;
    }

    public String getText(int AnnouncementID, PrintWriter out) throws SQLException {
        String psAnnouncementText = "select beskjed_innhold from beskjeder where beskjed_id=?";
        String announcementText = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(psAnnouncementText)) {
            psStatus.setInt(1, AnnouncementID);

            try (ResultSet rsAnnouncementText = psStatus.executeQuery()) {
                while (rsAnnouncementText.next()) {
                    announcementText = rsAnnouncementText.getString("beskjed_innhold");
                }
            }
        }//end connection
        return announcementText;
    }
    public String getDate(int AnnouncementID, PrintWriter out) throws SQLException {
        String psAnnouncementDate = "select beskjed_dato from modul where modul_id=?";
        String AnnouncementDate = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(psAnnouncementDate)) {
            psStatus.setInt(1, AnnouncementID);

            try (ResultSet rsAnnouncementDate = psStatus.executeQuery()) {
                while (rsAnnouncementDate.next()) {
                    AnnouncementDate = rsAnnouncementDate.getString("beskjed_dato");
                }
            }
        }//end connection
        return AnnouncementDate;
}
}
