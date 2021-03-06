package com.example.sm4forandroid.Cipher;


import android.os.Build;
import android.support.annotation.RequiresApi;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import javax.crypto.spec.SecretKeySpec;

import static com.example.sm4forandroid.Cipher.SM4.decryptBASE64;
import static com.example.sm4forandroid.Cipher.SM4.encryptBASE64;

public class AES
{
    public static final String ALGORITHM_NAME="AES";
    public static final String ALGORITHM_NAME_CBC_PADDING_AES="AES/ECB/PKCS5Padding";

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String base64Encode(byte[] bytes) throws Exception {
            return encryptBASE64(bytes);
        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static byte[] base64Decode(String base64Code) throws Exception{
            return StringUtils.isEmpty(base64Code) ? null : decryptBASE64(base64Code);
        }


    public static byte[] aesEncryptToBytes(byte[] content, byte[] encryptKey) throws Exception {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key_padding(encryptKey), "AES"));

            return cipher.doFinal(content);
        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static String aesEncrypt(String content, String encryptKey) throws Exception {
            return base64Encode(aesEncryptToBytes(content.getBytes(), key_padding(encryptKey).getBytes()));
        }

    static byte[] aesDecryptByBytes(byte[] encryptBytes, byte[] decryptKey) throws Exception {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128);

            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key_padding(decryptKey), "AES"));
            byte[] decryptBytes = cipher.doFinal(encryptBytes);

            return decryptBytes;
        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
            return StringUtils.isEmpty(encryptStr) ? null : new String(aesDecryptByBytes(base64Decode(encryptStr), key_padding(decryptKey).getBytes()));
        }

    public static byte[] key_padding(byte[] key){
        int i;
        byte[] result = new byte[16];
        for(i=0;i<16;i++){
            result[i] = key[i%16];
        }
        return result;
    }
    public static String key_padding(String key){
        String result;
        StringBuilder keyBuilder = new StringBuilder(key);
        while(keyBuilder.length() < 16){
            keyBuilder.append("a");
        }
        key = keyBuilder.toString();
        result = key;
        return  result;
    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static void main(String[] args) throws Exception {
//        String content = "AAAAAAAAAAAAAAAAAAAAAAAAA";
//        System.out.println("加密前：" + content);
//        String KEY = "0123456789";// 密钥长度为16
//
//        System.out.println("加密密钥和解密密钥：" + KEY);
//
//        String encrypt = aesEncrypt(content, KEY);
//        System.out.println("加密后：" + encrypt);
//
//        String decrypt = aesDecrypt(encrypt, KEY);
//
//        System.out.println("解密后：" + decrypt);
//    }
}
