package com.apoem.mmxx.eventtracking.admin.trial;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: ApiConfig </p>
 * <p>Description:  </p>
 * <p>Date: 2020/11/5 13:55 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Configuration
public class ApiConfig {

    @Value("${test.service.sta.baseUrl}")
    private String staBaseUrl;

    @Value("${test.service.acq.baseUrl}")
    private String acqBaseUrl;

    @Bean(name = "staApi")
    public TestApi staApi() {
        return getTestApi(staBaseUrl);
    }

    @Bean(name = "acqApi")
    public TestApi acqApi() {
        return getTestApi(acqBaseUrl);
    }

    private TestApi getTestApi(String baseUrl) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build();

        return retrofit.create(TestApi.class);
    }
}
