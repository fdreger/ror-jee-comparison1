package net.bajobongo.ch.faktury.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
public class SystemUser implements Serializable {
    @Id
    @Pattern(regexp="^[a-z0-9]{7,15}$")
    private String login;
    private String hashedPassword;
    private String salt;
    @Pattern(regexp="^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$")
    private String email;
    private SystemUserStatus status = SystemUserStatus.ACTIVE;
    
    @ElementCollection
    private List<String> usedInvoiceItemUnits;

    public SystemUserStatus getStatus() {
        return status;
    }

    public void setStatus(SystemUserStatus status) {
        this.status = status;
    }
    
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<String> getUsedInvoiceItemUnits() {
        if (null == usedInvoiceItemUnits) {
            usedInvoiceItemUnits = new ArrayList<String>();
        }
        return usedInvoiceItemUnits;
    }
}
