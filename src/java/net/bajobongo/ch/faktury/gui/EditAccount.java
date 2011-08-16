package net.bajobongo.ch.faktury.gui;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class EditAccount extends AccountAware {
    private LoginForm form = new LoginForm();

    public LoginForm getForm() {
        return form;
    }
    
    public void save() {
        
    }
}
