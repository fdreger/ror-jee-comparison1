package net.bajobongo.ch.faktury.gui;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import net.bajobongo.ch.faktury.entity.Company;
import net.bajobongo.ch.faktury.service.CompanyService;


@ManagedBean
@RequestScoped
public class Companies extends AccountAware {
    @EJB
    CompanyService companyService;
    List<Company> companies;
    boolean moreCompanies = false;
    
    public List<Company> getCompanies() {
        return companies;
    }

    public boolean isMoreCompanies() {
        return moreCompanies;
    }
    
    @PostConstruct
    public void init(){
        companies = companyService.findCompaniesFor(account.getUser(), 101, 0);
        if (companies.size() > 100) {
            moreCompanies = true;
            companies.remove(100);
        } 
    }

    public void delete(Company c) {
        companyService.removeFor(account.getUser(), c);
        companies.remove(c);
    }
    
}
