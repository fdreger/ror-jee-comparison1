package net.bajobongo.ch.faktury.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Invoice implements Serializable  {
    @Id @GeneratedValue
    private Long id;
    @Size(min=3)
    private String number;
    @Embedded @NotNull @Valid
    private Address customerAddress;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "principal_name")),
        @AttributeOverride(name = "line1", column = @Column(name = "principal_line1")),
        @AttributeOverride(name = "line2", column = @Column(name = "principal_line2")),
        @AttributeOverride(name = "country", column = @Column(name = "principal_country")),
        @AttributeOverride(name = "vatno", column = @Column(name = "principal_vatno"))
    })
    private Address principalAddress;
    @ManyToOne
    private Company principal;
    @ManyToOne
    private Company customer;
    @ManyToOne
    private SystemUser owner;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date invoiceDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date saleDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date paymentDue;
    @OneToMany(cascade= CascadeType.ALL, orphanRemoval=true)
    private List<InvoiceItem> invoiceItems;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Company getCustomer() {
        return customer;
    }

    public void setCustomer(Company customer) {
        this.customer = customer;
    }

    public Address getCustomerAddress() {
        if (customerAddress == null) {
            customerAddress = new Address();
        }
        return customerAddress;
    }

    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public List<InvoiceItem> getInvoiceItems() {
        if (invoiceItems==null) {
            invoiceItems = new ArrayList<InvoiceItem>();
        }
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public Date getPaymentDue() {
        return paymentDue;
    }

    public void setPaymentDue(Date paymentDue) {
        this.paymentDue = paymentDue;
    }

    public Company getPrincipal() {
        return principal;
    }

    public void setPrincipal(Company principal) {
        this.principal = principal;
    }

    public Address getPrincipalAddress() {
        if (principalAddress == null) {
            principalAddress = new Address();
        }
        return principalAddress;
    }

    public void setPrincipalAddress(Address principalAdddress) {
        this.principalAddress = principalAdddress;
    }

    public SystemUser getOwner() {
        return owner;
    }

    public void setOwner(SystemUser owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Invoice other = (Invoice) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    
}
