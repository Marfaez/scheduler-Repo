import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
 import java.io.UnsupportedEncodingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class MessageDownloader {
    public MessageDownloader() {
        super();
    }

    
private static String getXMLmessage(String oracleDB, String userName, String password, String querySQL) 
            throws SQLException, DataFormatException, UnsupportedEncodingException, ClassNotFoundException {
    String oracleConnection = "jdbc:oracle:thin:@" + oracleDB;
    String id = "";
    String segment = "";
    String resultXML="";
    Connection conn = null;
    
    
    Class.forName("oracle.jdbc.driver.OracleDriver");
    //Get Connection 
   // conn = DriverManager.getConnection("jdbc:oracle:thin:@dbtsthrm.idcn.mil.intra:1585:PVXX","HCMSEL", "uiznacc3");
    conn = DriverManager.getConnection(oracleConnection ,userName, password);
    Statement stmt = conn.createStatement();
    ResultSet rs =
    stmt.executeQuery(querySQL);



    while (rs.next()) {
        if(!id.equals(rs.getString("IBTRANSACTIONID"))) {
        id = rs.getString("IBTRANSACTIONID");
        //System.out.println("----------------------------------------------------");
        //System.out.println("****** " + id + " ******");
        }

        segment = rs.getInt("IB_SEGMENTINDEX") + "-" +  rs.getInt("SEGMENTNO") + "-" + rs.getInt("SUBSEGMENTNO");

        Inflater inflater = new Inflater();
        byte[] result = new byte[rs.getInt("UNCOMPMIMEDATALEN")];
        inflater.setInput(rs.getBytes("MIMEDATALONG"));
        int length = inflater.inflate(result);

        //System.out.println("Segment: " + segment);
        if(segment.equalsIgnoreCase("0-0-1"))
                resultXML = new String(result, 0, length, "UTF-8");
        //System.out.println(new String(result, 0, length, "UTF-8"));
        //System.out.println();
        //System.out.println("--");
        //System.out.println();
        //System.out.println("****************************");
        //System.out.println(resultXML);
        //System.out.println("****************************");
        inflater.end();

    }
    
    return resultXML;
    
    }
    
    
public static void main(String[] args) throws SQLException, DataFormatException, UnsupportedEncodingException, ClassNotFoundException, FileNotFoundException {

    //System.out.println(d.getXMLmessage("dbtsthrm.idcn.mil.intra:1585:PVXX", "HCMSEL", "uiznacc3", "e8cf73e9-670b-11e6-bfed-9d1cece44b55"));
    
    
    // format for oracleDB param = hostName.idcn.mil.intra:portNumber:SID
    String oracleDB  = args[0];
    String userName  = args[1];
    String password  = args[2];
    String querySQL =  args[3];
    String fileExportPath = args[4];

      
    String fileName = "export.xml";
    String completePath = fileExportPath + File.separator + fileName;
    
    try (PrintStream out = new PrintStream(new FileOutputStream(completePath))) {
        out.print(getXMLmessage(oracleDB,userName,password, querySQL));
        out.flush();
        out.close();
    }
    
    /*
    String oracleConnection = "jdbc:oracle:thin:@" + oracleDB;
    String id = "";
    String segment = "";
    String resultXML="";
    Connection conn = null;
    
    
    Class.forName("oracle.jdbc.driver.OracleDriver");
    //Get Connection 
   // conn = DriverManager.getConnection("jdbc:oracle:thin:@dbtsthrm.idcn.mil.intra:1585:PVXX","HCMSEL", "uiznacc3");
    conn = DriverManager.getConnection(oracleConnection ,userName, password);
    Statement stmt = conn.createStatement();
    ResultSet rs =
    stmt.executeQuery("SELECT IBTRANSACTIONID, IB_SEGMENTINDEX, SEGMENTNO, SUBSEGMENTNO, UNCOMPMIMEDATALEN, MIMEDATALONG\n" +
    " FROM HCMOWN.PSAPMSGPUBDATA\n" +
    " WHERE IBTRANSACTIONID = '" + IBTRANSACTIONID + "'" + 
    " ORDER BY IBTRANSACTIONID, IB_SEGMENTINDEX, SEGMENTNO, SUBSEGMENTNO, DATASEQNO\n");



    while (rs.next()) {
        if(!id.equals(rs.getString("IBTRANSACTIONID"))) {
        id = rs.getString("IBTRANSACTIONID");
        //System.out.println("----------------------------------------------------");
        //System.out.println("****** " + id + " ******");
        }

        segment = rs.getInt("IB_SEGMENTINDEX") + "-" +  rs.getInt("SEGMENTNO") + "-" + rs.getInt("SUBSEGMENTNO");

        Inflater inflater = new Inflater();
        byte[] result = new byte[rs.getInt("UNCOMPMIMEDATALEN")];
        inflater.setInput(rs.getBytes("MIMEDATALONG"));
        int length = inflater.inflate(result);

        System.out.println("Segment: " + segment);
        if(segment.equalsIgnoreCase("0-0-1"))
                resultXML = new String(result, 0, length, "UTF-8");
        //System.out.println(new String(result, 0, length, "UTF-8"));
        //System.out.println();
        //System.out.println("--");
        //System.out.println();
        //System.out.println("****************************");
        //System.out.println(resultXML);
        //System.out.println("****************************");
        inflater.end();

    }
*/

    }
}
