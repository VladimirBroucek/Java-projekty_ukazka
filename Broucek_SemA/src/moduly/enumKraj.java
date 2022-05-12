/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moduly;

/**
 *
 * @author ladik
 */
public enum enumKraj {
    VSECHNY_KRAJE(0),PRAHA(1), STREDOCESKY_KRAJ(12), JIHOCESKY_KRAJ(2), PLZENSKY_KRAJ(11), KARLOVARSKY_KRAJ(4), USTECKY_KRAJ(13), LIBERECKY_KRAJ(7),
    KRALOVEHRADECKY_KRAJ(6), PARDUBICKY_KRAJ(10), VYSOCINA_KRAJ(5), JIHOMORAVSKY_KRAJ(3), OLOMOUCKY_KRAJ(9), MORAVSKOSLEZKY_KRAJ(8), ZLINSKÝ_KRAJ(14);

    private final int cislo;

    private enumKraj(int cislo) {
        this.cislo = cislo;
    }

    public int getCislo() {
        return cislo;
    }

    public static Enum[] getKraje() {
        Enum[] vycetKraju = {PRAHA, STREDOCESKY_KRAJ, JIHOCESKY_KRAJ, PLZENSKY_KRAJ, KARLOVARSKY_KRAJ, USTECKY_KRAJ, LIBERECKY_KRAJ,
            KRALOVEHRADECKY_KRAJ, PARDUBICKY_KRAJ, VYSOCINA_KRAJ, JIHOMORAVSKY_KRAJ, OLOMOUCKY_KRAJ, MORAVSKOSLEZKY_KRAJ, ZLINSKÝ_KRAJ};
        return vycetKraju;
    }

}
