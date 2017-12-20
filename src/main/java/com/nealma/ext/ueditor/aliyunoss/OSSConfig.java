package com.nealma.ext.ueditor.aliyunoss;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class OSSConfig {
    @Value("aliyun.oss.endpoint")
    private String endpoint;
    @Value("aliyun.oss.access-key")
    private String accessKeyId;
    @Value("aliyun.oss.access-key-secret")
    private String accessKeySecret;
    @Value("aliyun.oss.bucket-name")
    private String bucketName;
    @Value("aliyun.oss.file-path")
    private String filePath;
    @Value("aliyun.cdn.endpoint")
    private String cdnEndpoint;
}
