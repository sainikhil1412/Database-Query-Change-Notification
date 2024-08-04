package org.example;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;

import java.sql.*;
import java.util.Properties;

public class CQNDemo {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = (OracleConnection) DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1522:orcl2", "system", "nikhil"
            );
            OracleConnection oracleConn = (OracleConnection) conn;

            Properties prop = new Properties();
//            prop.setProperty(OracleConnection.NTF_LOCAL_TCP_PORT, "15000");
            prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
            prop.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION, "true");
            prop.setProperty(OracleConnection.DCN_BEST_EFFORT, "true");

            DatabaseChangeRegistration dcr = oracleConn.registerDatabaseChangeNotification(prop);

            dcr.addListener(new DatabaseChangeListener() {
                @Override
                public void onDatabaseChangeNotification(DatabaseChangeEvent e) {
                    if(e.getEventType().equals(DatabaseChangeEvent.EventType.QUERYCHANGE)) {
                        System.out.println("Database change event: " + e.toString());
                        // Implement logic to handle the change event, e.g., fetch updated data
                        action();
                    }
                }
            });

            String query = "SELECT * FROM MOVIE where director='RGV'";
            OracleStatement statement = (OracleStatement) conn.prepareStatement(query);

            statement.setDatabaseChangeRegistration(dcr);
//            ResultSet rs = statement.executeQuery(query);
//
//            while(rs.next()){
//                System.out.println(rs.getString("name"));
//            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static  void action(){
        System.out.println("Fetching updated data");
    }
}
