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
    public void insertModule(int moduleID, String moduleName, String moduleGoal,
            String moduleText, String modulePoints, String moduleDeliveryType, String moduleStatus, int moduleDate, PrintWriter out) throws SQLException {
        String sql = "insert into modul (modul_id, modul_navn, modul_goal, modul_tekst, modul_max_poeng, modul_innleveringstype, modul_status, modul_fristdato)\n"
                + "values (?, ?, ?, ?, ?, ?, ?, ?);";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, moduleID);
                pstmt.setString(2, moduleName);
                pstmt.setString(3, moduleGoal);
                pstmt.setString(4, moduleText);
                pstmt.setString(5, modulePoints);
                pstmt.setString(6, moduleDeliveryType);
                pstmt.setString(7, moduleStatus);
                pstmt.setInt(8, moduleDate);

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
        out.println("Modul navn: " + getModuleName(moduleID, out) + "<br><br>");
        out.println("Målet: " + getGoal(moduleID, out) + "<br><br>");
        out.println(getText(moduleID, out) + "<br><br>");
        out.println("Maks poeng: " + getMaxPoints(moduleID, out) +"<br><br>");
        out.println("Innleveringstype: " + getType(moduleID, out) + "<br><br>");
        out.println("Status: " + getStatus(moduleID, out) + "<br><br>");
        out.println("Fristdato: " + getDate(moduleID, out) + "<br><br>");
    }//try en
    
    /**
     * 
     * @param moduleID
     * @param out
     * @throws SQLException 
     */
    public void viewAllModules(int moduleID, PrintWriter out) throws SQLException{
                out.println(getModuleName(moduleID, out) + " " + getDate(moduleID, out) + " " + getDelivered(moduleID, out) + " " + getTotalRegistered(out) + " " + getEvaluated(moduleID, out));
    }
    
    /**
     * 
     * @param moduleID
     * @param out
     * @return
     * @throws SQLException 
     */
    public String getDelivered(int moduleID, PrintWriter out) throws SQLException{
        String checkDelivered = "SELECT COUNT(mk_status), modul_id FROM modulkanal WHERE mk_status =\"levert\" AND modul_id = ? GROUP BY modul_id;";
        String antall = null;
        String ifNull = "0";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psDelivered = conn.prepareStatement(checkDelivered)) {
                psDelivered.setInt(1, moduleID);
            try (ResultSet rsModulNavn = psDelivered.executeQuery()) {
                while (rsModulNavn.next()) {
                    antall = rsModulNavn.getString("COUNT(mk_status)");
                }
            }
        }
        if (antall ==null){
            return ifNull;
        }
        return antall;   
    }
    
    /**
     * 
     * @param out
     * @return
     * @throws SQLException 
     */
    public String getTotalRegistered(PrintWriter out) throws SQLException{
        String countRegistered ="SELECT COUNT(rolle) FROM bruker_rolle WHERE rolle =\"RegistrertStudent\";";
        String antall = null;
        String ifNull = "0";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psTotal = conn.prepareStatement(countRegistered)) {
                //psTotal.setInt(1, moduleID);
        try (ResultSet rsModulNavn = psTotal.executeQuery()) {
                while (rsModulNavn.next()) {
                    antall = rsModulNavn.getString("COUNT(rolle)");
                }
            }
        }
        if (antall==null){
            return ifNull;
        }
        return antall;
    }
        
    /**
     * 
     * @param moduleID
     * @param out
     * @return
     * @throws SQLException 
     */
    public String getEvaluated(int moduleID, PrintWriter out) throws SQLException {
        String countEvaluated = "SELECT COUNT(mk_rettet_status), modul_id FROM modulkanal WHERE mk_rettet_status=\"Rettet\" AND modul_id = ? GROUP BY modul_id;";
        String antall = null;
        String ifNull = "0";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psEvaluate = conn.prepareStatement(countEvaluated)) {
                psEvaluate.setInt(1, moduleID);
            try (ResultSet rsModulNavn = psEvaluate.executeQuery()) {
                while (rsModulNavn.next()) {
                    antall = rsModulNavn.getString("COUNT(mk_rettet_status");
                }
            }
        }
        if (antall==null){
            return ifNull;
        }
        return antall;
    }
    
        public String getModuleName(int moduleID, PrintWriter out) throws SQLException {
        String psModuleName = "select modul_navn from modul where modul_id=?";
        String moduleName = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(psModuleName)) {
            psStatus.setInt(1, moduleID);

            try (ResultSet rsModuleGoal = psStatus.executeQuery()) {
                while (rsModuleGoal.next()) {
                    moduleName = rsModuleGoal.getString("modul_navn");
                }
            }
        }//end connection
        return moduleName;
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

    public String getMaxPoints(int moduleID, PrintWriter out) throws SQLException {
        String qMaxPoints = "select modul_max_poeng from modul where modul_id=?";
        String modulMaxPoints = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psMaxPoints = conn.prepareStatement(qMaxPoints)) {
            psMaxPoints.setInt(1, moduleID);

            try (ResultSet rsMaxPoints = psMaxPoints.executeQuery()) {
                while (rsMaxPoints.next()) {
                    modulMaxPoints = rsMaxPoints.getString("modul_max_poeng");
                }
            }
        }//end connection
        return modulMaxPoints;
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

    public void updateModule(int modulID, String moduleName, String moduleGoal,
            String moduleText, String moduleDeliveryType, String moduleStatus, String moduleDate, int oldModuleID, String moduleMaxPoints, PrintWriter out) throws SQLException {
        String upd = "UPDATE slit.modul SET modul_id=?, modul_navn =?, modul_goal=?, "
                + "modul_tekst=?, modul_innleveringstype=?, modul_status=?, modul_fristdato=?, modul_max_poeng=? WHERE modul_id=?";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement updModule = conn.prepareStatement(upd)) {
            updModule.setInt(1, modulID);
            updModule.setString(2, moduleName);
            updModule.setString(3, moduleGoal);
            updModule.setString(4, moduleText);
            updModule.setString(5, moduleDeliveryType);
            updModule.setString(6, moduleStatus);
            updModule.setString(7, moduleDate);
            updModule.setString(8, moduleMaxPoints);
            updModule.setInt(9, oldModuleID);

            updModule.executeUpdate();

        } // end try     // end try     
        catch (SQLException ex) {
            out.println("Ikke fått oppdatert modul " + ex);
        }
    }

}//class end
