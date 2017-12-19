package com.nealma.ext.ueditor.aliyunoss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class OSSHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OSSConfig ossConfig;

    public String uploadFile(InputStream inputStream, String fileExt){
        if(inputStream == null){
            return null;
        }
        String filename = ossConfig.getOssFilePath() + genFilename(fileExt);
        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        try{
            ossClient.putObject(ossConfig.getBucketName(), filename, inputStream);
            inputStream.close();
        } catch (OSSException oe) {
            logger.error("Fail to upload file, error message: {}, error code: {} ,Request ID: {}, Host ID: {}", oe.getErrorMessage(), oe.getErrorCode(), oe.getRequestId(), oe.getHostId());
            return null;
        } catch (ClientException ce) {
            logger.error("Fail to upload file, error message: {}", ce.getErrorMessage());
            return null;
        } catch (IOException ioe) {
            logger.error("Fail to upload file, error message: {}", ioe.getMessage());
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return ossConfig.getCdnEndpoint() + filename;
    }

    private String genFilename(String fileExt){
        return UUID.randomUUID().toString().replaceAll("-", "") + fileExt;
    }

    public static OSSHelper getInstance(){
        return new OSSHelper();
    }
}
