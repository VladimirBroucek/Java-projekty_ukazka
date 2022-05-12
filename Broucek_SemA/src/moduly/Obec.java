/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moduly;

import java.io.Serializable;

/**
 *
 * @author ladik
 */
public class Obec implements Serializable{
    
    private int PSC;
    private String nazevObce;
    private int pocetMuzu;
    private int pocetZen;
    private int celkem;
    private final int cisloKraje;
    
    public Obec(int cisloKraje, int psc, String nazev, int pocetM, int pocetZ, int celkem){
        this.PSC = psc;
        this.nazevObce = nazev;
        this.pocetMuzu = pocetM;
        this.pocetZen = pocetZ;
        this.celkem = celkem;
        this.cisloKraje = cisloKraje;

    }


    @Override
    public String toString() {
        return "Nazev Obce: " +this.nazevObce+ " ;PSC: " +this.PSC+ " ;Počet mužů: " +this.pocetMuzu+ " ;Počet žen: " +this.pocetZen+ " ;Celkem obyvatel: " + this.celkem;
    }

    public int getPSC() {
        return PSC;
    }

    public void setPSC(int PSC) {
        this.PSC = PSC;
    }

    public String getNazevObce() {
        return nazevObce;
    }

    public void setNazevObce(String nazevObce) {
        this.nazevObce = nazevObce;
    }

    public int getPocetMuzu() {
        return pocetMuzu;
    }

    public void setPocetMuzu(int pocetMuzu) {
        this.pocetMuzu = pocetMuzu;
    }

    public int getPocetZen() {
        return pocetZen;
    }

    public void setPocetZen(int pocetZen) {
        this.pocetZen = pocetZen;
    }

    public int getCelkem() {
        return celkem;
    }

    public void setCelkem(int celkem) {
        this.celkem = celkem;
    }

    public int getCisloKraje() {
        return cisloKraje;
    }
    
}
