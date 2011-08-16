package net.bajobongo.ch.faktury.service;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Validator;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import net.bajobongo.ch.faktury.entity.Company;
import net.bajobongo.ch.faktury.entity.SystemUser;

@Stateless
public class CompanyService {

    static Locale locale = new Locale("pl");
    static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    @PersistenceContext
    EntityManager em;

    public List<String> companyNamesFor(SystemUser su, String part) {
        return em.createQuery("select c.address.name from Company c where c.owner = :user and lower(c.address.name) like :part")
                .setMaxResults(15)
                .setParameter("user", su)
                .setParameter("part", "%" + part.toLowerCase(locale) + "%")
                .getResultList();
    }

    public Set<? extends ConstraintViolation> validateCompany(Company c) {
        return validator.validate(c);
    }

    public void saveFor(SystemUser user, Company c) {
        if (!validateCompany(c).isEmpty()) {
            throw new ServiceException(validateCompany(c));
        }
        if (c.getId() != null) {
            Company orig = em.find(Company.class, c.getId());
            if (!orig.getOwner().getLogin().equals(user.getLogin())) {
                throw new AuthorisationException();
            }
        }
        em.merge(c);
    }

    public List<Company> findCompaniesFor(SystemUser user, int limit, int offset) {
        List<Company> res = em.createQuery("select c from Company c where c.owner = :owner").setParameter("owner", user).setMaxResults(limit).setFirstResult(offset).getResultList();
        return res;
    }

    public void removeFor(SystemUser user, Company c) {
        Company company = em.merge(c);
        try {
            em.remove(company);
        } catch (Exception se) {
            throw new ServiceException("Error while deleting company");
        }
    }

    public Company findCompanyFor(SystemUser user, Long id) {
        Company res = em.find(Company.class, id);
        if (!res.getOwner().equals(user)) {
            throw new AuthorisationException();
        }
        return res;
    }

    public Company findCompany(Long id) {
        return em.find(Company.class, id);
    }
    
    public Company findCompanyByNameFor(SystemUser user, String name) {
        List<Company> results = em.createQuery("select c from Company c where c.address.name = :name and c.owner = :user")
                .setParameter("name", name).setParameter("user", user)
                .setMaxResults(1)
                .getResultList();
        if (results.size() == 0) return null;
        return results.get(0);
    }
}
