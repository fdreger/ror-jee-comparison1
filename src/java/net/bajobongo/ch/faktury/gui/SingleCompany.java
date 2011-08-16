package net.bajobongo.ch.faktury.gui;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.validation.Valid;
import net.bajobongo.ch.faktury.entity.Company;
import net.bajobongo.ch.faktury.service.CompanyService;
import net.bajobongo.ch.faktury.service.ServiceException;

@ManagedBean
@ViewScoped
public class SingleCompany extends AccountAware implements Serializable {
    @Valid
    private Company company;
    @EJB
    private CompanyService companyService;
    private Long companyId;

    public void debug(Object o) {
        System.out.println(":::: " + o);
    }
    
    public Company getCompany() {
        return company;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void loadCompany(ValueChangeEvent vce) {
        company = companyService.findCompany((Long)vce.getNewValue());
    }
    
    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
        
    @PostConstruct
    public void reset(){
        company = new Company();
        company.setOwner(account.getUser());
    }
    
    public String save(){
        try {
            companyService.saveFor(account.getUser(), company);        
        } catch (ServiceException se) {
            JsfUtils.errors(se);
            return null;
        }
        return "/company/index?faces-redirect=1";
    }
    
    
}
