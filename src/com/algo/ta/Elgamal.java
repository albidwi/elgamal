/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algo.ta;



import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * @author isahroni
 */
public class Elgamal {

    private static BigInteger p, g, y, x;
   


    public String encrypt(String message, String p1,String g1,String x1){
      

       p = new BigInteger(p1);
g = new BigInteger(g1);
x= new BigInteger(x1);
        //check nilai pgx
        if (p.intValue() < 255) {
            System.out.println("Bilangan Harus Lebih Besar Dari 255");
            return null;
        } else if (g.intValue() < 1 | g.intValue() >= p.intValue() - 1) {
            System.out.println("Nilai g : 1 < g <= p-1");
            return null;
        } else if (x.intValue() < 1 | x.intValue() >= p.intValue() - 2) {
            System.out.println("Nilai x : 1 < x <= p-2");
            return null;
        }else{

        }
        //kelas untuk hitung y
        BuatKunci bk = new BuatKunci();
        KonversiChar converter = new KonversiChar();
        BilanganAcak number = new BilanganAcak();
        EnkripDekrip ed = new EnkripDekrip();

        bk.setPrima(p.intValue());

        if (bk.isPrima() == false) {
            System.out.println(p + " bukan bilangan prima");
            return null;
        }


        BigInteger y = bk.getKunci(p, g, x);
 

        //konversi char ke ASCII
        ArrayList chr = converter.getCharASCII(message);

        //membuat nilai random
        ArrayList rn = number.getBilanganAcak(message, p);


      
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
       
        }

        String cipher = "";
        String hasilEnkrip = "";
        //==============================================================
        //Proses Enkripsi
        for (int i = 0; i < message.length(); i++) {
            cipher = ed.getEnkripsi(chr.get(i).toString(),
                    rn.get(i).toString(), g, p, y, message);
            hasilEnkrip += cipher;
        }

      
        return hasilEnkrip;
    }

    public String decrypt(String message, String p1,String g1,String x1){

       p = new BigInteger(p1);
g = new BigInteger(g1);
x= new BigInteger(x1);
  

  

        //Proses Dekripsi
        PecahChiperText pct = new PecahChiperText();

        pct.setChiper(message);

        ArrayList ngama = pct.getGamma();
        ArrayList ndelta = pct.getDelta();
        EnkripDekrip ed = new EnkripDekrip();

        String hasilDekrip = "";
        for (int i = 0; i < ngama.size(); i++) {
            char dek = ed.getDekripsi(ngama.get(i).toString(),
                    ndelta.get(i).toString(), p, x,message);
            hasilDekrip += dek;
        }

        return hasilDekrip;

    }

   

    //checks whether an int is prime or not.
    public boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n % 2 == 0) return false;
        //if not, then just check the odds
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

   
}
