package com.apoem.mmxx.eventtracking.signature;

import java.security.MessageDigest;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: Md5Util </p>
 * <p>Description: 消息摘要算法 MD5 </p>
 * <p>Date: 2020/7/31 15:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class Md5Utils {

    private Md5Utils() {
    }

    private static MessageDigest getEncoder() {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return messageDigest;
    }

    /**
     * MD5加密
     *
     * @param message 报文
     * @return 加密后的报文
     */
    public static String encode(String message){
        MessageDigest encoder = getEncoder();
        byte[] digest = encoder.digest(message.getBytes());
        return HexUtils.byteArrayToHexString(digest);
    }

}
