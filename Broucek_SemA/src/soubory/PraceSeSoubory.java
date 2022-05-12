/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soubory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import struktura.AbstrDoubleList;

/**
 *
 * @author ladik
 */
public class PraceSeSoubory {

    /**
     *
     * @param <T>
     * @param nazevSouboru
     * @param seznam
     * @throws java.io.IOException
     */
    public static <T> void zalohovatDoBin(String nazevSouboru, AbstrDoubleList<T>[] seznam) throws IOException {
        try {
            try (ObjectOutputStream vystup = new ObjectOutputStream(new FileOutputStream(nazevSouboru))) {
                for (int i = 1; i < seznam.length; i++) {
                    vystup.writeInt(seznam[i].getPocet());

                    Iterator<T> iterator = seznam[i].iterator();
                    while (iterator.hasNext()) {
                        vystup.writeObject(iterator.next());

                    }
                }
            }
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    public static <T> AbstrDoubleList<T>[] obnovZBin(String nazevSouboru, AbstrDoubleList<T>[] seznam) throws IOException, ClassNotFoundException {
        try {
            try (ObjectInputStream vstup = new ObjectInputStream(new FileInputStream(nazevSouboru))) {
                int pocetPrvku = vstup.readInt();
                for (int i = 1; i < seznam.length; i++) {
                    seznam[i].zrus();

                    for (int j = 1; j < pocetPrvku; j++) {
                        seznam[j].vlozPosledni((T) vstup.readObject());
                    }
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new IOException(ex);
        }
        return seznam;
    }

}
