/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algo.ta;

import java.math.BigInteger;

/**
 *
 * @author albi
 */
public class tes {
    Elgamal ed = new Elgamal();
public static void main(String[] args) {
     Elgamal ed = new Elgamal();
    String g1 ="1962434541";
   String p= "2357";
   String g="2";
    String x= "1751";
    String enc = ed.encrypt(g1, p, g, x);
    System.out.println("enkripsi"+enc);
    String dec = ed.decrypt("1373 1338 194 2041 269 684 294 36 423 2083 1743 618  ", p, g, x);
    System.out.println("dec"+dec);
}
}