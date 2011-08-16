package net.bajobongo.ch.faktury.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Set;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import net.bajobongo.ch.faktury.entity.SystemUser;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Stateless
public class UserService {

    @PersistenceContext
    EntityManager em;
    static ValidatorFactory validatorF = Validation.buildDefaultValidatorFactory();
    private Validator validator = validatorF.getValidator();
    private static final Pattern passwordValidator = Pattern.compile(
            "^\\S{7,100}$");

    public boolean isPasswordValid(String email) {
        return passwordValidator.matcher(email).matches();
    }

    public SystemUser login(String login, String password) {
        if (login == null || password == null) {
            throw new ServiceException("password or login is null");
        }
        SystemUser su = em.find(SystemUser.class, login);
        if (su == null) {
            return null;
        }
        final String hashedGivenPassword = createHash(password, su.getSalt());
        if (su.getHashedPassword().equals(hashedGivenPassword)) {
            return su;
        }
        return null;
    }

    public void createUser(String login, String password, String email) {
        if (!isPasswordValid(password)) {
            throw new ServiceException("Password should be between 7 and 100 characters");
        }
        final SystemUser su = em.find(SystemUser.class, login);
        if (su != null) {
            throw new ServiceException("login already in use");
        }

        final SystemUser newUser = new SystemUser();
        newUser.setEmail(email);
        newUser.setLogin(login);
        Set<ConstraintViolation<SystemUser>> results = validator.validate(newUser);
        if (!results.isEmpty()) {
            throw new ServiceException(results);
        }
        final String salt = generateRandomSalt();
        newUser.setSalt(salt);
        newUser.setHashedPassword(createHash(password, salt));
        em.persist(newUser);
    }

    private String generateRandomSalt() {
        SecureRandom random;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException ex) {
            throw new ServiceException(ex);
        }
        byte[] bSalt = new byte[8];
        random.nextBytes(bSalt);
        return byteToBase64(bSalt);
    }

    private String createHash(String password, String salt) {
        try {
            int count = 1000;
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update(base64ToByte(salt));
            byte[] input = md.digest(password.getBytes("UTF-8"));
            while (count-- >= 0) {
                md.reset();
                input = md.digest(input);
            }
            return byteToBase64(input);
        } catch (UnsupportedEncodingException ex) {
            throw new ServiceException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new ServiceException(ex);
        }
    }

    private static byte[] base64ToByte(String data) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(data);
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }

    private static String byteToBase64(byte[] data) {
        BASE64Encoder endecoder = new BASE64Encoder();
        return endecoder.encode(data);
    }
}
