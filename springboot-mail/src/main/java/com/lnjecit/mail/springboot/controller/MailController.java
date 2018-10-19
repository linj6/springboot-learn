package com.lnjecit.mail.springboot.controller;

import com.lnjecit.mail.springboot.MailTemplate;
import com.lnjecit.mail.springboot.service.MailService;
import com.lnjecit.mail.springboot.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lnj
 * @create 2018-06-30 21:34
 **/
@RequestMapping("/mail")
@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    String to = "l1779668895@gmail.com";

    @GetMapping("/sendSimpleMail")
    public ResponseEntity sendSimpleMail() {
        mailService.sendSimpleMail(to, "简单邮件发送测试", "举杯邀明月，对影成三人。");
        return new ResponseEntity("简单邮件发送成功", HttpStatus.OK);
    }

    @GetMapping("/sendHtmlMail")
    public ResponseEntity sendHtmlMail() {
        try {
            String content = MailUtil.getHtmlTemplateText(MailTemplate.TemplateEnum.DRAGON.getPath());
            mailService.sendHtmlMail(to, "Html邮件发送测试", content);
            return new ResponseEntity("Html邮件发送成功", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Html邮件发送失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sendTemplateMail")
    public ResponseEntity sendTemplateMail() {
        try {
            Map<String, Object> map = new HashMap<>();
            List<String> animationList = new ArrayList<>();
            animationList.add("秦时明月");
            animationList.add("天行九歌");
            animationList.add("一人之下");
            map.put("animationList", animationList);

            String content = MailUtil.getTemplateText(MailTemplate.TemplateEnum.ANIMATION.getPath(), map);
            mailService.sendTemplateMail(to, "Html邮件发送测试", content);
            return new ResponseEntity("模板邮件发送成功", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity("Html邮件发送失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
