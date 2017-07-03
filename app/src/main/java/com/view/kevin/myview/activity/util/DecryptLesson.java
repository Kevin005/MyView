//package com.view.kevin.myview.activity.util;
//
//import android.os.Environment;
//
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.Cipher;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.spec.SecretKeySpec;
//
///**
// * Created by Kevin on 2017/6/23.
// */
//
//public class DecryptLesson {
//    private static byte k[] = {(byte) 0xef, (byte) 0xca, (byte) 0x21, (byte) 0x43, (byte) 0x2f, (byte) 0x43, (byte) 0x42, (byte) 0x23,
//            (byte) 0x3e, (byte) 0xcd, (byte) 0x23, (byte) 0x87, (byte) 0x1e, (byte) 0x8f, (byte) 0x10, (byte) 0x98,};
//
//    public static void descrypt() {
//        String uri = Environment.getExternalStorageDirectory().getPath() + "/.yqtec" + "/textbook" + "/小学英语-人教版.final";
//        String toUri = Environment.getExternalStorageDirectory().getPath() + "/.yqtec" + "/textbook" + "/小学英语-人教版.zip";
//        try {
//            File file = new File(uri);
//            FileInputStream fis = new FileInputStream(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream(fis);
//            byte[] buffer = new byte[65552];
//            int len = -1;
//            while((len = fis.read(buffer)) != -1) {
//                byte after[] = decrypt(k, buffer);
//                bos.write(after, 0, len);
//            }
//            bytesToFile(bos.toByteArray(),toUri);
//            bos.close();
//            fis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static File bytesToFile(byte[] b, String outputFile) {
//        File ret = null;
//        BufferedOutputStream stream = null;
//        try {
//            ret = new File(outputFile);
//            FileOutputStream fstream = new FileOutputStream(ret);
//            stream = new BufferedOutputStream(fstream);
//            stream.write(b);
//        } catch (Exception e) {
//            // log.error("helper:get file from byte process error!");
//            e.printStackTrace();
//        } finally {
//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                    // log.error("helper:get file from byte process error!");
//                    e.printStackTrace();
//                }
//            }
//        }
//        return ret;
//    }
//
//    static byte[] decrypt(byte[] key, byte[] in) {
//        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
//        byte[] out = null;
//        try {
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
//            cipher.init(Cipher.DECRYPT_MODE, keySpec);// 初始化
//            out = cipher.doFinal(in);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        }
//        return out;
//    }
//}
