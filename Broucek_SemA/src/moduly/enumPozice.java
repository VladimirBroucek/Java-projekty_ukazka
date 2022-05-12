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
public enum enumPozice {
    PRVNI,
    POSLEDNI,
    PREDCHUDCE,
    DALSI,
    AKTUALNI;
    

    public static Enum[] getPozice() {
        Enum[] kraje = {PRVNI, POSLEDNI, PREDCHUDCE, DALSI};
        return kraje;
    }

}
