package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import com.sky.utils.QiniuKodoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 *
 * @author ZJHAO
 * @date 2024/06/14
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
@RequiredArgsConstructor
public class CommonController {

    private final AliOssUtil aliOssUtil;
    private final QiniuKodoUtil qiniuKodoUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        try {
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            // 获取文件后缀 abc.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 拼接文件名
            String objectName = UUID.randomUUID() + extension;

            String filePath = qiniuKodoUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException ex) {
            log.error("文件上传失败：{}", ex);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
