package com.sky.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 七牛Kodo工具
 *
 * @author ZJHAO
 * @date 2024/06/14
 */
@Data
@AllArgsConstructor
@Slf4j
public class QiniuKodoUtil {

    // 私有字符串端点;
    private String accessKey;
    private String secretKey;
    private String bucket;

    public String upload(byte[] bytes, String objectName) {

        // 秘钥配置
        Auth auth = Auth.create(accessKey, secretKey);
        String toBucket = auth.uploadToken(bucket);
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadong());
        // 创建上传工具
        UploadManager uploadManager = new UploadManager(cfg);

        try {
            // 上传
            Response response = uploadManager.put(bytes, objectName, toBucket);
        } catch (QiniuException ex) {
            ex.printStackTrace();
            if (ex.response != null) {
                System.err.println(ex.response);
                try {
                    String body = ex.response.toString();
                    System.err.println(body);
                } catch (Exception ignored) {
                }
            }
        }

        // 获取上传文件的url http://sf0h0y7bp.hd-bkt.clouddn.com/640.jpg
        // http://sf0h0y7bp.hd-bkt.clouddn.com/054363f5-5580-4a13-907f-bcd47de09874.png
        String cdn = "sf0h0y7bp.hd-bkt.clouddn.com";

        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder.append(cdn).append("/").append(objectName);
        log.info("文件上传到:{}", stringBuilder.toString());
        return stringBuilder.toString();

    }
}
