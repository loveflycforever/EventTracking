package com.apoem.mmxx.eventtracking.admin.trial;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: AcqApi </p>
 * <p>Description:  </p>
 * <p>Date: 2020/11/5 8:55 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
public interface TestApi {

    @GET("common/testService")
    Call<ResponseBody> commonTestService();

    @GET("common/testDatabase")
    Call<ResponseBody> commonTestDatabase();

    @GET("common/taskStatus")
    Call<ResponseBody> commonTaskStatus();

    @GET("api/acq/testService")
    Call<ResponseBody> apiAcqTestService();
}
