package mx.ipn.escom.pizarron.crud.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GenerateRandom {

    private GenerateRandom(){}

    public static String getRandom() throws NoSuchAlgorithmException {
        return SecureRandom.getInstanceStrong().ints(48, 123)
                .filter(num -> (num<58 || num>64) && (num<91 || num>96))
                .limit(12)
                .mapToObj(c -> (char)c).collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                .toString();
    }
}
