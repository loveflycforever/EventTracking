package com.apoem.mmxx.eventtracking.infrastructure;

import com.apoem.mmxx.eventtracking.DateUtils;
import com.apoem.mmxx.eventtracking.NumberUtils;
import com.apoem.mmxx.eventtracking.exception.BusinessException;
import com.apoem.mmxx.eventtracking.exception.Suppose;
import com.apoem.mmxx.eventtracking.infrastructure.biz.*;
import com.apoem.mmxx.eventtracking.exception.LogSupport;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.CommunityInfoDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.RangeDao;
import com.apoem.mmxx.eventtracking.infrastructure.dao.mongo.RangeDao2;
import com.apoem.mmxx.eventtracking.infrastructure.enums.RangeTypeEnum;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.CommunityInfoEntity;
import com.apoem.mmxx.eventtracking.infrastructure.po.entity.RangeEntity;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BizServiceImpl {

    @Value("${biz.service.baseUrl}")
    private String baseUrl;

    @Autowired
    private RangeDao rangeDao;

    @Autowired
    private RangeDao2 rangeDao2;

    @Autowired
    private CommunityInfoDao communityInfoDao;

    public void requestCityData() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build();

        BizInterface myServer = retrofit.create(BizInterface.class);
        Call<JsonRootBean> call1 = myServer.commonConf("e4e57fa25385a19f392710c016d915db", "11");

        Response<JsonRootBean> execute;
        try {
            execute = call1.execute();
        } catch (IOException e) {
            throw new BusinessException(Suppose.REQUEST_CITY_ERROR, e.getMessage());
        }

        log.info(LogSupport.info(DateUtils.literalYyyyMmDdHhMmSsSss(LocalDateTime.now()), execute.toString()));

        JsonRootBean body = execute.body();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        Integer dateDay = DateUtils.numericalYyyyMmDd(now);
        if (Objects.nonNull(body)) {

            List<Data> data = body.getData();
            for (Data datum : data) {
                String cityCode = datum.getCityCode();
                String cityName = datum.getCityName();
                Range range = datum.getRange();

                ford(dateDay, cityCode, cityName, RangeTypeEnum.SELL_PRICE, range.getSellPrice());
                ford(dateDay, cityCode, cityName, RangeTypeEnum.RENT_PRICE, range.getRentPrice());
                ford(dateDay, cityCode, cityName, RangeTypeEnum.NEW_PRICE, range.getNewPrice());
                ford(dateDay, cityCode, cityName, RangeTypeEnum.SELL_AREA, range.getSellArea());
                ford(dateDay, cityCode, cityName, RangeTypeEnum.RENT_AREA, range.getRentArea());
                ford(dateDay, cityCode, cityName, RangeTypeEnum.SELL_LAYOUT, range.getSellLayout());
                ford(dateDay, cityCode, cityName, RangeTypeEnum.RENT_LAYOUT, range.getRentLayout());
            }
        } else {
            log.error(execute.toString());
        }
    }

    private void ford(Integer dateDay, String cityCode, String cityName, RangeTypeEnum rangeTypeEnum, RangeItem rangeItem) {
        List<RangeEntity> result = new ArrayList<>();

        String type = rangeItem.getType();
        List<String> cnList = rangeItem.getCnList();
        List<List<Integer>> list = rangeItem.getList();
        if (CollectionUtils.isEmpty(cnList) || CollectionUtils.isEmpty(list) || cnList.size() != list.size()) {
            log.error("");
            throw new RuntimeException("");
        }

        for (int i = 0; i < list.size(); i++) {
            Integer lower = NumberUtils.trimToZero(list.get(i).get(0));
            Integer upper = NumberUtils.trimToZero(list.get(i).get(1));
            String label = StringUtils.trimToEmpty(cnList.get(i));

            RangeEntity rangeEntity = new RangeEntity();
            rangeEntity.setDateDay(dateDay);
            rangeEntity.setCity(cityCode);
            rangeEntity.setCityName(cityName);
            rangeEntity.setRangeTypeName(rangeTypeEnum.getName());
            rangeEntity.setLower(lower);
            rangeEntity.setUpper(upper);
            rangeEntity.setLabel(label);
            rangeEntity.setType(type);
            rangeEntity.setOrderNumber(i);
            rangeEntity.setMark(0);

            result.add(rangeEntity);
        }

        if (CollectionUtils.isNotEmpty(result)) {
            rangeDao2.makeDateDayHistory(dateDay, cityCode, rangeTypeEnum);
            rangeDao.insert(result);
            rangeDao2.deletePrevDateDay(dateDay, cityCode, rangeTypeEnum);
            rangeDao2.deleteDateDayHistory(dateDay, cityCode, rangeTypeEnum);
        }
    }

    public void requestCommunityData(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(baseUrl)
                .build();

        BizInterface myServer = retrofit.create(BizInterface.class);
        Call<List<JsonCommunityInfo>> res = myServer.getBlock("e4e57fa25385a19f392710c016d915db", "11");
        Response<List<JsonCommunityInfo>> execute;
        try {
            execute = res.execute();
        } catch (IOException e) {
            throw new BusinessException(Suppose.REQUEST_COMMUNITY_INFO_ERROR, e.getMessage());
        }
        List<JsonCommunityInfo> body = execute.body();
        String city;
        LocalDateTime now = LocalDateTime.now();
        Integer dateDay = DateUtils.numericalYyyyMmDd(now);

        if (CollectionUtils.isNotEmpty(body)) {
            for(JsonCommunityInfo jsonCommunityInfo : body){
                city = jsonCommunityInfo.getCity();
                List<CommunityInfo> list = jsonCommunityInfo.getList();
                for(CommunityInfo o : list){
                    CommunityInfoEntity entity = new CommunityInfoEntity();
                    entity.setCity(city);
                    entity.setDateDay(dateDay);
                    entity.setCommunityId(o.getCommunityId());
                    entity.setCommunityName(o.getCommunityName());
                    entity.setCommunityAvgPrice(o.getHousePrices());
                    entity.setCommunityLatitude(o.getCommunityLatitude());
                    entity.setCommunityLongitude(o.getCommunityLongitude());
                    entity.setPlateId(o.getCommunityId());
                    entity.setPlateName(o.getPlateName());
                    entity.setMark(0);
                    communityInfoDao.insert(entity);
                }
            }
        }
    }

}
