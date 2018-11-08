/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Doffen
 */
public class DeliveryTools {

    /**
     *
     * @param deliveryText
     * @param date This should be the current time that the user registers the
     * delivery. YYYYMMDD
     * @param name
     * @param out
     * @throws SQLException
     */
    public void registerDelivery(String deliveryText, String date, String name, PrintWriter out) throws SQLException {
        String qDelivery = ("Insert into Innlevering (Innlevering_tekst, innlevering_dato, brukernavn)\n"
                + "values (?, ?, ?);");

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psDelivery = conn.prepareStatement(qDelivery)) {
            psDelivery.setString(1, deliveryText);
            psDelivery.setString(2, date);
            psDelivery.setString(3, name);

            psDelivery.executeUpdate();
        }

    }//method end

    public void getLastDeliveryByUser(String name, PrintWriter out) throws SQLException {
        String qModulKanal = ("select innlevering_id from innlevering where brukernavn=? order by innlevering_id desc limit 1;");

        DbConnector db = new DbConnector();
        try (Connection conn = db.getConnection(out);
                PreparedStatement psUpdateModulKanal = conn.prepareStatement(qModulKanal)) {

            psUpdateModulKanal.setString(1, name);

        }
    }

    }//class end 
