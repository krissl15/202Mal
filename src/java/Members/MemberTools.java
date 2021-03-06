/*
 * This class contains tools used around member functionalities. Especially member servlets.
 */
package Members;

import Utilities.DbConnector;
import java.sql.*;
import java.io.*;
import javax.naming.NamingException;

/**
 *
 * @author Doffen, Elias
 */
public class MemberTools {

    /**
     * 
     * @param role rollen til studentene du vil printe ut 
     * @param out
     * @throws SQLException
     * @throws NamingException 
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
                    
                    //deklarerer URL hvor valgte person blir lagret, slik at en kan hente ut kontaktinformasjon.
                    String chosenPerson  = "<a href='StudentProgressServlet?firstName=%s&lastName=%s&userName=%s&value=%s'>%s %s</a>"; 

                    if (rolle.equals("RegistrertStudent")) { //sjekker om rollen til objektet som blir iterert er registrert
                        out.println("<div id=\"memberListRegistered\">");
                        out.println("<div id=\"memberListRegisteredName\">");
                        out.format(chosenPerson, firstName, surName, userName, "registrertstudent", firstName, surName, userName,"registrertstudent");
                        out.println("</div>");
                        //out.println(userName + " (" + firstName + " " + surName + ")");
                        

                        out.println("<form action=\"MemberListServlet\" method=\"post\">"
                              // + "<input type=\"checkbox\" name=\"removeCheck\" value=\"Remove " + userName + "\"><br>"
                                + "<div id=\"memberListRegisteredButtons\">"
                                + "<input type=\"Submit\" name=\"member\" value=\"Fjern " + userName + "\">");
                         out.println("<form action=\"MemberListServlet\" method=\"post\">"
                                + "<input type=\"Submit\" name =\"member\" value =\"Assistent " + userName + "\">");
                         out.println("</div>");
                         out.println("</div>");
                         out.println("<br>");
                    } else if (rolle.equals("UregistrertStudent")) {
                        out.format(chosenPerson, firstName, surName, userName, "uregistrertstudent", firstName, surName, userName,"uregistrertstudent");
                        //out.println(userName + " (" + firstName + " " + surName + ")");
                        out.println("<form action=\"MemberListServlet\" method=\"post\">"
                              // + "<input type=\"checkbox\" name=\"addCheck\" value=\"Add " + userName + "\"><br>"
                                + "<input type=\"Submit\" name=\"member\" value=\"Registrer " + userName + "\"><br>");
                    }else if (rolle.equals("Assistent")) {
                        out.format(chosenPerson, firstName, surName, userName, "assistent", firstName, surName, userName,"assistent");
                        //out.println(userName + " (" + firstName + " " + surName + ")");
                        out.println("<form action=\"MemberListServlet\" method=\"post\">"
                                + "<input type=\"Submit\" name=\"member\" value=\"Ta bort assistent " + userName + "\"><br>");
                    }else if (rolle.equals("Foreleser")) {
                        out.format(chosenPerson, firstName, surName, userName, "foreleser", firstName, surName, userName,"foreleser");
                        //out.println(userName + " (" + firstName + " " + surName + ")");                    
                    }
                }
            }
        }
    }//end printMembersByRole
    
    /**
     * 
     * @param out
     * @throws SQLException
     * @throws NamingException 
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
                    String chosenPerson  = "<a href='StudentProgressServlet?firstName=%s&lastName=%s&userName=%s&value=%s'>%s %s</a>";
                    String userName = rsRegistered.getString("brukernavn");
                    String firstName = rsRegistered.getString("fornavn");
                    String surName = rsRegistered.getString("etternavn");
                    out.format(chosenPerson, firstName, surName, userName, "registrertstudent", firstName, surName, userName,"registrertstudent");
                    //out.println(userName + " (" + firstName + " " + surName + ")<br>");
                }
            }
        }
    }
    
    /**
     * 
     * @param name name you are searching for 
     * @param roleName
     * @param out
     * @throws SQLException
     * @throws NamingException 
     */
    public void searchUser(String name, String roleName, PrintWriter out) throws SQLException, NamingException {
        
        String forSql = name+"%";
        String searchQ = "SELECT bruker.brukernavn, bruker.fornavn, bruker.etternavn, bruker_rolle.rolle\n" +
                         "FROM bruker\n" +
                         "INNER JOIN bruker_rolle\n" +
                         "ON bruker_rolle.brukernavn = bruker.brukernavn\n" +
                         "WHERE rolle = ? AND (fornavn LIKE ? OR etternavn LIKE ?) ORDER BY bruker.etternavn;";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement ps = conn.prepareStatement(searchQ)) {
                ps.setString(1, roleName);
                ps.setString(2, forSql);
                ps.setString(3, forSql);
                
                ResultSet searchResult = ps.executeQuery(); //resultset er en "liste" av alt queryen selected
                while (searchResult.next()) { //iterator'

                    String userName = searchResult.getString("brukernavn");
                    String firstName = searchResult.getString("fornavn");
                    String surName = searchResult.getString("etternavn");
                    
                    if (roleName.equals("RegistrertStudent")) { //sjekker om rollen til objektet som blir iterert er registrert
                        out.println(userName + " (" + firstName + " " + surName + ")");
                        out.println("<form action=\"MemberSearchServlet" + "?search=" + name + "\" method=\"post\">"
                               // + "<input type=\"checkbox\" name=\"removeCheck\" value=\"Remove " + userName + "\"><br>"
                                + "<input type=\"Submit\" name=\"member\" value=\"Fjern " + userName + "\"><br>");
                         out.println("<form action=\"MemberSearchServlet" + "?search=" + name + "\" method=\"post\">"
                                + "<input type=\"Submit\" name =\"member\" value =\"Assistent " + userName + "\"><br>");
                    } else if (roleName.equals("UregistrertStudent")) {
                        out.println(userName + " (" + firstName + " " + surName + ")");
                        out.println("<form action=\"MemberSearchServlet" + "?search=" + name + "\" method=\"post\">"
                               // + "<input type=\"checkbox\" name=\"addCheck\" value=\"Add " + userName + "\"><br>"
                                + "<input type=\"Submit\" name=\"member\" value=\"Registrer " + userName + "\"><br>");
                    }else if (roleName.equals("Assistent")) {
                        out.println(userName + " (" + firstName + " " + surName + ")");
                        out.println("<form action=\"MemberSearchServlet" + "?search=" + name + "\" method=\"post\">"
                                + "<input type=\"Submit\" name=\"member\" value=\"Ta bort assistent " + userName + "\"><br>");
                    }
                        
                    else if (roleName.equals("Foreleser")) {
                        out.println(userName + " (" + firstName + " " + surName + ")");   

                }

            }
        }
    }
    }
    
    /**
     * 
     * @param name who are u searching for 
     * @param roleName
     * @param out
     * @throws SQLException
     * @throws NamingException 
     */
    public void searchUserStudent(String name, String roleName, PrintWriter out) throws SQLException, NamingException {
        
        String forSql = name+"%";
        String searchQ = "SELECT bruker.brukernavn, bruker.fornavn, bruker.etternavn, bruker_rolle.rolle\n" +
                         "FROM bruker\n" +
                         "INNER JOIN bruker_rolle\n" +
                         "ON bruker_rolle.brukernavn = bruker.brukernavn\n" +
                         "WHERE rolle = ? AND (fornavn LIKE ? OR etternavn LIKE ?) ORDER BY bruker.etternavn;";

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (PreparedStatement ps = conn.prepareStatement(searchQ)) {
                ps.setString(1, roleName);
                ps.setString(2, forSql);
                ps.setString(3, forSql);
                
                ResultSet searchResult = ps.executeQuery(); //resultset er en "liste" av alt queryen selected
                while (searchResult.next()) { //iterator'

                    String userName = searchResult.getString("brukernavn");
                    String firstName = searchResult.getString("fornavn");
                    String surName = searchResult.getString("etternavn");
                    out.println(userName + " (" + firstName + " " + surName + ")<br>");
                 }
             }
        }
    }

    /**
     * 
     * @param name who is being registered
     * @param out
     * @throws SQLException 
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

    /**
     * 
     * @param name who is being unregistered
     * @param out
     * @throws SQLException 
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
    
    /**
     * 
     * @param name
     * @param out
     * @throws SQLException 
     */
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
 
   /**
    * 
    * @param name
    * @param out
    * @throws SQLException 
    */
    public void addToModulKanal(String name, PrintWriter out) throws SQLException {
        String moduleQ = "select modul_id from modul";
        String insertModulKanal = "insert into modulkanal (brukernavn, modul_id, mk_status, mk_rettet_status)\n"
                + "values(?, ?, ?,?);";

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
                psInsert.setString(4, "Ikke rettet");
                psInsert.executeUpdate(); //kjør insert query med navn og modul_id
            }
        }

    } //end addToModulKanal 


    /**
     * Adds all students to modulkanal. Has to be done when a new module is created. 
     * @param moduleID
     * @param out
     * @throws SQLException 
     */
   public void addAllToNewModulekanal(String moduleID, PrintWriter out) throws SQLException{
       String qUsers ="select brukernavn from bruker_rolle where rolle=\"registrertstudent\";";
       String qInsert="insert into modulkanal (brukernavn, modul_id, mk_status, mk_rettet_status)\n" +
"values(?, ?, \"Ikke levert\", \"Ikke rettet\");";
       DbConnector db = new DbConnector();
       try(Connection conn = db.getConnection(out);
       PreparedStatement psUsers = conn.prepareStatement(qUsers);
       PreparedStatement psInsert = conn.prepareCall(qInsert)){
           
           ResultSet rsUsers = psUsers.executeQuery();
           while(rsUsers.next()){
               String userName = rsUsers.getString("brukernavn");
               
               psInsert.setString(1, userName);
               psInsert.setString(2, moduleID);
               psInsert.executeUpdate();
           }
           
       }
   }
    
   /**
    * 
    * @param name
    * @param out
    * @throws SQLException 
    */
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
    
    /**
     * 
     * @param out
     * @throws SQLException
     * @throws NamingException 
     */
    public void printAssistants(PrintWriter out) throws SQLException, NamingException {
        String selectUsers = "select bruker.brukernavn, bruker.fornavn, bruker.etternavn, bruker_rolle.rolle\n"
                + "from bruker\n"
                + "inner join bruker_rolle on bruker_rolle.brukernavn = bruker.brukernavn\n"
                + "where rolle = \"Assistent\";";
        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out)) {
            try (Statement psRegistered = conn.createStatement()) {
                 ResultSet rsRegistered = psRegistered.executeQuery(selectUsers);
                while (rsRegistered.next()) {
                    String chosenPerson  = "<a href='StudentProgressServlet?firstName=%s&lastName=%s&userName=%s&value=%s'>%s %s</a>"; 
                    String userName = rsRegistered.getString("brukernavn");
                    String firstName = rsRegistered.getString("fornavn");
                    String surName = rsRegistered.getString("etternavn");
                    out.format(chosenPerson, firstName, surName, userName, "assistent", firstName, surName, userName,"assistent");
                    
                    //out.println(userName + " (" + firstName + " " + surName + ")<br>");
                }
            }
        }
    }
}
