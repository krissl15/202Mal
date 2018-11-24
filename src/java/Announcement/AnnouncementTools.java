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
    
    public void showAnnouncement(String announcementID, PrintWriter out) throws SQLException {
        String qAnnounce = "select beskjed_tittel, beskjed_innhold, beskjed_dato, brukernavn from beskjeder where beskjed_id = ?;";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement psAnnounce = conn.prepareStatement(qAnnounce)) {
                psAnnounce.setString(1, announcementID);
                
                ResultSet rsAnnounce = psAnnounce.executeQuery();
                while (rsAnnounce.next()) { //iterator
                String announceTitle = rsAnnounce.getString("beskjed_tittel");
                String announceText = rsAnnounce.getString("beskjed_innhold");
                String userName = rsAnnounce.getString("brukernavn");
                String announceDate = rsAnnounce.getString("beskjed_dato");
                out.println("<br> " + announceTitle + "<br>" + announceText + "<br><br>" + userName + "<br>" + announceDate);
                }
    }
        }
    }
    
    public void showAllAnnouncement(PrintWriter out) throws SQLException {
        String qAllAnnounce = "select beskjed_id, beskjed_tittel, beskjed_innhold, beskjed_dato, brukernavn from beskjeder order by beskjed_id desc";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement psAllAnnounce = conn.prepareStatement(qAllAnnounce)) {

                ResultSet rsAllAnnounce = psAllAnnounce.executeQuery();
                while (rsAllAnnounce.next()) { //iterator
                    String announceID = rsAllAnnounce.getString("beskjed_id");
                    String announceTitle = rsAllAnnounce.getString("beskjed_tittel");
                    String userName = rsAllAnnounce.getString("brukernavn");
                    String announceDate = rsAllAnnounce.getString("beskjed_dato");
                    out.format("<h3>" + announceTitle + "</h3>"+ "Publisert av: " + userName + "<br>" + announceDate + "<br>");
                    out.println("<form action=\"AnnouncementPageServlet\" method=\"post\">\n" + 
                            "<input type=\"hidden\" name=\"hdnID\" Value=\" " +announceID+ "\">" + 
                            "<input type=\"Submit\" name=\"btnButtonBeskjed\" value=\"se mer\"> <br><br>\n" +
                            "</form>  \n");
                   
                    
                }
            }
        }
    }

    public void showLastThreeAnnouncements(PrintWriter out) throws SQLException {
        String qThreeAnnounce = "select beskjed_id, beskjed_tittel, beskjed_innhold, beskjed_dato, brukernavn from beskjeder order by beskjed_id desc limit 3;";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement psLastThree = conn.prepareStatement(qThreeAnnounce)) {

                ResultSet rsLastThree = psLastThree.executeQuery();
                while (rsLastThree.next()) { //iterator
                    String announceTitle = rsLastThree.getString("beskjed_tittel");
                    String announceText = rsLastThree.getString("beskjed_innhold");
                    String userName = rsLastThree.getString("brukernavn");
                    String announceDate = rsLastThree.getString("beskjed_dato");
                    out.format("<h3>" + announceTitle + "</h3>" + announceText + "<br> Publisert av: " + userName + "<br>" + announceDate + "<br> <br>");
                    
                }
            }
        }
    }

    /**
     * 
     * @param announceTitle
     * @param announceText
     * @param announceDate
     * @param userName
     * @param out
     * @throws SQLException 
     */
    public void insertAnnouncement(String announceTitle, String announceText,
            String announceDate, String userName, PrintWriter out) throws SQLException {
        String qInsert = "INSERT INTO beskjeder(beskjed_tittel, beskjed_innhold, beskjed_dato, brukernavn) VALUES(?, ?, ?, ?);";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement psInsert = conn.prepareStatement(qInsert)) {
                psInsert.setString(1, announceTitle);
                psInsert.setString(2, announceText);
                psInsert.setString(3, announceDate);
                psInsert.setString(4, userName);

                psInsert.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

    public String getTitle(int AnnouncementID, PrintWriter out) throws SQLException {
        String qAnnouncementTitle = "select beskjed_tittel from beskjeder where beskjed_id=?";
        String announcementTitle = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psAnnouncementTitle = conn.prepareStatement(qAnnouncementTitle)) {
            psAnnouncementTitle.setInt(1, AnnouncementID);

            try (ResultSet rsAnnouncementTitle = psAnnouncementTitle.executeQuery()) {
                while (rsAnnouncementTitle.next()) {
                    announcementTitle = rsAnnouncementTitle.getString("beskjed_tittel");
                }
            }
        }//end connection
        return announcementTitle;
    }

    public String getText(int AnnouncementID, PrintWriter out) throws SQLException {
        String qAnnouncementText = "select beskjed_innhold from beskjeder where beskjed_id=?";
        String announcementText = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psAnnouncementText = conn.prepareStatement(qAnnouncementText)) {
            psAnnouncementText.setInt(1, AnnouncementID);

            try (ResultSet rsAnnouncementText = psAnnouncementText.executeQuery()) {
                while (rsAnnouncementText.next()) {
                    announcementText = rsAnnouncementText.getString("beskjed_innhold");
                }
            }
        }//end connection
        return announcementText;
    }
    public String getDate(int AnnouncementID, PrintWriter out) throws SQLException {
        String qAnnouncementDate = "select beskjed_dato from modul where modul_id=?";
        String AnnouncementDate = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psAccouncementDate = conn.prepareStatement(qAnnouncementDate)) {
            psAccouncementDate.setInt(1, AnnouncementID);

            try (ResultSet rsAnnouncementDate = psAccouncementDate.executeQuery()) {
                while (rsAnnouncementDate.next()) {
                    AnnouncementDate = rsAnnouncementDate.getString("beskjed_dato");
                }
            }
        }//end connection
        return AnnouncementDate;
}
}