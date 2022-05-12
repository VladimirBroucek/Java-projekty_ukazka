/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package struktura;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author ladik
 */
public interface IAbstrDoubleList<T> extends Iterable<T>{
    
    void zrus(); //zrušení celého seznamu
    boolean jePrazdny(); //test naplněnosti seznamu
    
    void vlozPrvni(T data); // vložení prvku do seznamu na první místo
    void vlozPosledni(T data); // vložení prvku do seznamu na poslední místo
    void vlozNaslednika(T data); // vložení prvku do seznamu jakožto následníka aktuálního prvku
    void vlozPredchudce(T data); // vložení prvku do seznamu jakožto předchůdce aktuálního prvku
    
    T zpristupniAktualni();//zpřístupnění aktuálního prvku
    T zpristupniPrvni();//zpřístupnění prvního prvku
    T zpristupniPosledni();//zpřístupnění posledního prvku
    T zpristupniNaslednika();//zpřístupnění následníha aktuálního prvku
    T zpristupniPredchudce();//zpřístupnění předchůdce aktuálního prvku
    
    T odeberAktualni(); //odebere aktuální prvek
    T odeberPrvni();// odebere první prvek
    T odeberPosledni();//odebere poslední prvek
    T odeberNaslednika();//odebere následníka aktuálního prvku
    T odeberPredchudce();//odebere předchůdce aktuálního prvku
    
    @Override
    Iterator<T> iterator(); //iterátor -> průchod lin seznamem
    int getPocet();
    
    default Stream<T> stream(){
        return StreamSupport.stream(spliterator(),false);
    }
    
}
