package net.bajobongo.ch.faktury.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

public class AuthorisationException extends ServiceException {

    public AuthorisationException(Set<? extends ConstraintViolation> violations) {
        super(violations);
    }

    public AuthorisationException() {
        super(Collections.singleton(new ConstraintViolation(){

            @Override
            public String getMessage() {
                return "User unauthorized";
            }

            @Override
            public String getMessageTemplate() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object getRootBean() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Class getRootBeanClass() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object getLeafBean() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Path getPropertyPath() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Object getInvalidValue() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public ConstraintDescriptor getConstraintDescriptor() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }));
    }

    public AuthorisationException(String message) {
        super(message);
    }

    public AuthorisationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorisationException(Throwable cause) {
        super(cause);
    }
    
}
