package com.apoem.mmxx.eventtracking.signature;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.apoem.mmxx.eventtracking.exception.Suppose;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: VerifierCollator </p>
 * <p>Description: 校验器 </p>
 * <p>Date: 2020/7/27 10:48 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifierCollator {

    private Object preVerifyArg;
    private String sourceSystem;
    private String viaTimestamp;
    private String signWrinkles;
    private String passportSeal;
    private Strategies strategy;
    private boolean debugModeOn;

    public String purposed() {
        String result;

        // 根据键的自然顺序进行排序
        String preVerifyArgJsonString = JSONObject.toJSONString(preVerifyArg, SerializerFeature.SortField, SerializerFeature.MapSortField);
        String x = StringUtils.joinWith("&", sourceSystem, viaTimestamp, preVerifyArgJsonString);

        switch (strategy) {
            case SHA256:
                // 加盐、加盐
                x = StringUtils.joinWith("&", sourceSystem, viaTimestamp, preVerifyArgJsonString, passportSeal);
                result = Sha256Utils.encode(x);
                break;
            case MD5:
                // 加盐、加盐
                x = StringUtils.joinWith("&", sourceSystem, viaTimestamp, preVerifyArgJsonString, passportSeal);
                result = Md5Utils.encode(x);
                break;
            default:
                result = HmacSha256Utils.encode(x, passportSeal);
                break;
        }

        return result;
    }

    public void validate() {
        String purposed = purposed();
        String abbreviate = debugModeOn ? purposed : StringUtils.abbreviate(purposed, "******", 12);
        Suppose.AOP_VALIDATION.isTrue(StringUtils.equalsIgnoreCase(purposed, signWrinkles),
                "This API request validation is [FAILED], please check the [signWrinkles], " +
                        "it might like [" + abbreviate + "], " +
                        "but submitted is [" + signWrinkles + "].");
    }
}
