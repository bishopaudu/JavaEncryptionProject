package com.jobDemo.workDemo.FileEncrypt;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class FileEncryptService {

    public void fileEncryption(String inputFile, String outputFile,
                               SecretKey secretKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);

        try(FileInputStream fileInputStream = new FileInputStream(inputFile);
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream,cipher)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }

        }

    }
}
