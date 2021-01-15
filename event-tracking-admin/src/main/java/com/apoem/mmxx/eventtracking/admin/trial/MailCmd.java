package com.apoem.mmxx.eventtracking.admin.trial;

import javax.mail.Session;

public class MailCmd {
    private final Session session;
    private final String mailFrom;
    private final String[] mailTo;
    private final String mailTittle;
    private final String mailContent;

    /**
     */
    public MailCmd(Session session, String mailfrom, String[] mailTo, String mailTittle, String mailText) {
        this.session = session;
        mailFrom = mailfrom;
        this.mailTo = mailTo;
        this.mailTittle = mailTittle;
        mailContent = mailText;
    }

    public Session getSession() {
        return session;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public String[] getMailTo() {
        return mailTo;
    }

    public String getMailTittle() {
        return mailTittle;
    }

    public String getMailContent() {
        return mailContent;
    }
}
