package net.bajobongo.ch.faktury.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import net.bajobongo.ch.faktury.entity.Invoice;
import net.bajobongo.ch.faktury.entity.SystemUser;

@Stateless
public class InvoiceService {
    
    @PersistenceContext
    EntityManager em;
    
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    public Set<ConstraintViolation<Invoice>> validateInvoice(Invoice i){
        return validator.validate(i);
    }
    
    public void save(Invoice i) {
        Set<ConstraintViolation<Invoice>> result = validateInvoice(i);
        if (!result.isEmpty()) {
            throw new ServiceException(result);
        }
        em.merge(i);
    }
    
    public List<Invoice> findInvoicesFor(SystemUser user, int limit, int offset) {
        return em.createQuery(
                    "select i from Invoice i where i.owner = :owner"
                    + " order by i.invoiceDate desc, i.paymentDue desc, i.id desc"
                )
                .setParameter("owner", user)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }
    
    public List<Invoice> findAllInvoices(int limit, int offset) {
        return em.createNamedQuery("Invoice.findAll")
                .setFirstResult(offset).setMaxResults(limit).getResultList();
    }
    
    public Invoice findInvoice(Long id) {
        return em.find(Invoice.class, id);
    }
    
    public Invoice findInvoiceFor(SystemUser user, Long id) {
        Invoice i = findInvoice(id);
        
        if (i != null && i.getOwner() != null && !i.getOwner().getLogin().equals(user.getLogin())) {
            throw new AuthorisationException();
        }
        return i;
    }

    public void remove(SystemUser user, Long id) {
        remove(user, em.find(Invoice.class, id));
    }
    
    public void remove(SystemUser user, Invoice invoice) {
        Invoice orig = em.find(Invoice.class, invoice.getId());
        if (
                invoice.getOwner() != null
                && invoice.getOwner().getLogin() != null
                && !invoice.getOwner().getLogin().equals(orig.getOwner().getLogin())) {

            throw new AuthorisationException();
        }
        em.remove(orig);
    }
}
