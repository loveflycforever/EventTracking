package com.apoem.mmxx.eventtracking.signature;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: HexUtil </p>
 * <p>Description: 十六进制工具 </p>
 * <p>Date: 2020/7/14 12:53 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public class HexUtils {

    private HexUtils() {
    }

    /**
     * 将加密后的字节数组转为16进制表示的字符串
     *
     * @param bytes 字节数组
     * @return 字符串
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String stmp;
        for (int n = 0; bytes != null && n < bytes.length; n++) {
            stmp = Integer.toHexString(bytes[n] & 0XFF);
            if (stmp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append('0');
            }
            stringBuilder.append(stmp);
        }
        return stringBuilder.toString();
    }

    /**
     * 将字符串转换成字节数组
     *
     * @param str 字符串
     * @return 字节数组
     */
    public static byte[] toByteArray(String str) {
        byte[] buf = new byte[str.length() / 2];
        int j = 0;
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) ((Character.digit(str.charAt(j++), 16) << 4) | Character
                    .digit(str.charAt(j++), 16));
        }
        return buf;
    }
}
