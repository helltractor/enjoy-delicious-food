package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.alioss")
@Data
public class AliOssProperties {
    
    private String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    private String accessKeyId = "LTAI5t6tLJZUguPNuhzUarAs";
    private String accessKeySecret = "BVgkBLarf2nBQloYRkrlxTJfQkp81p";
    private String bucketName = "helltractor";
    
}
