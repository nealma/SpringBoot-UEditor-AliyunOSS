package com.nealma.ext.ueditor.upload;

import com.nealma.ext.ueditor.aliyunoss.OSSConfig;
import com.nealma.ext.ueditor.aliyunoss.OSSHelper;
import com.nealma.ext.ueditor.define.AppInfo;
import com.nealma.ext.ueditor.define.BaseState;
import com.nealma.ext.ueditor.define.State;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * create by neal.ma
 */
public class BinaryUploader{

    private static final Logger logger = LogManager.getLogger("BinaryUploader");

    public static final State save(HttpServletRequest request, Map<String, Object> conf, OSSConfig ossConfig) {
        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }
        logger.debug("ueditor : coming...");
        try {
            String  fileExt = ".png", fileName = "";
            long size = 0;
            String url = null;
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> it = multiRequest.getFileNames();
            while (it.hasNext()) {
                MultipartFile file = multiRequest.getFile(it.next().toString());
                if (file == null) {
                    continue;
                }
                fileName = file.getOriginalFilename();
                if (fileName.lastIndexOf(".") != -1) {
                    fileExt = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
                }
                if (!validType(fileExt, (String[]) conf.get("allowFiles"))) {
                    return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
                }
                if(!validSize(file.getSize(), ((Long) conf.get("maxSize")).longValue())){
                    return new BaseState(false, AppInfo.MAX_SIZE);
                }
                url = OSSHelper.getInstance(ossConfig).uploadFile(file.getInputStream(), fileExt);
            }
            State state = new BaseState(Boolean.TRUE);
            state.putInfo("url", url);
            state.putInfo("title", fileName);
            state.putInfo("original", fileName);
            state.putInfo("type", fileExt);
            state.putInfo("size", size);
            return state;
        } catch (FileNotFoundException e) {
            logger.debug("ueditor error : {} from upload", e);
            return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
        } catch (IOException e) {
            logger.debug("ueditor error : {} from upload", e);
            return new BaseState(false, AppInfo.IO_ERROR);
        } catch (Exception e) {
            logger.debug("ueditor error : {} from upload", e);
            return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
        }
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);
        return list.contains(type);
    }

    private static boolean validSize(long fileSize, long maxSize){
        return fileSize <= maxSize;
    }

}
