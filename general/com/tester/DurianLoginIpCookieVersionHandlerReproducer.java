package com.tester;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DurianLoginIpCookieVersionHandlerReproducer {

    public static void main(String[] args) throws Exception {
        byte[] keySpecBytes = {119, 76, -93, -102, -70, 117, 85, 96, -78, -74, 126, 117, 126, 7, -98, 77};
        byte[] ivSpecBytes = {-30, 116, -77, -25, 54, -21, -92, -100};
        var keySpec = new SecretKeySpec(keySpecBytes, "AES");
        var param = new IvParameterSpec(ivSpecBytes);
        var encryptor = Cipher.getInstance("AES");
        var decryptor = Cipher.getInstance("AES");
        encryptor.init(Cipher.ENCRYPT_MODE, keySpec, param);
        decryptor.init(Cipher.DECRYPT_MODE, keySpec, param);
    }

}
