package com.controller.ta;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Model.koneksi;
import com.algo.ta.BilanganAcak;
import com.algo.ta.BuatKunci;
import com.algo.ta.Elgamal;
import com.algo.ta.EnkripDekrip;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author Karjono,Bhatara,Guntur
 */
public class enkripdatabase {
   
    private String filename;
    private String directory;
    private String publicFile;
    private String p;
    private String g;
    private String x;
    private String fileNameEncrypt;
    private String extension;
    String UkuranFile= null;
    String UkuranEnkripsi = null;
    private final Model.koneksi con = new koneksi(); 
    
    /**
     * untuk seting file yang akan di enkripsi.
     * @param fileName
     */

     public static final double SPACE_KB = 1024;
     
    public static final double SPACE_MB = 1024 * SPACE_KB;
    //private String extension;
    private int progressCount;
    private int maxProgress;
    //final JDialog dlg;
    JProgressBar dpb;
    
    public void setFile(String directory, String filename, String extens) {
        
        this.fileNameEncrypt = directory + "/" + "en." + filename+ "." + extens;
            System.out.println("fileNameEncrypt="+fileNameEncrypt);
     }
 /**
     * untuk mengambil nilai n dan e
     * yang di ambil dari file kunci public
     */
    public void setPublicKey(String filePublic) throws IOException {
         BufferedReader br = null;
        try {
            int batas = 0;
            String current;
            br = new BufferedReader(new FileReader(filePublic));//mebaca file PublicKey
           
            while ((current = br.readLine()) != null) {
                if (batas == 1) {
                    // set nilai modulo dari file kunci publik
                    this.p = current;
               
                } else if (batas == 2) {
              
                    this.g = current;
                 
                }
                  else if (batas == 3) {
                    // set nilai eksponen e dari file kunci publik
                    this.x = current;
         
                }
                batas++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {br.close();}
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
    
     /**
     * untuk melakukan penulisan file
     * yang telah dienkripsi
     * @param dataEncrypt 
     */
   
      
    
     public String enkrip_database(String database,String table) throws SQLException{
        con.setDatabase("jdbc:mysql://localhost/"+database);
        Connection connect = con.getConnection();
        String balik = null;
        ArrayList<String> Data = new ArrayList<>() ;
        String Dataget;
        int j = 0;
        Elgamal ed = new Elgamal();
        
        if(connect!=null){
            Statement sta = connect.createStatement();
            String sql = "SELECT * FROM "+table+";";
            System.out.println(sql);
            ResultSet res =sta.executeQuery(sql);
             ResultSetMetaData rsMetaData = res.getMetaData();
            int collumn = rsMetaData.getColumnCount();
            while(res.next()){
                for(int i=1;i<=collumn;i++){
                    Dataget = res.getString(i);
                    Data.add(Dataget);
//                    System.out.println(Data.get(j));
                    j++;
                } 
            }
            String[][] arrData = new String[Data.size()/collumn][collumn];
            int k = 0;
//            System.out.println(Data.size()/collumn);
            System.out.println("PLAIN TEXT");
            for(int i=0;i<Data.size()/collumn;i++){
                for(int a=0;a<collumn;a++){
                    arrData[i][a] = Data.get(k);
                    k++;
                  //  System.out.print(arrData[i][a]+" ");
                }
              
            }
            String chiper = null;
            
            for(int i=0;i<Data.size()/collumn;i++){
                Connection connect2 = con.getConnection();
                Connection connect3 = con.getConnection();
                for(int l=0;l<collumn;l++){
                    byte[] byteData = arrData[i][l].getBytes();
                    String[] data1 = new String[byteData.length];
            //System.out.println(data1);
                    for(int u=0; u < byteData.length; u++) {
                        data1[u] = String.valueOf(byteData[u] & 0xff); //unsigned integer
        
                    }
                    
                    //proses enkripsi RSA
                  String[] data2 = new String[data1.length];
                    for (int u = 0; u < data1.length; u++) {
                           int a = Integer.parseInt(data1[u]);
                        String b = Character.toString((char) a);
                         data2[u] = ed.encrypt(b, p, g, x);
                         System.out.print("data "+data2[u]);
                    }
                    
                    //mengubah value chipertetxt ke string
                    String[] x = new String[data2.length];
                    for(int u = 0; u < data2.length; u++) {
                        x[u] = data2[u];

                    }
                    int m=0;
                    
                    int columnCount = rsMetaData.getColumnCount();
//                    String query = "insert into "+table+" values(";
                    for(int u=1;u<=x.length;u++){
                        if(chiper==null){
                            chiper = "('";
                            chiper += x[u-1];
                         
                        }else{
                            chiper += x[u-1];
                            if(u!=x.length){
                               
                            }
                        }
                    }
                    if(l==collumn-1){
                        chiper += "');";
                    }else{
                        chiper += "','";
                    }
                    //System.out.println(chiper);
                    //System.out.println(x.length);
//                    for(int u = 1; u <= columnCount; u++){
                         String collumnName = rsMetaData.getColumnName(1);
//                         System.out.println(collumnName);
//                         Statement sta2 = connect2.createStatement();
//                         String sql2 = "INSERT INTO "+table+"("+collumnName+") values("+x[m]+") ;";
//                         ResultSet res2 =sta2.executeQuery(sql2);
//                    }
                    
                     // data5 adalah nilai yang akan di masukan kedalam file
//                    String data5 = StringUtils.join(x, " ");//menggabungkan file dengan spasi
//                    System.out.println(data5+" ");
                }
                String query = "insert into "+table+" values";
                String delete = "delete from "+table+";";
                query += chiper;
                System.out.println(query);
                Statement sta2 = connect2.createStatement();
                if(i==0){
                    Statement sta3 = connect3.createStatement();
                    int res1 = sta3.executeUpdate(delete);
                }
                int res2 = sta2.executeUpdate(query);
                chiper = null;
                query = null;
            }
        }
            else{
balik ="tidak ada koneksi / gagal";
}
        return balik;
    }
}
