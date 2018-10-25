/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.sql.*;
import java.io.*;
import javax.naming.NamingException;

/**
 *
 * @author Doffen
 */
public class MemberTools {

    
             
    public void printRegisteredMembers(PrintWriter out) throws SQLException, NamingException {
        String selectUsers = "select brukernavn, fornavn, etternavn from bruker where medlem_status=?";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) { //TRY-with-resources

            //modulnavn print start   
            try (PreparedStatement psRegistered = conn.prepareStatement(selectUsers)) {
                psRegistered.setString(1, "Registrert");
                ResultSet rsRegistered = psRegistered.executeQuery();
                while (rsRegistered.next()) {
                    String userName = rsRegistered.getString("brukernavn");
                    String firstName = rsRegistered.getString("fornavn");
                    String surName = rsRegistered.getString("etternavn");
                    out.println(userName + " (" + firstName + " " + surName + ")");
                    out.println("<form action=\"MedlemsListeVlet\" method=\"post\">" 
                            + "<input type=\"Submit\" name=\"member\" value=\"Fjern "+ userName + "\"><br>");
                }//registrerte brukere slutt
            }
        }
    }//end printRegisteredMembers
    /*
    Query stringen funket ikke å ha som global string, så den må opprettes i hver funksjon
    Kodeduplisering UNAVOIDABLE
    */
    public void printUnregisteredmembers(PrintWriter out) throws SQLException, NamingException {
       String selectUsers = "select brukernavn, fornavn, etternavn from bruker where medlem_status=?";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {

            //modulnavn print start   
            try (PreparedStatement psRegistered = conn.prepareStatement(selectUsers)) {
                psRegistered.setString(1, "Ikke registrert");
                ResultSet rsRegistered = psRegistered.executeQuery();
                while (rsRegistered.next()) {
                    String userName = rsRegistered.getString("brukernavn");
                    String firstName = rsRegistered.getString("fornavn");
                    String surName = rsRegistered.getString("etternavn");
                    out.println(userName + " (" + firstName + " " + surName + ")");
                    out.println("<form action=\"MedlemsListeVlet\" method=\"post\">"
                            + "<input type=\"Submit\" name=\"member\" value=\"Registrer "+ userName + "\"><br>");
                }//registrerte brukere slutt
            }
        }
    }//end printUnRegisteredMembers
    
    public void printAllMembers(PrintWriter out) throws SQLException, NamingException {
       String selectUsers = "select brukernavn, fornavn, etternavn from bruker where medlem_status=? or medlem_status=?";
        
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {

            //modulnavn print start   
            try (PreparedStatement psRegistered = conn.prepareStatement(selectUsers)) {
                psRegistered.setString(1, "Registrert");
                   psRegistered.setString(2, "Ikke registrert");
                ResultSet rsRegistered = psRegistered.executeQuery();
                while (rsRegistered.next()) {
                    String userName = rsRegistered.getString("brukernavn");
                    String firstName = rsRegistered.getString("fornavn");
                    String surName = rsRegistered.getString("etternavn");
                    out.println(userName + " (" + firstName + " " + surName + ")<br>");
                }//registrerte brukere slutt
            }
        }
    }//end printRegisteredMembers
    
    public void registerStudent(String name, PrintWriter out) throws SQLException{
        String updateQ = "update bruker set medlem_status=? where brukernavn=?";
        DbConnector db = new DbConnector();
        try(Connection conn = db.getConnection(out)){
            try(PreparedStatement ps = conn.prepareStatement(updateQ)){
                ps.setString(1, "Registrert");
                ps.setString(2, name);
                ps.executeUpdate();
            }   
        }
    }
    public void unRegister(String name, PrintWriter out) throws SQLException{
        String updateQ = "update bruker set medlem_status=? where brukernavn=?";
        DbConnector db = new DbConnector();
        try(Connection conn = db.getConnection(out)){
            try(PreparedStatement ps = conn.prepareStatement(updateQ)){
                ps.setString(1, "Ikke registrert");
                ps.setString(2, name);
                ps.executeUpdate();
            }   
        }
    }
    
    
}
