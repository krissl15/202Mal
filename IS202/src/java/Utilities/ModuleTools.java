/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Doffen
 */
public class ModuleTools {
    
    public void insertModule(int modul_id, String modul_navn, String modul_læringsmål, 
            String modul_tekst, String modul_status, int modul_fristdato, PrintWriter out) throws SQLException {
        String sql = "INSERT INTO slit.modul(modul_id, modul_navn, modul_læringsmål, modul_tekst, "
                + "modul_status, modul_fristdato) VALUES(?,?,?,?,?,?)";
        
       DbConnector db = new DbConnector();
        try(Connection conn = db.getConnection(out)){
                try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, modul_id);
            pstmt.setString(2, modul_navn);
            pstmt.setString(3, modul_læringsmål);
            pstmt.setString(4, modul_tekst);
            pstmt.setString(5, modul_status);
            pstmt.setInt(6, modul_fristdato);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        }

    }

    
    public void showModule(int moduleID, PrintWriter out) throws SQLException{
       String selectName = "select modul_navn from modul where modul_id =?"; //modulnavn
       String selectTekst = "select modul_tekst from modul where modul_id=?";
       String selectLæringsmål = "select modul_læringsmål from modul where modul_id=?";
       String selectStatus = "select modul_status from modul where modul_id=?";
       String selectFristdato = "select modul_fristdato from modul where modul_id=?";
        DbConnector db = new DbConnector();
        try(Connection conn = db.getConnection(out)){
            
        //modulnavn print start   
        try(PreparedStatement sel = conn.prepareStatement(selectName)){
            sel.setInt(1, moduleID);
            ResultSet rsName = sel.executeQuery();
            while(rsName.next()){
                String modulName = rsName.getString("modul_navn");
                out.println(modulName + "<br>");
                //modulnavn print slutt
            
            //modulTekst print start
            try(PreparedStatement tekst = conn.prepareStatement(selectTekst)){
                tekst.setInt(1, moduleID);
                ResultSet rsTekst = tekst.executeQuery();
                while(rsTekst.next()){
                    String modulTekst = rsTekst.getString("modul_tekst");
                    out.println("Tekst: " + modulTekst + "<br>");
                    //modulTekst print slutt
            
            //modulLæringsmål print start
            try(PreparedStatement læringsmål = conn.prepareStatement(selectLæringsmål)){
                læringsmål.setInt(1, moduleID);
                ResultSet rsLæringsmål = læringsmål.executeQuery();
                while(rsLæringsmål.next()){
                    String modulLæringsmål = rsLæringsmål.getString("modul_læringsmål");
                    out.println("Læringsmål: " + modulLæringsmål + "<br>");
                    //modulLæringsmål print slutt
                    
            //modulStatus print start
            try(PreparedStatement status = conn.prepareStatement(selectStatus)){
                status.setInt(1, moduleID);
                ResultSet rsStatus = status.executeQuery();
                while(rsStatus.next()){
                    String modulStatus = rsStatus.getString("modul_status");
                    out.println("Status: " + modulStatus + "<br>");
                    //modulStatus print slutt
                    
            //modulFristdato print start
            try(PreparedStatement fristdato = conn.prepareStatement(selectFristdato)){
                fristdato.setInt(1, moduleID);
                ResultSet rsFristdato = fristdato.executeQuery();
                while(rsFristdato.next()){
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

  
    
}//class end