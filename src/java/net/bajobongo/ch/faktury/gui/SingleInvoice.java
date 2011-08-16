package net.bajobongo.ch.faktury.gui;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import net.bajobongo.ch.faktury.entity.Company;
import net.bajobongo.ch.faktury.entity.Invoice;
import net.bajobongo.ch.faktury.entity.InvoiceItem;
import net.bajobongo.ch.faktury.entity.Vat;
import net.bajobongo.ch.faktury.service.CompanyService;
import net.bajobongo.ch.faktury.service.InvoiceService;
import net.bajobongo.ch.faktury.service.ServiceException;
import org.primefaces.event.SelectEvent;


@ManagedBean
@ViewScoped
public class SingleInvoice extends AccountAware implements Serializable {
    Invoice invoice;
    @EJB
    InvoiceService invoiceService;
    @EJB
    CompanyService companyService;
    Long invoiceId;
    
    public List<String> companyNames(String part) {
//        return Arrays.asList("asdasd","dsfbsdfb","dsfdsf");
        System.out.println("part: [" + part + "]");
        List<String> res = companyService.companyNamesFor(account.getUser(), part);
        System.out.println(res);
        return res;
    }
    
    public void customerSelected(SelectEvent vce) {
        Company newCustomer = companyService.findCompanyByNameFor(account.getUser(), vce.getObject().toString());
        if (newCustomer != null) {
            invoice.setCustomer(newCustomer);
            invoice.setCustomerAddress(newCustomer.getAddress());
        }
    }
    
    public void principalSelected(SelectEvent vce) {
        Company newPrincipal = companyService.findCompanyByNameFor(account.getUser(), vce.getObject().toString());
        if (newPrincipal != null) {
            invoice.setPrincipal(newPrincipal);
            invoice.setPrincipalAddress(newPrincipal.getAddress());
        }
    }
    
    public Vat[] getVatTypes(){
        return Vat.values();
    }
    
    public void addItem() {
        invoice.getInvoiceItems().add(new InvoiceItem());
    }
    
    public void removeItem(InvoiceItem ii) {
        invoice.getInvoiceItems().remove(ii);
    }
    
    public void invoiceIdChanged(ValueChangeEvent vce) {
        loadInvoice((Long)vce.getNewValue());
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
        
    public void loadInvoice(Long id) {
        invoice = invoiceService.findInvoiceFor(account.getUser(), id);
    }

    public Invoice getInvoice() {
        return invoice;
    }
    
    @PostConstruct
    public void resetInvoice() {
        invoice = new Invoice();
        invoice.getInvoiceItems().add(new InvoiceItem());
        invoice.setOwner(account.getUser());
    }
    
    public String saveInvoice() {
        try {
            invoiceService.save(invoice);        
        } catch (ServiceException se) {
            JsfUtils.errors(se);
            return null;
        }
        return "/invoice/index?faces-redirect=true";
    }
    
    
}
