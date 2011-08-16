package net.bajobongo.ch.faktury.gui;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import net.bajobongo.ch.faktury.entity.SystemUser;
import net.bajobongo.ch.faktury.service.AuthorisationException;
import net.bajobongo.ch.faktury.service.ServiceException;
import net.bajobongo.ch.faktury.service.UserService;


@ManagedBean
@SessionScoped
public class Account implements Serializable {
    @EJB
    private UserService userService;
    private SystemUser user;
    
    public boolean isLoggedIn() {
        String vid = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            if (user == null && !(vid.equals("/index.xhtml") || vid.equals("/user/create_account.xhtml"))) {
            throw new AuthorisationException();
        }
        return user != null;
    }

    public String logOut() {
        user = null;
        return "/index?faces-redirect=1";
    }
    
    public void logIn(String login, String password) {
        user = (userService.login(login, password));
        if (user==null) {
            JsfUtils.error("Wrong credentials","try again");
        } else {
            JsfUtils.info("you are now signed in",null);
        }
        
    }
    
    public void createUser(String login, String email, String password, String passwordConfirm) {
        if (passwordConfirm==null || !passwordConfirm.equals(password)) {
            JsfUtils.error("Password and password confirmation do not match",null);
            return;
        }
        try {
            userService.createUser(login, password, email);
            JsfUtils.info("account created",null);
            logIn(login, password);
        } catch (ServiceException se) {
            JsfUtils.error(se.getMessage(),null);
        }
    }
    
    public SystemUser getUser() {
        return user;
    }
}
