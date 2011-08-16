package net.bajobongo.ch.faktury.gui;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import net.bajobongo.ch.faktury.service.ServiceException;

public class JsfUtils {

    public static String getParam(String param) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(param);
    }
    
    public static void errors(ServiceException ex){
        if (ex.getMessage() != null && !ex.getMessage().equals("")) {
            error(ex.getMessage(), null);
        }
        for (ConstraintViolation v : ex.getViolations()) {
            error(v.getMessage(),null);
        }
    }
    
    public static void info(String summary, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, message));
    }

    public static void error(String summary, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, message));
    }
    
    
}
