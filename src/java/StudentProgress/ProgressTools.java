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
        getUserPointbyModule(userName, moduleID, out);
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
        
        public void getUserPointbyModule(String userName, int moduleID, PrintWriter out) throws SQLException {
        String selectPoints = "SELECT tilbakemelding_poeng FROM tilbakemelding WHERE brukernavn =? AND modul_id =?";
        //String pointsModule = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psGetModuleName = conn.prepareStatement(selectPoints)) {
            psGetModuleName.setString(1, userName);
            psGetModuleName.setInt(2, moduleID);

            try (ResultSet rsModuleStatus = psGetModuleName.executeQuery()) {
                while (rsModuleStatus.next()) {
                    String pointsModule = rsModuleStatus.getString("tilbakemelding_poeng");
                    if (pointsModule != null){
                        out.println(pointsModule);
                    } else {
                        out.println("0");
                    }
                }
            }
        }//end connection
        //return pointsModule;
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
