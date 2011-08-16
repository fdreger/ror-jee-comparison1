/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.bajobongo.ch.faktury.entity;

/**
 *
 * @author filip
 */
public enum Country {
    PL, EN, FR, RU;

    private String getName() {
        // TODO: nazwy krajów dorobić różne, nie tylko identyfikatory
        return toString();
    }
    
}
