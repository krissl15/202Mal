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
public class modulTools {
    
    
    public void showModule(int moduleID, PrintWriter out) throws SQLException{
       String selectName = "select modul_navn from modul where modul_id =?"; //modulnavn
       String selectTekst = "select modul_tekst from modul where modul_id=?";
        DbConnector db = new DbConnector();
        try(Connection conn = db.getConnection(out)){
        try(PreparedStatement sel = conn.prepareStatement(selectName)){
            sel.setInt(1, moduleID);
            //modulnavn print start
            ResultSet rsName = sel.executeQuery();
            while(rsName.next()){
            String modulName = rsName.getString("modul_navn");
            out.println(modulName+"<br>");
            //modulnavn print slutt
            //modulTekst print start
            try(PreparedStatement tekst = conn.prepareStatement(selectTekst)){
                tekst.setInt(1, moduleID);
                ResultSet rsTekst = tekst.executeQuery();
                while(rsTekst.next()){
                    String modulTekst = rsTekst.getString("modul_tekst");
                    out.println("modul tekst: " + modulTekst);
                }
                   
            }
            }
        }//try end
        }//try end
    }//showmodule end
}//class end
