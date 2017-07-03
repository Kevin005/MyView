package com.view.kevin.myview.activity.util;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Kevin on 2017/6/23.
 */

public class Decryption {
    private BufferedInputStream fileIn;
    private BufferedOutputStream fileOut;
    private long fileLength;
    private int arraySize;
    private byte[] array;
    private static byte key[] = {(byte) 0xef, (byte) 0xca, (byte) 0x21, (byte) 0x43, (byte) 0x2f, (byte) 0x43, (byte) 0x42, (byte) 0x23,
            (byte) 0x3e, (byte) 0xcd, (byte) 0x23, (byte) 0x87, (byte) 0x1e, (byte) 0x8f, (byte) 0x10, (byte) 0x98,};

    public Decryption(String fromFileName, String outFileName, int arraySize) throws IOException {
        this.fileIn = new BufferedInputStream(new FileInputStream(fromFileName), arraySize);
        this.fileOut = new BufferedOutputStream(new FileOutputStream(outFileName), arraySize);
        this.fileLength = fileIn.available();
        this.arraySize = arraySize;
    }

    public int read() throws IOException {
        byte[] tmpArray = new byte[arraySize];
        int bytes = fileIn.read(tmpArray);// 暂存到字节数组中
        if (bytes != -1) {
//            array = new byte[bytes];// 字节数组长度为已读取长度
//            System.arraycopy(tmpArray, 0, array, 0, bytes);// 复制已读取数据
            byte[] decrypt = decrypt(key, tmpArray);
            if (decrypt != null) {
                fileOut.write(decrypt);
            }
            return bytes;
        }
        return -1;
    }

    static byte[] decrypt(byte[] key, byte[] in) {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        byte[] out = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, keySpec);// 初始化
            out = cipher.update(in);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return out;
    }

    public void close() throws IOException {
        fileIn.close();
        fileOut.flush();
        fileOut.close();
        array = null;
    }

    public byte[] getArray() {
        return array;
    }

    public long getFileLength() {
        return fileLength;
    }

    public interface DecryptCallback {
        void success();
    }

    public static void start(String fromUri, String toUri, DecryptCallback callback) {
        try {
            Decryption reader = new Decryption(fromUri, toUri, 65552);
            long start = System.nanoTime();
            while (reader.read() != -1) ;
            long end = System.nanoTime();
            reader.close();
            callback.success();
            System.out.println("StreamFileReader: " + (end - start));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
