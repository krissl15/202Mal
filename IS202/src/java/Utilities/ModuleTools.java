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
 public void editModule(int modul_id, String modul_navn, String modul_læringsmål, 
        String modul_tekst, String modul_status, int modul_fristdato, PrintWriter out){
        String sql = "update modul set modul_id =?, modul_navn=?, modul_læringsmål=?, "
                + "modul_tekst=?, modul_status=?, modul_fristdato=?";
        
        DbConnector db = new DbConnector();
        try(Connection conn = db.getConnection(out)){
        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, modul_id);
            pstm.setString(2, modul_navn);
            pstm.setString(3, modul_læringsmål);
            pstm.setString(4, modul_tekst);
            pstm.setString(5, modul_status);
            pstm.setInt(6, modul_fristdato);
            pstm.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());   
        }
    }     catch (SQLException e) {
            Logger.getLogger(ModuleTools.class.getName()).log(Level.SEVERE, null, e);
    }
    }
    public void edit(int intID, String modul_navn, String modul_læringsmål, String modul_tekst, String modul_status, int intDato, PrintWriter out) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
      
  
    public void deleteModule(int modul_id, PrintWriter out){
        String sql = "DELETE FROM slit.module where Modul_id =?";
        
        DbConnector db = new DbConnector();
        try(Connection conn = db.getConnection(out)){
        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, modul_id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            
            System.out.println(e.getMessage());   
    }
    }   catch (SQLException ex) {   
            Logger.getLogger(ModuleTools.class.getName()).log(Level.SEVERE, null, ex);
    }   
    }
    
    
  
    
}//class end
