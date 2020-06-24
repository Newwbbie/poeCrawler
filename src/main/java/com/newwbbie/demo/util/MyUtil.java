package com.newwbbie.demo.util;

import org.jasypt.util.text.BasicTextEncryptor;

public class MyUtil {



    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("newwbbie");
        String password = textEncryptor.encrypt("f2nMCYLKW4rNTJRm");
        System.out.println("password:"+password);
    }
}
