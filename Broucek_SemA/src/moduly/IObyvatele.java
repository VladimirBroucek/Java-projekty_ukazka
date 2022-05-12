/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moduly;

import java.io.IOException;
import java.util.Iterator;
import struktura.IAbstrDoubleList;




/**
 *
 * @author ladik
 */
public interface IObyvatele {
    
    int importData(String soubor);
    void vlozObec(Obec obec, enumPozice pozice, enumKraj kraj);
    Obec zpristupniObec(enumPozice pozice, enumKraj Kraj);
    Obec odeberObec(enumPozice pozice, enumKraj Kraj );
    float zjistiPrumer(enumKraj Kraj);
    public Iterator<Obec> zobrazObce(enumKraj kraj);
    //void zobrazObceNadPrumer(enumKraj Kraj);
    public Obec[] zobrazObceNadPrumer(enumKraj kraj);
    void zrus(enumKraj Kraj);
    void ulozDoBin()throws IOException;
    public void nactiZBin() throws IOException;
    public IAbstrDoubleList<Obec>[] getSeznam();

    

    
}
