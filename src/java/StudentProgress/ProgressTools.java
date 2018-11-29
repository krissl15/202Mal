/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StudentProgress;

import Module.ModuleTools;
import Utilities.DbConnector;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author phuonghapham
 */
public class ProgressTools {
        
   
    
    /**
     * Funksjon som viser status for alle moduler/innleveringer basert på
     * brukernavn
     *
     * @param userName
     * @param moduleID
     * @param out
     * @throws SQLException
     */
    /*
    public void printModulesByUsername(String userName, int modulID, PrintWriter out) throws SQLException {
        String selectModule = "SELECT tilbakemelding_id, modulkanal.modul_id, mk_status, mk_rettet_status, modul_navn, tilbakemelding_poeng, modul_max_poeng\n"
                + "FROM modulkanal\n"
                + "LEFT JOIN tilbakemelding ON tilbakemelding.modul_id = modulkanal.modul_id\n"
                + "INNER JOIN modul ON modul.modul_id = modulkanal.modul_id\n"
                + "WHERE modulkanal.brukernavn=?\n"
                + "AND modulkanal.modul_id=?\n"
                + "ORDER BY tilbakemelding_id\n"
                + "DESC LIMIT 1;";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psListModules = conn.prepareStatement(selectModule)) {
            psListModules.setString(1, userName);
            psListModules.setInt(2, modulID);
            ResultSet rsModulKanal = psListModules.executeQuery();
            while (rsModulKanal.next()) {
                int maxPoeng = rsModulKanal.getInt("modul_max_poeng");
                String modulRettet = rsModulKanal.getString("mk_rettet_status");
                String modulPoeng = rsModulKanal.getString("tilbakemelding_poeng");
                String modulName = rsModulKanal.getString("modul_navn");
                String modulStatus = rsModulKanal.getString("mk_status");
                if (modulPoeng == null) {
                    out.println(modulName + " " + modulStatus + " " + modulRettet + " -/" + maxPoeng + "<br>");
                } else if (modulPoeng != null) {
                    out.println(modulName + " " + modulStatus + " " + modulRettet + " " + modulPoeng + "/" + maxPoeng + "<br>");
                }
            }
        }
    }*/
    
    public void printModulesByUserName(String userName, int moduleID, PrintWriter out) throws SQLException{
        ModuleTools mt = new ModuleTools();
        out.println("<td class=\"moduleCol prog\">");
        out.println(getUserModuleName(userName, moduleID, out));
        out.println("</td>");
        out.println("<td class=\"deliveredCol prog\">");
        out.println(getUserDeliveredStatus(userName, moduleID, out));
        out.println("</td>");
        out.println("<td class=\"correctedCol prog\">");
        out.println(getUserCorrectedStatus(userName, moduleID, out));
        out.println("</td>");
        out.println("<td class=\"pointsCol prog\">");
        out.println(getUserPointbyModule(userName, moduleID, out));
        out.println("</td>");
        out.println("<td class=\"maxPointsCol prog\">");
        out.println("/" + mt.getMaxPoints(moduleID, out));
        out.println("</td>");
   }
    
    public String getUserModuleName(String userName, int moduleID, PrintWriter out) throws SQLException {
        String selectModuleName = "SELECT modul_navn FROM modulkanal INNER JOIN modul ON modul.modul_id = modulkanal.modul_id WHERE modulkanal.brukernavn=? AND modulkanal.modul_id=?;";
        String moduleName = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psGetModuleName = conn.prepareStatement(selectModuleName)) {
            psGetModuleName.setString(1, userName);
            psGetModuleName.setInt(2, moduleID);

            try (ResultSet rsModuleGoal = psGetModuleName.executeQuery()) {
                while (rsModuleGoal.next()) {
                    moduleName = rsModuleGoal.getString("modul_navn");
                }
            }
        }//end connection
        return moduleName;
    }
    
        public String getUserDeliveredStatus(String userName, int moduleID, PrintWriter out) throws SQLException {
        String selectModuleStatus = "SELECT mk_status FROM modulkanal WHERE modulkanal.brukernavn=? AND modulkanal.modul_id=?;";
        String moduleStatus = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psGetModuleName = conn.prepareStatement(selectModuleStatus)) {
            psGetModuleName.setString(1, userName);
            psGetModuleName.setInt(2, moduleID);

            try (ResultSet rsModuleStatus = psGetModuleName.executeQuery()) {
                while (rsModuleStatus.next()) {
                    moduleStatus = rsModuleStatus.getString("mk_status");
                }
            }
        }//end connection
        return moduleStatus;
    }
        
        public String getUserCorrectedStatus(String userName, int moduleID, PrintWriter out) throws SQLException {
        String selectCorrectedStatus = "SELECT mk_rettet_status FROM modulkanal WHERE modulkanal.brukernavn=? AND modulkanal.modul_id=?;";
        String correctedStatus = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psGetModuleName = conn.prepareStatement(selectCorrectedStatus)) {
            psGetModuleName.setString(1, userName);
            psGetModuleName.setInt(2, moduleID);

            try (ResultSet rsModuleStatus = psGetModuleName.executeQuery()) {
                while (rsModuleStatus.next()) {
                    correctedStatus = rsModuleStatus.getString("mk_rettet_status");
                }
            }
        }//end connection
        return correctedStatus;
    }
        
        public String getUserPointbyModule(String userName, int moduleID, PrintWriter out) throws SQLException {
        String selectPoints = "SELECT tilbakemelding_poeng FROM tilbakemelding WHERE brukernavn =? AND modul_id =?";
        String pointsModule = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psGetModuleName = conn.prepareStatement(selectPoints)) {
            psGetModuleName.setString(1, userName);
            psGetModuleName.setInt(2, moduleID);

            try (ResultSet rsModuleStatus = psGetModuleName.executeQuery()) {
                while (rsModuleStatus.next()) {
                    pointsModule = rsModuleStatus.getString("tilbakemelding_poeng");
                }
            }
        }//end connection
        return pointsModule;
    }

    /**
     * Kontaktinformasjon
     *
     * @param userName
     * @param out
     */
    public void printPerson(String userName, PrintWriter out) {
        String selectUser = "SELECT fornavn, etternavn, epost, telefon FROM bruker WHERE brukernavn=?";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psPrintPerson = conn.prepareStatement(selectUser)) {
            psPrintPerson.setString(1, userName);
            ResultSet rsUser = psPrintPerson.executeQuery();
            while (rsUser.next()) {
                String firstName = rsUser.getString("fornavn");
                String lastName = rsUser.getString("etternavn");
                String mail = rsUser.getString("epost");
                int tlf = rsUser.getInt("telefon");
                out.println("<b>Fornavn: </b>" + firstName);
                out.println("<br>");
                out.println("<b>Etternavn: </b>" + lastName);
                out.println("<br>");
                out.println("<b>E-post: </b>" + mail);
                out.println("<br>");
                out.println("<b>Telefon: </b>" + tlf);
                out.println("<form action=\"ReplyServlet\" method=\"post\">\n"
                        + "<input type=\"hidden\" name=\"hdnName\" Value=\" " + userName + "\">"
                        + "<input type=\"Submit\" name=\"btnReplyMessage\" value=\"Send melding\"> <br> \n"
                        + "</form>  \n");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProgressTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
