package com.helltractor.enjoy.controller.admin;

import com.helltractor.enjoy.constant.MessageConstant;
import com.helltractor.enjoy.result.Result;
import com.helltractor.enjoy.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        try {
            // 原始文件名称
            String originFilename = file.getOriginalFilename();

            // 截取原始文件的后缀
            String extension = originFilename.substring(originFilename.lastIndexOf("."));

            // 构建文件的新名称
            String objectName = UUID.randomUUID() + extension;

            // 文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);

            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败： {}", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}
