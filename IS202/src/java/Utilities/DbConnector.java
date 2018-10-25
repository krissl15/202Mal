package Utilities;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbConnector {

    Connection conn;


    public void close() {

        try {
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Ikke lukke DB " + ex);
        }
    }

    public Connection getConnection(PrintWriter out) {
        try {
            // Step 1: Allocate a database 'Connection' object
            Context cont = new InitialContext();
            DataSource ds = (DataSource) cont.lookup("java:comp/env/jdbc/slit");
            //DataSource ds = (DataSource)cont.lookup("jdbc/LocalhostDS");
            conn = ds.getConnection();
            return conn;

        } catch (SQLException ex) {
            out.println("Not connected to database " + ex);
        } catch (NamingException nex) {
            out.println("Not correct naming" + nex);
        }
        return null;
    }  // end getConnection       

    Connection getConnection(PrintStream out) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
