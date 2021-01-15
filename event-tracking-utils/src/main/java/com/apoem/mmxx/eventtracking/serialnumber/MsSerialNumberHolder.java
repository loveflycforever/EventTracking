package com.apoem.mmxx.eventtracking.serialnumber;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: MsSerialNumberHolder </p>
 * <p>Description: 流水号处理器 </p>
 * <p>Date: 2020/7/21 15:24 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public final class MsSerialNumberHolder {

    private static final String MS_SERIAL_NUMBER = "msSerialNumber";

    private static final ThreadLocal<String> MS_SERIAL_NUMBER_HOLDER =  new NamedThreadLocal<>(MS_SERIAL_NUMBER);
    private static final ThreadLocal<String> INHERITABLE_MS_SERIAL_NUMBER_HOLDER = new NamedTransmittableThreadLocal<>(MS_SERIAL_NUMBER);

    private MsSerialNumberHolder() {
    }

    public static void resetMsSerialNumber() {
        MS_SERIAL_NUMBER_HOLDER.remove();
        INHERITABLE_MS_SERIAL_NUMBER_HOLDER.remove();
    }

    public static void setMsSerialNumber(String msSerialNumber) {
        // 默认可以继承
        setMsSerialNumber(msSerialNumber, true);
    }

    public static void setMsSerialNumber(String msSerialNumber, boolean inheritable) {
        if (msSerialNumber == null) {
            resetMsSerialNumber();
        }
        else {
            if (inheritable) {
                INHERITABLE_MS_SERIAL_NUMBER_HOLDER.set(msSerialNumber);
                MS_SERIAL_NUMBER_HOLDER.remove();
            }
            else {
                MS_SERIAL_NUMBER_HOLDER.set(msSerialNumber);
                INHERITABLE_MS_SERIAL_NUMBER_HOLDER.remove();
            }
        }
    }

    public static String getMsSerialNumber() {
        String msSerialNumber = MS_SERIAL_NUMBER_HOLDER.get();
        if (msSerialNumber == null) {
            msSerialNumber = INHERITABLE_MS_SERIAL_NUMBER_HOLDER.get();
        }
        return msSerialNumber;
    }
}
