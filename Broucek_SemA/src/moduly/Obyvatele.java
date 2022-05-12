/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moduly;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import soubory.PraceSeSoubory;
import struktura.AbstrDoubleList;
import struktura.IAbstrDoubleList;

/**
 *
 * @author ladik
 */
public class Obyvatele implements IObyvatele, Serializable {

    IAbstrDoubleList<Obec>[] seznam = new AbstrDoubleList[enumKraj.values().length];
    private static final String BIN_FILE = "zaloha.bin";

    public Obyvatele() {
        for (int i = 1; i < seznam.length; i++) {
            seznam[i] = new AbstrDoubleList<>();
        }
    }

    @Override
    public int importData(String soubor) {
        zrus(enumKraj.VSECHNY_KRAJE);
        Obec obec = null;
        int pocet = 0;
        String radek;

        try {
            BufferedReader br = new BufferedReader(new FileReader(soubor));
            while (true) {
                radek = br.readLine();
                if (radek == null) {
                    break;
                }
                //radek = radek.trim();

                String[] poleObce = radek.split(";");
                for (int i = 0; i < poleObce.length; i++) {
                    poleObce[i] = poleObce[i].trim();
                }
                
                int cisloKraje = Integer.valueOf(poleObce[0]);
                int psc = Integer.valueOf(poleObce[2]);
                String nazev = poleObce[3];
                int pocetMuzu = Integer.valueOf(poleObce[4]);
                int pocetZen = Integer.valueOf(poleObce[5]);
                int celkem = Integer.valueOf(poleObce[6]);

                obec = new Obec(cisloKraje, psc, nazev, pocetMuzu, pocetZen, celkem);
                vlozObec(obec, enumPozice.POSLEDNI, enumKraj.values()[cisloKraje]);

            }
        } catch (IOException ex) {
            Logger.getLogger(Obyvatele.class.getName()).log(Level.SEVERE, null, ex);

        }

        return pocet;
    }

    @Override
    public void vlozObec(Obec obec, enumPozice pozice, enumKraj kraj) {
        switch (pozice) {
            case PRVNI:
                seznam[kraj.getCislo()].vlozPrvni(obec);
                break;
            case DALSI:
                seznam[kraj.getCislo()].vlozNaslednika(obec);
                break;
            case PREDCHUDCE:
                seznam[kraj.getCislo()].vlozPredchudce(obec);
                break;
            case POSLEDNI:
                seznam[kraj.getCislo()].vlozPosledni(obec);
                break;
        }
    }

    @Override
    public Obec zpristupniObec(enumPozice pozice, enumKraj kraj) {
        Obec temp = null;
        switch (pozice) {
            case PRVNI:
                temp = seznam[kraj.getCislo()].zpristupniPrvni();
                break;
            case DALSI:
                temp = seznam[kraj.getCislo()].zpristupniNaslednika();
                break;
            case PREDCHUDCE:
                temp = seznam[kraj.getCislo()].zpristupniPredchudce();
                break;
            case POSLEDNI:
                temp = seznam[kraj.getCislo()].zpristupniPosledni();
                break;
            case AKTUALNI:
                temp = seznam[kraj.getCislo()].zpristupniAktualni();
                break;
        }
        return temp;
    }

    @Override
    public Obec odeberObec(enumPozice pozice, enumKraj kraj) {
        Obec temp = null;
        switch (pozice) {
            case PRVNI:
                temp = seznam[kraj.getCislo()].odeberPrvni();
                break;
            case DALSI:
                temp = seznam[kraj.getCislo()].odeberNaslednika();
                break;
            case PREDCHUDCE:
                temp = seznam[kraj.getCislo()].odeberPredchudce();
                break;
            case POSLEDNI:
                temp = seznam[kraj.getCislo()].odeberPosledni();
                break;
            case AKTUALNI:
                temp = seznam[kraj.getCislo()].odeberAktualni();
                break;
        }
        return temp;
    }

    @Override
    public float zjistiPrumer(enumKraj kraj) {
        float prumer = 0;
        int suma = 0;
        Iterator<Obec> iterator = seznam[kraj.getCislo()].iterator();
        while (iterator.hasNext()) {
            Obec next = iterator.next();
            suma += next.getCelkem();
        }
        return prumer = suma / seznam[kraj.getCislo()].getPocet();
    }

    @Override
    public Iterator<Obec> zobrazObce(enumKraj kraj) {
        return seznam[kraj.getCislo()].iterator();
    }

    @Override
    public Obec[] zobrazObceNadPrumer(enumKraj kraj) {
//        Obec[] pole = null;
//        int i = 0;
//        float prumer = zjistiPrumer(kraj);
//        Iterator<Obec> iterator = seznam[kraj.getCislo()].iterator();
//        while (iterator.hasNext()) {
//            Obec next = iterator.next();
//            if (next.getCelkem() > prumer) {
//                pole[i] = next;
//                i++;
//            }
//
//        }
//        return pole;
        return null;
    }

    @Override
    public void zrus(enumKraj kraj) {
        if (kraj == kraj.VSECHNY_KRAJE) {
            for (int i = 1; i < seznam.length; i++) {
                seznam[i].zrus();
            }
        } else {
            seznam[kraj.getCislo()].zrus();
        }
    }

    @Override
    public IAbstrDoubleList<Obec>[] getSeznam() {
        return seznam;
    }

    @Override
    public void ulozDoBin() throws IOException {
        try {
            PraceSeSoubory.zalohovatDoBin(BIN_FILE, (AbstrDoubleList<Obec>[]) seznam);
        } catch (IOException ex) {
            throw new IOException(ex);
        }

    }

    @Override
    public void nactiZBin() throws IOException {
        try {
            PraceSeSoubory.obnovZBin(BIN_FILE, (AbstrDoubleList<Obec>[]) seznam);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Obyvatele.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
