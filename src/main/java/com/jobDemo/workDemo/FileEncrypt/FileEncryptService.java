package com.jobDemo.workDemo.FileEncrypt;

import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


@Service
public class FileEncryptService {

    public byte[] encryptWithKey(String encryptionKey,byte[] fileBytes) throws Exception{
        SecretKeySpec keySpec = new SecretKeySpec(encryptionKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(fileBytes);

    }

    public byte[] decryptWithKey(byte[] encryptedFile, String decryptionKey) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(decryptionKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(encryptedFile);
    }
}
