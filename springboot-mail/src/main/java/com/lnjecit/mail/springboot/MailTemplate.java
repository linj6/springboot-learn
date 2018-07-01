package com.lnjecit.mail.springboot;

/**
 * 邮件模板
 *
 * @author lnj
 * @create 2018-06-29 16:04
 **/
public class MailTemplate {
    // 邮件模板所在文件夹
    public static final String TEMPLATE_DIR = "template/mail/";
    // html后缀
    public static final String HTML_SUFFIX = ".html";
    // velocity模板后缀
    public static final String VM_SUFFIX = ".vm";

    /**
     * 邮件模板枚举
     */
    public enum TemplateEnum {
        DRAGON("龙族", TEMPLATE_DIR + "dragon-template" + HTML_SUFFIX),
        // 下单邮件模板
        ANIMATION("动漫列表", TEMPLATE_DIR + "animation-template" + VM_SUFFIX);

        /**
         * 主题
         */
        private String subject;

        /**
         * 模板路径
         */
        private String path;

        TemplateEnum(String subject, String path) {
            this.subject = subject;
            this.path = path;
        }

        public static String of(String subject) {
            for (TemplateEnum templateEnum : TemplateEnum.values()) {
                if (templateEnum.getSubject() == subject) {
                    return templateEnum.path;
                }
            }
            return null;
        }

        public String getSubject() {
            return subject;
        }

        public String getPath() {
            return path;
        }
    }

}
