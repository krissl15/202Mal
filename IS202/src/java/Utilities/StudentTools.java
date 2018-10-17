/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Doffen
 */
public class StudentTools {

    Connection con;
    

    public void registerStudent(String studentFName, String studentSName, String studentPass, PrintWriter out) throws SQLException {

        String ins = "insert into slit.studentUser (student_firstName, student_surName, student_password)  values (?, ?, ?)";
        DbConnector db = new DbConnector();
            try(Connection conn = db.getConnection(out);){
        try (PreparedStatement insert = conn.prepareStatement(ins);) {
            insert.setString(1, studentFName);
            insert.setString(2, studentSName);
            insert.setString (3, studentPass);

            insert.executeUpdate();
        }
    }
    }
    public Connection printStudents(PrintWriter out) throws SQLException, NamingException {
        Context context = new InitialContext();
        DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/slit");

        int id;
        String name;
        try (Connection conn = dataSource.getConnection();
                final PreparedStatement ps = conn.prepareStatement("select * from student")) {
            try (final ResultSet rs = ps.executeQuery()) {
                int rowNr = 0;
                while (rs.next()) {
                    id = rs.getInt(1);
                    name = rs.getString(2);
                    htmlTools ht = new htmlTools();
                    ht.header();
                    out.println("<br>");
                    out.println("ID: " + id + " -");
                    out.println("Navn: " + name);
                    out.println("<br>");
                    ht.headerEnd();
                    rowNr++;
                }

                return conn;
            } catch (SQLException ex) {
                Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
                out.println(ex);
            }
            return null;
        }
    }
    
    public void checkUserName(PrintWriter out) throws SQLException{
        DbConnector db = new DbConnector();
            try(Connection conn = db.getConnection(out);){
                
            }
        
    }
}
