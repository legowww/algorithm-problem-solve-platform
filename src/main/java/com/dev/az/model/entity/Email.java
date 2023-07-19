package com.dev.az.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Embeddable
public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b");

    @Column(nullable = false, unique = true)
    private String email;

    protected Email() {}

    private Email(String email) {
        this.email = email;
    }

    public static Email from(String email) {
        Assert.isTrue(validate(email), "잘못된 이메일 형식입니다.");
        return new Email(email);
    }

    private static boolean validate(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
