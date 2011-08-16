/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.bajobongo.ch.faktury.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.ejb.ApplicationException;
import javax.validation.ConstraintViolation;

@ApplicationException(rollback=true)
public class ServiceException extends RuntimeException {
    
    private final Set<? extends ConstraintViolation> violations;

    public ServiceException(Throwable cause) {
        super(cause);
        violations = null;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        violations = null;
    }

    public ServiceException(String message) {
        super(message);
        violations = null;
    }

    public ServiceException() {
        violations = null;
    }

    public ServiceException(Set<? extends ConstraintViolation> violations) {
        this.violations = violations;
    }

    public Set<ConstraintViolation> getViolations() {
        return violations == null? Collections.EMPTY_SET : violations;
    }

}
