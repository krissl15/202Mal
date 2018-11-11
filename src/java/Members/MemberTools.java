/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Members;

import Utilities.DbConnector;
import java.sql.*;
import java.io.*;
import javax.naming.NamingException;

/**
 *
 * @author Doffen
 */
public class MemberTools {

    /*
    @param PrintWriter objekt, og en string som matcher rollene i databasen 
     */
    public void printMembersByRole(String role, PrintWriter out) throws SQLException, NamingException {
        String selectUsers = "select bruker.brukernavn, bruker.fornavn, bruker.etternavn, bruker_rolle.rolle\n"
                + " from bruker\n"
                + " inner join bruker_rolle on bruker_rolle.brukernavn = bruker.brukernavn\n"
                + " where rolle = ?;";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement ps = conn.prepareStatement(selectUsers)) { //opprett ps med query som parameter
                ps.setString(1, role);

                ResultSet rsRegistered = ps.executeQuery(); //resultset er en "liste" av alt queryen selected
                while (rsRegistered.next()) { //iterator
                    String userName = rsRegistered.getString("brukernavn");
                    String firstName = rsRegistered.getString("fornavn");
                    String surName = rsRegistered.getString("etternavn");
                    String rolle = rsRegistered.getString("rolle");

                    if (rolle.equals("RegistrertStudent")) { //sjekker om rollen til objektet som blir iterert er registrert
                        out.println(userName + " (" + firstName + " " + surName + ")");
                        out.println("<form action=\"MemberListServlet\" method=\"post\">"
                                + "<input type=\"checkbox\" name=\"removeCheck\" value=\"Remove " + userName + "\"><br>"
                                + "<input type=\"Submit\" name=\"member\" value=\"Fjern " + userName + "\"><br>");
                         out.println("<form action=\"MemberListServlet\" method=\"post\">"
                                + "<input type=\"Submit\" name =\"member\" value =\"Assistent " + userName + "\"><br>");
                    } else if (rolle.equals("UregistrertStudent")) {
                        out.println(userName + " (" + firstName + " " + surName + ")");
                        out.println("<form action=\"MemberListServlet\" method=\"post\">"
                                + "<input type=\"checkbox\" name=\"addCheck\" value=\"Add " + userName + "\"><br>"
                                + "<input type=\"Submit\" name=\"member\" value=\"Registrer " + userName + "\"><br>");
                    }else if (rolle.equals("Assistent")) {
                        out.println(userName + " (" + firstName + " " + surName + ")");
                        out.println("<form action=\"MemberListServlet\" method=\"post\">"
                                + "<input type=\"Submit\" name=\"member\" value=\"Ta bort assistent " + userName + "\"><br>");
                    }
                }
            }
        }
    }//end printMembersByRole

    /*
    *This function prints all users marked as "RegistrertStudent" in the database
     */
    public void printRegisteredMembers(PrintWriter out) throws SQLException, NamingException {
        String selectUsers = "select bruker.brukernavn, bruker.fornavn, bruker.etternavn, bruker_rolle.rolle\n"
                + "from bruker\n"
                + "inner join bruker_rolle on bruker_rolle.brukernavn = bruker.brukernavn\n"
                + "where rolle = \"RegistrertStudent\";";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (Statement psRegistered = conn.createStatement()) {

                ResultSet rsRegistered = psRegistered.executeQuery(selectUsers);
                while (rsRegistered.next()) {
                    String userName = rsRegistered.getString("brukernavn");
                    String firstName = rsRegistered.getString("fornavn");
                    String surName = rsRegistered.getString("etternavn");
                    out.println(userName + " (" + firstName + " " + surName + ")<br>");
                }
            }
        }
    }

    /*
    *@Param name of student to register
     */
    public void registerStudent(String name, PrintWriter out) throws SQLException {
        String updateQ = "update bruker_rolle set rolle=? where brukernavn=?";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement ps = conn.prepareStatement(updateQ)) {
                ps.setString(1, "RegistrertStudent");
                ps.setString(2, name);
                ps.executeUpdate();
            }
        }
    }

    /*
    *@Param name of student to unregister
     */
    public void unRegister(String name, PrintWriter out) throws SQLException {
        String updateQ = "update bruker_rolle set rolle=? where brukernavn=?";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement ps = conn.prepareStatement(updateQ)) {
                ps.setString(1, "UregistrertStudent");
                ps.setString(2, name);
                ps.executeUpdate();
            }
        }
    }
    
    public void registerAssistent(String name, PrintWriter out) throws SQLException {
        String updateQ = "update bruker_rolle set rolle=? where brukernavn=?";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement ps = conn.prepareStatement(updateQ)) {
                ps.setString(1, "Assistent");
                ps.setString(2, name);
                ps.executeUpdate();
            }
        }
    }
 
   /*
    *Metode for å legge student inn i modulkanal 
    * @param name er navn på student
    */
    public void addToModulKanal(String name, PrintWriter out) throws SQLException {
        String moduleQ = "select modul_id from modul";
        String insertModulKanal = "insert into modulkanal (brukernavn, modul_id, mk_status)\n"
                + "values(?, ?, ?);";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                Statement modules = conn.createStatement(); //statisk select query
                PreparedStatement psInsert = conn.prepareStatement(insertModulKanal)) { //dynamisk insert query

            ResultSet rsModules = modules.executeQuery(moduleQ); //rsModules blir liste med alle modul_id'er
            while (rsModules.next()) { //iterer gjennom hver id 
                int moduleId = rsModules.getInt("modul_id"); 
                psInsert.setString(1, name);
                psInsert.setInt(2, moduleId);
                psInsert.setString(3, "Ikke levert");
                psInsert.executeUpdate(); //kjør insert query med navn og modul_id
            }
        }

    } //end addToModulKanal 
    
    public void removeFromModulKanal(String name, PrintWriter out) throws SQLException{
        String deleteQ = "delete from modulkanal\n" +
"where brukernavn = ?";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psDelete = conn.prepareStatement(deleteQ)) { //dynamisk insert query
            psDelete.setString(1, name);
            psDelete.execute();
        }
    }//end removeFromModulKanal
}
