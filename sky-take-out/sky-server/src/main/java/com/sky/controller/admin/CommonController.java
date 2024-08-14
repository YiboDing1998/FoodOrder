package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.utils.AliOssUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口，上传图片都用这个
 */

@RestController
@RequestMapping("/admin/common")
public class CommonController {

  @Autowired
  private AliOssUtil aliOssUtil;

  @PostMapping("/upload")
  public Result<String> upload(MultipartFile file) throws IOException {
    String originalName = file.getOriginalFilename();
    String extension = originalName.substring(originalName.lastIndexOf("."));
    String objectName = UUID.randomUUID().toString() + extension;
    String filePath = aliOssUtil.upload(file.getBytes(), objectName);
    return Result.success(filePath);
  }
}
