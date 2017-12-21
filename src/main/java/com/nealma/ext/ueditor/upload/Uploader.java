package com.nealma.ext.ueditor.upload;


import com.nealma.ext.ueditor.aliyunoss.OSSConfig;
import com.nealma.ext.ueditor.define.State;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Uploader {
	private HttpServletRequest request;
	private Map<String, Object> conf;
	private OSSConfig ossConfig;

	public Uploader(HttpServletRequest request, Map<String, Object> conf, OSSConfig ossConfig) {
		this.request = request;
		this.conf = conf;
		this.ossConfig=ossConfig;
	}

	public final State doExec() {
		
		State state = null;

		if ("true".equals(this.conf.get("isBase64"))) {
			state = Base64Uploader.save(this.request,
					this.conf);
		} else {
			state = BinaryUploader.save(this.request, this.conf, this.ossConfig);
		}

		return state;
	}
}
