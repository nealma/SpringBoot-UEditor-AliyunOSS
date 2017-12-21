package com.nealma.ext.ueditor.aliyunoss;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfiger {
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;
    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;
    @Value("${aliyun.oss.file-path}")
    private String filePath;
    @Value("${aliyun.cdn.endpoint}")
    private String cdnEndpoint;

    @Bean
    public OSSConfig ossConfig(){
        OSSConfig ossConfig = new OSSConfig();
        ossConfig.setCdnEndpoint(cdnEndpoint);
        ossConfig.setAccessKeyId(accessKeyId);
        ossConfig.setAccessKeySecret(accessKeySecret);
        ossConfig.setBucketName(bucketName);
        ossConfig.setEndpoint(endpoint);
        ossConfig.setFilePath(filePath);
        return ossConfig;
    }
}
