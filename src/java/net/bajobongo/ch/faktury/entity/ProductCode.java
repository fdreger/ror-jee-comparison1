/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.bajobongo.ch.faktury.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProductCode implements Serializable {
    @Id @GeneratedValue
    private String id;

    @Override
    public String toString() {
        return id;
    }
    
}
