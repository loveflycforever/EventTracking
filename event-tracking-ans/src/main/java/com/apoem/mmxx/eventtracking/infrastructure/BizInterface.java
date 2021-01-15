package com.apoem.mmxx.eventtracking.infrastructure;

import com.apoem.mmxx.eventtracking.infrastructure.biz.*;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.List;


public interface BizInterface {

    @POST("/dcenter/common-conf")
    @FormUrlEncoded
    Call<JsonRootBean> commonConf(@Field("accToken") String accToken, @Field("timestamp") String timestamp);

    @POST("/dcenter/get-block")
    @FormUrlEncoded
    Call<List<JsonCommunityInfo>> getBlock(@Field("accToken") String accToken, @Field("timestamp") String timestamp);

}
