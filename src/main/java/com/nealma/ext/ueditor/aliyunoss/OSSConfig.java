package com.nealma.ext.ueditor.aliyunoss;

import lombok.Data;

@Data
public class OSSConfig {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String filePath;
    private String cdnEndpoint;
}
