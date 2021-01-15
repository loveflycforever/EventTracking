package com.apoem.mmxx.eventtracking.serialnumber;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: Strings </p>
 * <p>Description:  </p>
 * <p>Date: 2020/7/22 8:04 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class Strings {

    private Strings() {
    }

    public static void hasText(String text, String message) {
        if (!hasText(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static boolean hasText(String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
