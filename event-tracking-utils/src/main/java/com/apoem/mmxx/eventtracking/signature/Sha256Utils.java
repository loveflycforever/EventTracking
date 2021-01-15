package com.apoem.mmxx.eventtracking.signature;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: Sha256Utils </p>
 * <p>Description: 消息摘要算法 SHA256 </p>
 * <p>Date: 2020/7/31 15:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class Sha256Utils {

    private Sha256Utils() {
    }

    private static MessageDigest getEncoder() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return messageDigest;
    }

    /**
     * 利用Java原生的类实现SHA256加密
     *
     * @param message 报文
     * @return 加密后的报文
     */
    public static String encode(String message) {
        MessageDigest encoder = getEncoder();
        encoder.update(message.getBytes(StandardCharsets.UTF_8));
        byte[] digest = encoder.digest();
        return HexUtils.byteArrayToHexString(digest);
    }
}