package com.newwbbie.poeCrawler.util;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MyUtil {

    /**
     * 发送邮件
     * @param sender 发送者
     * @param from 发送地址
     * @param to 接收地址
     * @param title 标题
     * @param context 内容
     */
    public static void sendEmail(JavaMailSender sender, String from, String to, String title, String context) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to); // 接收地址,可传入数组进行群发
            message.setSubject(title); // 标题
            message.setText(context); // 内容
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("newwbbie");
        String password = textEncryptor.encrypt("f2nMCYLKW4rNTJRm");
        System.out.println("password:"+password);
    }
}
