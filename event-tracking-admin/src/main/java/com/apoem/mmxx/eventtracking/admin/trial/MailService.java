package com.apoem.mmxx.eventtracking.admin.trial;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@Slf4j
public class MailService {

    /**
     * 指明邮件的发件人
     * 指明邮件的发件人登陆密码
     * 指明邮件的收件人
     * 邮件的标题
     * 邮件的文本内容
     * 邮件的服务器域名
     */
    @Value("${test.mail.from}")
    private String mailFrom;
    @Value("${test.mail.from.password}")
    private String passwordMailFrom;
    @Value("#{'${test.mail.to}'.split(',')}")
    private String[] mailTo;
    @Value("${test.mail.host}")
    private String mailHost;
    @Value("${test.mail.port}")
    private String mailPort;

    public void sendError(String mailText) {
        String mailTittle = "系统异常！！！";
        try {
            getherMail(mailText, mailTittle);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sendInfo(String mailText) {
        String mailTittle = "系统报告。。。";
        try {
            getherMail(mailText, mailTittle);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void getherMail(String mailText, String mailTittle) throws Exception {
        log.info("发送邮件mailTittle {}，mailText {} 开始", mailTittle, mailText);

        Properties prop = new Properties();
        prop.setProperty("mail.smtp.ssl.enable", "true");
        prop.setProperty("mail.host", mailHost);
        prop.setProperty("mail.smtp.port", mailPort);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");

        // 使用JavaMail发送邮件的5个步骤

        // 1、创建session
//        Session session = Session.getInstance(prop)
        // 阿里云默认屏蔽25端口，所以将25端口换成465，发送SSL邮件
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailFrom , passwordMailFrom);
            }
        });

        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        // 2、通过session得到transport对象
        Transport ts = session.getTransport();
        // 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(mailHost, mailFrom, passwordMailFrom);
        // 4、创建邮件
        Message message = createSimpleMail(new MailCmd(session, mailFrom, mailTo, mailTittle, mailText));
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        // 6、关闭会话
        ts.close();
        log.info("发送邮件 结束");
    }

    /**
     *
     * @param mailAggre 参数集
     * @return MimeMessage
     * @throws Exception Exception
     */
    public MimeMessage createSimpleMail(MailCmd mailAggre) throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(mailAggre.getSession());
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(mailAggre.getMailFrom()));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        String[] mailTo = mailAggre.getMailTo();
        InternetAddress[] addresses = new InternetAddress[mailTo.length];
        for (int i = 0; i < mailTo.length; i++) {
            addresses[i] = new InternetAddress(mailTo[i]);
        }
        message.setRecipients(Message.RecipientType.TO, addresses);
        // 邮件的标题
        message.setSubject(mailAggre.getMailTittle());
        // 邮件的文本内容
        message.setContent(mailAggre.getMailContent(), "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        return message;
    }

}