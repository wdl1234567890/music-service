package com.fl.wdl.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.PolicyConditions;

@Service
@RefreshScope
public class OSSService {

	@Value("${oss.accessId}")
	String accessId;

	@Value("${oss.accessKey}")
	String accessKey;

	@Value("${oss.endpoint}")
	String endpoint;

	@Value("${oss.bucket}")
	String bucket;

	public Map<String, String> getOSSPostObjectParams() {
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey);
		try {
			long expireTime = 30;
			long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
			Date expiration = new Date(expireEndTime);
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_TYPE, "image/*");
			policyConds.addConditionItem("accessid", accessId);
			String host = "https://" + bucket + "." + endpoint;
			policyConds.addConditionItem("host", host);
			policyConds.addConditionItem(PolicyConditions.COND_SUCCESS_ACTION_STATUS, "200");
			String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
			String postSignature = ossClient.calculatePostSignature(postPolicy);
			Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("host", host);
            respMap.put("successActionStatus", "200");
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            return respMap;
		} catch (Exception e) {

		} finally {
			ossClient.shutdown();
		}
		return null;
	}
}
