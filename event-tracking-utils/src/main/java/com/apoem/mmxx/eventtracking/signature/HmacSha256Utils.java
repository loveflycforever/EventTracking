package com.apoem.mmxx.eventtracking.signature;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: HmacSHA256Utils </p>
 * <p>Description: 消息摘要算法 HMAC SHA256 </p>
 * <p>Date: 2020/7/14 15:47 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class HmacSha256Utils {

    private HmacSha256Utils() {
    }

    private static Mac getEncoder(String secret) {
        Mac encoder;
        try {
            encoder = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            encoder.init(secretKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encoder;
    }

    /**
     * sha256_HMAC加密
     *
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String encode(final String message, final String secret) {
        Mac encoder = getEncoder(secret);
        byte[] bytes = encoder.doFinal(message.getBytes());
        return HexUtils.byteArrayToHexString(bytes);
    }
}
