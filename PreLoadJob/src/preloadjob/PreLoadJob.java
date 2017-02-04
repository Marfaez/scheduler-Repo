/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preloadjob;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author genicot.a
 */
public class PreLoadJob {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        String database = args[0];
        String port = args[1];
        String schemas = args[2];
        String user = args[3];
        String pass = args[4];

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            //String url = "jdbc:oracle:thin:@dbtsthrm.idcn.mil.intra:1585:PVXT";
            String url = "jdbc:oracle:thin:@" + database+ ":" + port + ":" + schemas;
            //Connection con = DriverManager.getConnection(url, "hrmwrk", "hrmtstwrk");
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to database");


            String command = "{call P_LOAD_TSYNCTBLVIEW}";
            CallableStatement cstmt = con.prepareCall(command);
            cstmt.execute();

            cstmt.close();
            System.out.println("END BATCH");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    

