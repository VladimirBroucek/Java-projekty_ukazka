/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

import java.util.Random;
import moduly.Obec;
import struktura.AbstrDoubleList;

/**
 *
 * @author ladik
 */
public final class Generator {

    private static final Random random = new Random();

    private Generator() {
    }

    public static void naplnPole(AbstrDoubleList<Obec>[] pole) {
        for (int i = 0; i < 10; i++) {
            Obec obec = generujObec();
            pole[obec.getCisloKraje()].vlozPosledni(obec);
        }
    }

    private static Obec generujObec() {
        Obec obec = null;
        return obec = new Obec(novyCisloKraje(), novaPSC(), novyNazevObce(), novyPocetMuzu(), novyPocetZen(), novyCelkem());
    }

    public static int novaPSC() {
        return 10000 + random.nextInt(99999);
    }

    public static String novyNazevObce() {
        StringBuilder nazev = new StringBuilder();
        nazev.append(random.nextInt(10))
                .append((char) ('A' + random.nextInt(27)))
                .append((char) ('a' + random.nextInt(27)))
                .append((char) ('a' + random.nextInt(27)))
                .append((char) ('a' + random.nextInt(27)))
                .append((char) ('a' + random.nextInt(27)));

        return nazev.toString();
    }

    public static int novyPocetMuzu() {
        return random.nextInt(1000) + 500;
    }

    public static int novyPocetZen() {
        return random.nextInt(2000) + 1000;
    }

    public static int novyCelkem() {
        return novyPocetMuzu() + novyPocetZen();
    }
    
    public static int novyCisloKraje(){
        int cisloKraje = (int) ((Math.random() * (14 - 1)) +1);
        return cisloKraje;
    }

}
