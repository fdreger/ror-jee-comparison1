/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.bajobongo.ch.faktury.gui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import net.bajobongo.ch.faktury.entity.Country;

/**
 *
 * @author filip
 */
@ManagedBean
@RequestScoped
public class Countries {

    public Country[] getCountries() {
        return Country.values();
    }
}
