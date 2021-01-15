package com.apoem.mmxx.eventtracking.admin.trial;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Project: Event Tracking </p>
 * <p>Name: TestServiceImpl </p>
 * <p>Description:  </p>
 * <p>Date: 2020/11/5 11:31 </p>
 * <p>Company: Apoem, Co. All Rights Reserved. </p>
 *
 * @author papafan
 * @version v1.0
 */
@Service
@Slf4j
public class TestService {

    @Autowired
    private TestApi staApi;

    @Autowired
    private TestApi acqApi;

    @Autowired
    private MailService mailService;

    private static String lastString = "";

    private static final AtomicInteger COUNT_STA_SERVICE = new AtomicInteger();
    private static final AtomicInteger COUNT_ACQ_SERVICE = new AtomicInteger();

    @Scheduled(cron = "0 30 7 * * ? ")
    public void staTestTask() {
        Call<ResponseBody> objectCall = staApi.commonTaskStatus();
        log.info("每日任务执行情况报告-开始");
        try {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<table border='2'>");

            stringBuilder.append("<tr>");
            stringBuilder.append("<th>dateDay</th>");
            stringBuilder.append("<th>taskName</th>");
            stringBuilder.append("<th>status</th>");
            stringBuilder.append("<th>taskDesc</th>");
            stringBuilder.append("<th>createTime</th>");
            stringBuilder.append("<th>updateTime</th>");
            stringBuilder.append("<th>updateUserId</th>");
            stringBuilder.append("<th>updateUserName</th>");
            stringBuilder.append("<th>mark</th>");
            stringBuilder.append("<th>order</th>");
            stringBuilder.append("</tr>");

            String body = Objects.requireNonNull(objectCall.execute().body()).string();
            JsonObject result = JsonParser.parseString(body).getAsJsonObject();

            JsonArray data = result.getAsJsonArray("data");
            for (JsonElement daraElement : data) {
                stringBuilder.append("<tr>");
                JsonObject dataObject = daraElement.getAsJsonObject();

                stringBuilder.append("<td>");
                stringBuilder.append(dataObject.get("dateDay"));
                stringBuilder.append("</td>");

                stringBuilder.append("<td>");
                stringBuilder.append(dataObject.get("taskName"));
                stringBuilder.append("</td>");

                stringBuilder.append("<td>");
                stringBuilder.append(dataObject.get("status"));
                stringBuilder.append("</td>");

                stringBuilder.append("<td>");
                stringBuilder.append(dataObject.get("taskDesc"));
                stringBuilder.append("</td>");

                stringBuilder.append("<td>");
                stringBuilder.append(dataObject.get("createTime"));
                stringBuilder.append("</td>");

                stringBuilder.append("<td>");
                stringBuilder.append(dataObject.get("updateTime"));
                stringBuilder.append("</td>");

                stringBuilder.append("<td>");
                stringBuilder.append(dataObject.get("updateUserId"));
                stringBuilder.append("</td>");

                stringBuilder.append("<td>");
                stringBuilder.append(dataObject.get("updateUserName"));
                stringBuilder.append("</td>");

                stringBuilder.append("<td>");
                stringBuilder.append(dataObject.get("mark"));
                stringBuilder.append("</td>");

                stringBuilder.append("<td>");
                stringBuilder.append(dataObject.get("order"));
                stringBuilder.append("</td>");

                stringBuilder.append("</tr>");
            }

            stringBuilder.append("</table>");
            mailService.sendInfo("每日任务执行情况报告。<br>" + stringBuilder.toString());
        } catch (Exception e) {
            log.error("每日任务执行情况报告-异常", e);
            mailService.sendError(
                    "测试统计服务器每日任务执行情况报告可用性的过程中产生错误。<br>"
                            + e.toString());
        }
        log.info("每日任务执行情况报告-结束");
    }

    @Scheduled(cron = "0 58 6/5 * * ? ")
    public void staTestDatabase() {
        Call<ResponseBody> objectCall = staApi.commonTestDatabase();
        log.info("测试统计服务器数据库可用性-开始");
        try {
            String mailText = Objects.requireNonNull(objectCall.execute().body()).string();

            if (StringUtils.equalsIgnoreCase(lastString, mailText)) {
                mailService.sendInfo("测试统计服务器数据库可用性的过程中数据未增长。<br>" +
                        "上次数据为：" + lastString + "<br>" +
                        "本次数据为：" + mailText);
            }
            lastString = mailText;
        } catch (Exception e) {
            log.error("测试统计服务器数据库可用性-异常", e);
            mailService.sendError(
                    "测试统计服务器数据库可用性的过程中产生错误。<br>"
                            + e.toString());
        }
        log.info("测试统计服务器数据库可用性-结束");
    }

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void staTestService() {
        Call<ResponseBody> objectCall = staApi.commonTestService();
        log.info("测试统计服务器服务可用性-开始");
        try {
            Objects.requireNonNull(objectCall.execute().body()).string();
        } catch (Exception e) {
            log.error("测试统计服务器服务可用性-异常", e);
            if (COUNT_STA_SERVICE.get() <= 15) {
                mailService.sendError(
                        "测试统计服务器服务可用性的过程中产生错误。<br>"
                                + e.toString());
                COUNT_STA_SERVICE.incrementAndGet();
            }
        }
        log.info("测试统计服务器服务可用性-结束");
    }

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void acqTestService() {
        Call<ResponseBody> objectCall = acqApi.apiAcqTestService();
        log.info("测试收集服务器服务可用性-开始");
        try {
            Objects.requireNonNull(objectCall.execute().body()).string();
        } catch (Exception e) {
            log.error("测试收集服务器服务可用性-异常", e);
            if (COUNT_ACQ_SERVICE.get() <= 15) {
                mailService.sendError(
                        "测试收集服务器服务可用性的过程中产生错误。<br>"
                                + e.toString());
                COUNT_ACQ_SERVICE.incrementAndGet();
            }
        }
        log.info("测试收集服务器服务可用性-结束");
    }
}
