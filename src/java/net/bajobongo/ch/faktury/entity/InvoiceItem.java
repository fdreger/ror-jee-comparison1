package net.bajobongo.ch.faktury.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class InvoiceItem implements Serializable {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String invoiceUnitName;
    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE})
    private ProductCode code;
    @Column(precision=8, scale=2)
    private BigDecimal count;
    @Column(precision=8, scale=2)
    private BigDecimal netto;
    @Column(precision=8, scale=2)
    private BigDecimal discount;
    @Column(precision=8, scale=2)
    private BigDecimal vat;
    @Enumerated(EnumType.STRING)
    private Vat vatType = Vat.V22;

    public ProductCode getCode() {
        return code;
    }

    public void setCode(ProductCode code) {
        this.code = code;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceUnitName() {
        return invoiceUnitName;
    }

    public void setInvoiceUnitName(String invoiceUnitName) {
        this.invoiceUnitName = invoiceUnitName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getNetto() {
        return netto;
    }

    public void setNetto(BigDecimal netto) {
        this.netto = netto;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public Vat getVatType() {
        return vatType;
    }

    public void setVatType(Vat vatType) {
        this.vatType = vatType;
    }
    
    
}
