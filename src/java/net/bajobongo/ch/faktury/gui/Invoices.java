package net.bajobongo.ch.faktury.gui;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import net.bajobongo.ch.faktury.entity.Invoice;
import net.bajobongo.ch.faktury.entity.SystemUser;
import net.bajobongo.ch.faktury.service.InvoiceService;

@ManagedBean
@RequestScoped
public class Invoices extends AccountAware {
    @EJB
    private InvoiceService invoiceService;
    private List<Invoice> invoices;
    private boolean moreInvoices;
    private int offset;

    public int getOffset() {
        return offset;
    }

    public void remove() {
        Long id = Long.valueOf(JsfUtils.getParam("id"));
        invoiceService.remove(account.getUser(), id);
    }
    
    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isMoreInvoices() {
        return moreInvoices;
    }
    
    public List<Invoice> getInvoices() {
        return invoices;
    }

    @PostConstruct
    public void init() {
        if (account.getUser() == null) return;
        
        invoices = invoiceService.findInvoicesFor(account.getUser(), 101, offset);
        if (invoices.size()==101) {
            invoices.remove(100);
            moreInvoices = true;
        }
    }    
}
