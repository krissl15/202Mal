/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Delivery;

import Utilities.DbConnector;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Doffen
 */
public class DeliveryTools {

    /**
     *
     * @param deliveryText
     * @param date This should be the current time that the user registers the
     * delivery. YYYYMMDD
     * @param name
     * @param out
     * @throws SQLException
     */
    public void registerDelivery(String deliveryText, String date, String name, PrintWriter out) throws SQLException {
        String qDelivery = ("Insert into Innlevering (Innlevering_tekst, innlevering_dato, brukernavn)\n"
                + "values (?, ?, ?);");

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psDelivery = conn.prepareStatement(qDelivery)) {
            psDelivery.setString(1, deliveryText);
            psDelivery.setString(2, date);
            psDelivery.setString(3, name);

            psDelivery.executeUpdate();
        }

    }//method end

    public int getLastDeliveryByUser(String name, PrintWriter out) throws SQLException {
        String qModulKanal = ("select innlevering_id from innlevering where brukernavn=? order by innlevering_id desc limit 1;");
        Integer i = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psUpdateModulKanal = conn.prepareStatement(qModulKanal)) {
            psUpdateModulKanal.setString(1, name);

            ResultSet rsModulKanal = psUpdateModulKanal.executeQuery();
            while (rsModulKanal.next()) {
                i = rsModulKanal.getInt("innlevering_id");
            }

        }//end connection
        return i;
    }

    public String checkIfDelivered(String name, int moduleID, PrintWriter out) throws SQLException {
        String qStatus = "select mk_status from modulkanal where brukernavn=? and modul_id=?";
        String deliveryStatus = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(qStatus)) {
            psStatus.setString(1, name);
            psStatus.setInt(2, moduleID);

            try (ResultSet rsStatus = psStatus.executeQuery()) {
                while (rsStatus.next()) {
                    deliveryStatus = rsStatus.getString("mk_status");
                }
            }
        }//end connection
        return deliveryStatus;
    }//method end 

    public void setDeliveryToModulkanal(int deliveryID, int modulID, String userName, PrintWriter out) throws SQLException {

        String qDeliveryModulKanal = ("update modulkanal set innlevering_id=?, mk_status=\"Levert\" where modul_id=? and brukernavn=?");

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psDeliveryModulkanal = conn.prepareStatement(qDeliveryModulKanal)) {
            psDeliveryModulkanal.setInt(1, deliveryID);
            psDeliveryModulkanal.setInt(2, modulID);
            psDeliveryModulkanal.setString(3, userName);

            psDeliveryModulkanal.executeUpdate();
        }
    }

    public String checkIfDeliveryCorrected(String name, int moduleID, PrintWriter out) throws SQLException {
        String qStatus = "select mk_rettet_status from modulkanal where brukernavn=? and modul_id=?";
        String deliveryCheckedStatus = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(qStatus)) {
            psStatus.setString(1, name);
            psStatus.setInt(2, moduleID);

            try (ResultSet rsStatus = psStatus.executeQuery()) {
                while (rsStatus.next()) {
                    deliveryCheckedStatus = rsStatus.getString("mk_rettet_status");
                }
            }
        }//end connection
        return deliveryCheckedStatus;
    }

    /**
     *
     * @param name
     * @param out
     * @throws SQLException
     */
    public void listUnCorrectedDeliveries(PrintWriter out) throws SQLException {
        String qUncorrected = "select bruker.fornavn, bruker.etternavn, modul_innleveringstype, bruker.brukernavn, modulkanal.mk_status, modulkanal.mk_rettet_status, innlevering_id, modul.modul_navn, modul.modul_id\n" +
"from modulkanal\n" +
"inner join bruker on bruker.brukernavn = modulkanal.brukernavn\n" +
"inner join modul on modul.modul_id = modulkanal.modul_id\n" +
"where modulkanal.mk_rettet_status=\"Ikke rettet\" \n" +
"order by brukernavn";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psUncorrected = conn.prepareStatement(qUncorrected)) {

            try (ResultSet rsUncorrected = psUncorrected.executeQuery()) {
                while (rsUncorrected.next()) {
                    String userName = rsUncorrected.getString("brukernavn");
                    String firstName = rsUncorrected.getString("fornavn");
                    String surname = rsUncorrected.getString("etternavn");
                    String moduleName = rsUncorrected.getString("modul_navn");
                    String mkCorrectedStatus = rsUncorrected.getString("mk_rettet_status");
                    String mkDeliveredStatus = rsUncorrected.getString("mk_status");
                    String mkType = rsUncorrected.getString("modul_innleveringstype");
                    int moduleID = rsUncorrected.getInt("modul_id");

                    if((mkType.contains("Muntlig") && mkCorrectedStatus.contains("Ikke")) || (mkType.contains("Skriftlig") && mkDeliveredStatus.equals("Levert") && mkCorrectedStatus.contains("Ikke"))){
                   out.println("<div class=\"unCorrected\">");
                        String chosenPerson  = "<li><a href='GradingServlet?moduleID=%s&userName=%s&firstName=%s&lastName=%s'>%s %s %s %s</a> </li>"; 
                    out.format(chosenPerson, moduleID, userName,firstName,surname, moduleID, 
                            userName, firstName, surname);
                     out.println(" Modul:" + moduleName + ", " + mkCorrectedStatus);
                     out.println("</div>");
                     
                    }
                }

            }

        }
    }

    public String getDeliveryTextByModuleUser(String moduleID, String user, PrintWriter out) throws SQLException{
         String qDeliveryText = "select innlevering_tekst \n" +
"from innlevering\n" +
"inner join modulkanal on modulkanal.innlevering_id = innlevering.innlevering_id\n" +
"where modulkanal.brukernavn=? and modulkanal.modul_id=?;";
        String deliveryText = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psDeliveryText = conn.prepareStatement(qDeliveryText)) {
            psDeliveryText.setString(1, user);
            psDeliveryText.setString(2, moduleID);

            try (ResultSet rsDeliveryText = psDeliveryText.executeQuery()) {
                while (rsDeliveryText.next()) {
                    deliveryText = rsDeliveryText.getString("innlevering_tekst");
                }
            }
        }//end connection
        return deliveryText;
        
    }
            
}//class end 
