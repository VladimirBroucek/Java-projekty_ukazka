/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package struktura;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author ladik
 * @param <T>
 */
public class AbstrDoubleList<T> implements IAbstrDoubleList<T>, Serializable {

    private class Prvek {

        T data;
        Prvek dalsi;
        Prvek predchozi;

        public Prvek(T data) {
            this.data = data;
        }

    }

    private Prvek prvni;
    private Prvek aktualni;
    private Prvek posledni;

    private int pocetPrvku = 0;

    @Override
    public void zrus() {
        prvni = null;
        aktualni = null;
        posledni = null;
        pocetPrvku = 0;
    }

    @Override
    public boolean jePrazdny() {
        return pocetPrvku <= 0;
    }

    @Override
    public void vlozPrvni(T data) {
        Objects.requireNonNull(data);
        Prvek prvek = new Prvek(data);
        if (prvni == null) {
            prvni = prvek;
            posledni = prvni;
            aktualni = prvni;
        } else {
            prvni.predchozi = prvek;
            prvek.dalsi = prvni;
            prvni = prvek;
        }
        pocetPrvku++;
    }

    @Override
    public void vlozPosledni(T data) {
        Objects.requireNonNull(data);
        Prvek prvek = new Prvek(data);
        if (prvni == null) {
            posledni = prvek;
            prvni = posledni;
        } else {
            prvek.predchozi = posledni;
            posledni.dalsi = prvek;
            posledni = posledni.dalsi;
        }
        pocetPrvku++;
    }

    @Override
    public void vlozNaslednika(T data) {
        Objects.requireNonNull(data);
        if (prvni == null) {
            vlozPrvni(data);
        } else {
            Prvek prvek = new Prvek(data);
            Prvek temp = aktualni.dalsi;

            if (prvni.dalsi == null) {
                prvni.dalsi = prvek;
                aktualni.predchozi = prvni;
            }

            aktualni.dalsi = prvek;
            prvek.predchozi = aktualni;
            aktualni = prvek;
        }

        pocetPrvku++;
    }

    @Override
    public void vlozPredchudce(T data) {
        if(prvni == null){
            vlozPrvni(data);
        }else{
            Prvek prvek = new Prvek(data);
            Prvek temp = aktualni.predchozi;
            
            if(temp == null){
                vlozPrvni(data);
            }
            
            prvek.predchozi = temp;
            prvek.dalsi = aktualni;
            
            temp.dalsi = prvek;
            aktualni.predchozi = prvek;
            aktualni = prvek;
            
            pocetPrvku++;
        }
    }

    @Override
    public T zpristupniAktualni() {
        if (jePrazdny()) {
            return null;
        } else {
            return aktualni.data;
        }
    }

    @Override
    public T zpristupniPrvni() {
        if (jePrazdny()) {
            return null;
        } else {
            aktualni = prvni;
            return prvni.data;
        }
    }

    @Override
    public T zpristupniPosledni() {
        if (jePrazdny()) {
            return null;
        } else {
            aktualni = posledni;
            return posledni.data;
        }
    }

    @Override
    public T zpristupniNaslednika() {
        if (jePrazdny()) {
            return null;
        }

        if (aktualni.dalsi != null) {
            aktualni = aktualni.dalsi;
            return aktualni.data;
        } else {
            return aktualni.data;
        }

    }

    @Override
    public T zpristupniPredchudce() {
        if (jePrazdny()) {
            return null;
        }

        if (aktualni.predchozi != null) {
            aktualni = aktualni.predchozi;
            return aktualni.data;
        } else {
            return aktualni.data;
        }
    }

    @Override
    public T odeberAktualni() {
        jePrazdny();
        T temp = null;
        if (aktualni != null) {

            if (aktualni == prvni) {
                prvni = prvni.dalsi;
            } else if (aktualni == posledni) {
                posledni = posledni.predchozi;
                posledni.dalsi = null;
            } else {
                temp = aktualni.data;
                aktualni.predchozi.dalsi = aktualni.dalsi;
                aktualni.dalsi.predchozi = aktualni.predchozi;

            }
            pocetPrvku--;

        }
        return temp;
    }

    @Override
    public T odeberPrvni() {
        jePrazdny();
        T temp = null;

        if (prvni.dalsi == null) {
            temp = prvni.data;
            zrus();
        } else {
            temp = prvni.data;
            aktualni = prvni.dalsi;
            prvni = prvni.dalsi;
            prvni.predchozi = null;

        }
        pocetPrvku--;
        return temp;

    }

    @Override
    public T odeberPosledni() {
        jePrazdny();
        T temp = null;

        if (posledni == prvni) {
            temp = odeberPrvni();
            zrus();
        } else if (posledni == aktualni) {
            temp = aktualni.data;
            posledni = aktualni.predchozi;
            posledni.dalsi = null;
            aktualni = aktualni.predchozi;
            aktualni.dalsi = null;

            pocetPrvku--;
        } else {
            temp = posledni.data;
            posledni = posledni.predchozi;
            posledni.dalsi.predchozi = null;
            posledni.dalsi = null;

            pocetPrvku--;
        }
        return temp;
    }

    @Override
    public T odeberNaslednika() {
        jePrazdny();
        T temp = null;

        if (aktualni == posledni) {
            temp = odeberPosledni();
            return null;
        } else {
            temp = aktualni.dalsi.data;
            if (aktualni.dalsi.dalsi != null) {
                aktualni.dalsi = aktualni.dalsi.dalsi;
                aktualni.dalsi.predchozi = aktualni;
            } else {
                return odeberPosledni();
            }
            pocetPrvku--;
        }
        return temp;
    }

    @Override
    public T odeberPredchudce() {
        jePrazdny();
        T temp = null;

        if (aktualni == prvni || aktualni.dalsi == prvni) {
            temp = odeberPrvni();
        } else {
            temp = aktualni.predchozi.data;
            aktualni.predchozi.predchozi.dalsi = aktualni;
            aktualni.predchozi = aktualni.predchozi.predchozi;
            pocetPrvku--;
        }
        return temp;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator() {
            Prvek ukazatel = prvni;

            @Override
            public boolean hasNext() {
                return ukazatel != null;
            }

            @Override
            public Object next() {
                if (hasNext()) {
                    T data = ukazatel.data;
                    ukazatel = ukazatel.dalsi;
                    return data;
                }
                throw new NoSuchElementException();
            }
        };
    }
    
    public int getPocet(){
        return pocetPrvku;
    }

}
