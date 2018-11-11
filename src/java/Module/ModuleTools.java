package Module;

import Utilities.DbConnector;
import java.io.*;
import java.sql.*;

/**
 *
 * @author Doffen
 */
public class ModuleTools {

    /**
     *
     * @param modul_id
     * @param modul_navn
     * @param modul_læringsmål
     * @param modul_tekst
     * @param modul_status
     * @param modul_fristdato
     * @param out
     * @throws SQLException
     */
    public void insertModule(int modulID, String modul_navn, String modul_goal,
            String modul_tekst, String modul_status, int modul_fristdato, PrintWriter out) throws SQLException {
        String sql = "INSERT INTO slit.modul(modul_id, modul_navn, modul_goal, modul_tekst, "
                + "modul_status, modul_fristdato) VALUES(?,?,?,?,?,?)";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, modulID);
                pstmt.setString(2, modul_navn);
                pstmt.setString(3, modul_goal);
                pstmt.setString(4, modul_tekst);
                pstmt.setString(5, modul_status);
                pstmt.setInt(6, modul_fristdato);

                pstmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    /**
     *
     * @param moduleID
     * @param out
     * @throws SQLException
     */
    public void showModule(int moduleID, PrintWriter out) throws SQLException {
        out.println("Modul navn: " + getModuleName(moduleID, out)+ "<br><br>");
        out.println("Målet: "  + getGoal(moduleID, out)+ "<br><br>");
        out.println(getText(moduleID, out)+ "<br><br>");
        out.println("Innleveringstype: " + getType(moduleID, out)+ "<br><br>");
        out.println("Status: " + getStatus(moduleID, out)+ "<br><br>");
        out.println("Fristdato: " + getDate(moduleID, out) + "<br><br>");
    }//try en

                //try end
            //showmodule end
    
   
            

    

    public String getModuleName(int moduleID, PrintWriter out) throws SQLException {
        String psModulNavn = "select modul_navn from modul where modul_id=?";
        String modulNavn = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(psModulNavn)) {
            psStatus.setInt(1, moduleID);

            try (ResultSet rsModulNavn = psStatus.executeQuery()) {
                while (rsModulNavn.next()) {
                    modulNavn = rsModulNavn.getString("modul_navn");
                }
            }
        }//end connection
        return modulNavn;
    }

    public String getGoal(int moduleID, PrintWriter out) throws SQLException {
        String psModuleGoal = "select modul_goal from modul where modul_id=?";
        String moduleGoal = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(psModuleGoal)) {
            psStatus.setInt(1, moduleID);

            try (ResultSet rsModuleGoal = psStatus.executeQuery()) {
                while (rsModuleGoal.next()) {
                    moduleGoal = rsModuleGoal.getString("modul_goal");
                }
            }
        }//end connection
        return moduleGoal;
    }

    public String getText(int moduleID, PrintWriter out) throws SQLException {
        String psModuleText = "select modul_tekst from modul where modul_id=?";
        String moduleText = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(psModuleText)) {
            psStatus.setInt(1, moduleID);

            try (ResultSet rsModuleText = psStatus.executeQuery()) {
                while (rsModuleText.next()) {
                    moduleText = rsModuleText.getString("modul_tekst");
                }
            }
        }//end connection
        return moduleText;
    }

    /**
     * Returnerer en string som forteller om modulen er muntlig/innlevering
     *
     * @param moduleID
     * @param out
     * @return
     * @throws SQLException
     */
   

    public String getStatus(int moduleID, PrintWriter out) throws SQLException {
        String psModuleStatus = "select modul_status from modul where modul_id=?";
        String moduleStatus = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(psModuleStatus)) {
            psStatus.setInt(1, moduleID);

            try (ResultSet rsModuleStatus = psStatus.executeQuery()) {
                while (rsModuleStatus.next()) {
                    moduleStatus = rsModuleStatus.getString("modul_status");
                }
            }
        }//end connection
        return moduleStatus;
    }

    public String getType(int moduleID, PrintWriter out) throws SQLException {
        String psModuleType = "select modul_innleveringstype from modul where modul_id=?";
        String moduleType = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(psModuleType)) {
            psStatus.setInt(1, moduleID);

            try (ResultSet rsModuleStatus = psStatus.executeQuery()) {
                while (rsModuleStatus.next()) {
                    moduleType = rsModuleStatus.getString("modul_innleveringstype");
                }
            }
        }//end connection
        return moduleType;
    }

    public String getDate(int moduleID, PrintWriter out) throws SQLException {
        String psModuleDate = "select modul_fristdato from modul where modul_id=?";
        String moduleDate = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(psModuleDate)) {
            psStatus.setInt(1, moduleID);

            try (ResultSet rsModuleDate = psStatus.executeQuery()) {
                while (rsModuleDate.next()) {
                    moduleDate = rsModuleDate.getString("modul_fristdato");
                }
            }
        }//end connection
        return moduleDate;
    }

    /**
     *
     * @param name
     * @param moduleID
     * @param out
     * @return
     * @throws SQLException
     */
    /**
     *
     * @param modul_id
     * @param out
     * @throws SQLException
     */
    public void deleteModule(int modul_id, PrintWriter out) throws SQLException {
        String deleteModule = "DELETE FROM slit.modul WHERE Modul_id =?";
        String deleteModulKanal = "delete from slit.modulkanal where modul_id=?";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psDeleteModule = conn.prepareStatement(deleteModule);
                PreparedStatement psDeleteModulKanal = conn.prepareStatement(deleteModulKanal)) {
            psDeleteModulKanal.setInt(1, modul_id);
            psDeleteModulKanal.executeUpdate();

            psDeleteModule.setInt(1, modul_id);
            psDeleteModule.executeUpdate();
        }
    }

    public void updateModule(int modulID, String modul_navn, String modul_goal,
            String modul_tekst, String modul_innleveringstype, String modul_status, String modul_fristdato, int oldModulId, PrintWriter out) throws SQLException {
        String upd = "UPDATE slit.modul SET modul_id=?, modul_navn =?, modul_goal=?, "
                + "modul_tekst=?, modul_innleveringstype=?, modul_status=?, modul_fristdato=? WHERE modul_id=?";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement updModule = conn.prepareStatement(upd)) {
            updModule.setInt(1, modulID);
            updModule.setString(2, modul_navn);
            updModule.setString(3, modul_goal);
            updModule.setString(4, modul_tekst);
            updModule.setString(5, modul_innleveringstype);
            updModule.setString(6, modul_status);
            updModule.setString(7, modul_fristdato);
            updModule.setInt(8, oldModulId);

            updModule.executeUpdate();

        } // end try     // end try     
        catch (SQLException ex) {
            out.println("Ikke fått oppdatert modul " + ex);
        }
    }

}//class end
