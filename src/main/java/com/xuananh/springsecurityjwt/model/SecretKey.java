package com.xuananh.springsecurityjwt.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class SecretKey {
    @Value("${secret.key}")
    private String key;

    private final String CUC_CU = "em hay noi xem nhung dieu tu truoc gio em khong that tha";

    @Override
    public String toString() {
        return key + CUC_CU;
    }
}
