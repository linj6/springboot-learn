package com.lnjecit.mail.springboot;

public interface MailService {

    /**
     * 发送简单邮件（内容为文本）
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送邮件（内容为html静态页面）
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送邮件（内容为veloctiy模板）
     *
     * @param to      收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendTemplateMail(String to, String subject, String content);
}
