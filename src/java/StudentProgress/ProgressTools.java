/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StudentProgress;

import Utilities.DbConnector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
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
    
    
public void listModulesByUsername(String userName, int modulID, PrintWriter out) throws SQLException{
                
    String selectModule = "SELECT modulkanal.modul_id, mk_status, innlevering_id, modul_navn\n" +
"FROM modulkanal\n" +
"INNER JOIN modul ON modul.modul_id = modulkanal.modul_id\n" +
"WHERE brukernavn = ?" +
"AND modulkanal.modul_id = ?;";    

            DbConnector db = new DbConnector();
            try (Connection conn = db.getConnection(out);
                PreparedStatement psListModules = conn.prepareStatement(selectModule)) {
                psListModules.setString(1, userName);
                psListModules.setInt(2, modulID);
                ResultSet rsModulKanal = psListModules.executeQuery();
                    while (rsModulKanal.next()) {
                        String modulName = rsModulKanal.getString("modul_navn");
                        String modulStatus = rsModulKanal.getString("mk_status");
                        out.println(modulName + " " + modulStatus + " " + "{Poeng}" + "<br>");
                    }
            }
}

public void printPerson(String userName, PrintWriter out){
    String selectUser = "SELECT fornavn, etternavn, epost FROM bruker WHERE brukernavn=?";
    DbConnector db = new DbConnector();
            try (Connection conn = db.getConnection(out);
                PreparedStatement psListModules = conn.prepareStatement(selectUser)) {
                psListModules.setString(1, userName);
                ResultSet rsUser = psListModules.executeQuery();
                    while (rsUser.next()) {
                        String firstName = rsUser.getString("fornavn");
                        String lastName = rsUser.getString("etternavn");
                        String mail = rsUser.getString("epost");
                        out.println("Fornavn: " + firstName);
                        out.println("<br>");
                        out.println("Etternavn: " + lastName);
                        out.println("<br>");
                        out.println("E-post adresse: " + mail);
                    }
            } catch (SQLException ex) {
        Logger.getLogger(ProgressTools.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}

