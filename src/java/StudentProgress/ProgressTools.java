/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StudentProgress;

import Utilities.DbConnector;
import Utilities.ModuleTools;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author phuonghapham
 */
public class ProgressTools {
    
    
public void listModulesByUsername(String brukernavn, PrintWriter out) throws SQLException{
        ModuleTools mT = new ModuleTools();
            DbConnector db = new DbConnector();
            try (Connection conn = db.getConnection(out)) {
                try (Statement st = conn.createStatement()) {
                    String selectModule = "SELECT modul_id, mk_status, innlevering_id\n" +
"FROM modulkanal\n" +
"WHERE brukernavn =?";
                    ResultSet rsModules = st.executeQuery(selectModule);
                    while (rsModules.next()) {
                        String modulID = rsModules.getString("modul_id");
                            int intID = Integer.parseInt(modulID);
                            mT.showModule(intID, out);
                            out.println("<br>");

                        }//end if
                    }//end while
                }//end preparedstatement
            }//end connection
}
