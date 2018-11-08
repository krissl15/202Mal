package Utilities;

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
    public void insertModule(String modul_navn, String modul_læringsmål, 
            String modul_tekst, String modul_status, int modul_fristdato, PrintWriter out) throws SQLException {
        String sql = "INSERT INTO slit.modul(modul_navn, modul_læringsmål, modul_tekst, "
                + "modul_status, modul_fristdato) VALUES(?,?,?,?,?)";
        
       DbConnector db = new DbConnector();
        try(Connection conn = db.getConnection(out)){
                try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, modul_navn);
            pstmt.setString(2, modul_læringsmål);
            pstmt.setString(3, modul_tekst);
            pstmt.setString(4, modul_status);
            pstmt.setInt(5, modul_fristdato);
            
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
        String selectName = "select modul_navn from modul where modul_id =?"; //modulnavn
        String selectTekst = "select modul_tekst from modul where modul_id=?";
        String selectLæringsmål = "select modul_læringsmål from modul where modul_id=?";
        String selectStatus = "select modul_status from modul where modul_id=?";
        String selectFristdato = "select modul_fristdato from modul where modul_id=?";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {

            //modulnavn print start   
            try (PreparedStatement sel = conn.prepareStatement(selectName)) {
                sel.setInt(1, moduleID);
                ResultSet rsName = sel.executeQuery();
                while (rsName.next()) {
                    String modulName = rsName.getString("modul_navn");
                    out.println(modulName + "<br>");
                    //modulnavn print slutt

                    //modulTekst print start
                    try (PreparedStatement tekst = conn.prepareStatement(selectTekst)) {
                        tekst.setInt(1, moduleID);
                        ResultSet rsTekst = tekst.executeQuery();
                        while (rsTekst.next()) {
                            String modulTekst = rsTekst.getString("modul_tekst");
                            out.println("Tekst: " + modulTekst + "<br>");
                            //modulTekst print slutt

                            //modulLæringsmål print start
                            try (PreparedStatement læringsmål = conn.prepareStatement(selectLæringsmål)) {
                                læringsmål.setInt(1, moduleID);
                                ResultSet rsLæringsmål = læringsmål.executeQuery();
                                while (rsLæringsmål.next()) {
                                    String modulLæringsmål = rsLæringsmål.getString("modul_læringsmål");
                                    out.println("Læringsmål: " + modulLæringsmål + "<br>");
                                    //modulLæringsmål print slutt

                                    //modulStatus print start
                                    try (PreparedStatement status = conn.prepareStatement(selectStatus)) {
                                        status.setInt(1, moduleID);
                                        ResultSet rsStatus = status.executeQuery();
                                        while (rsStatus.next()) {
                                            String modulStatus = rsStatus.getString("modul_status");
                                            out.println("Status: " + modulStatus + "<br>");
                                            //modulStatus print slutt

                                            //modulFristdato print start
                                            try (PreparedStatement fristdato = conn.prepareStatement(selectFristdato)) {
                                                fristdato.setInt(1, moduleID);
                                                ResultSet rsFristdato = fristdato.executeQuery();
                                                while (rsFristdato.next()) {
                                                    String modulFristdato = rsFristdato.getString("modul_fristdato");
                                                    out.println("Fristdato: " + modulFristdato + "<br>");
                                                    //modulFristdato print slutt
                                                }

                                            }//try end
                                        }

                                    }//try end
                                }

                            }//try end
                        }

                    }//try end
                }
            }//try end
        }//try end
    }//showmodule end

    /**
     *
     * @param name
     * @param moduleID
     * @param out
     * @return
     * @throws SQLException
     */
    public String checkIfDelivered(String name, int moduleID, PrintWriter out) throws SQLException {
        String qStatus = "select mk_status from modulkanal where brukernavn=? and modul_id=?";
        String deliveryStatus = null;

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psStatus = conn.prepareStatement(qStatus)) {
            psStatus.setString(1, name);
            psStatus.setInt(2, moduleID);

            try(ResultSet rsStatus = psStatus.executeQuery()){
            while (rsStatus.next()) {
                deliveryStatus = rsStatus.getString("mk_status");
            }
            }
        }//end connection
        return deliveryStatus;
    }//method end 
    
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
    

}//class end
