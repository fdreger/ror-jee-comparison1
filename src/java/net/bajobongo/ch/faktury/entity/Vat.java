package net.bajobongo.ch.faktury.entity;

public enum Vat {
    V0, VNONE, V7, V10, V22, V23;
    
    public String getName(){
        // TODO: ładne nazwy watów
        return toString();
    }
}
