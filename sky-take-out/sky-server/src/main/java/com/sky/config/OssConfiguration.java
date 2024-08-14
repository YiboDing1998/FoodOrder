package com.sky.config;


import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfiguration {//标识一个类为配置类，相当于传统的 Spring 配置文件（XML 文件）
  @Bean
  @ConditionalOnMissingBean
  public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
    return new AliOssUtil(aliOssProperties.getEndpoint(),
            aliOssProperties.getAccessKeyId(),
            aliOssProperties.getAccessKeySecret(),
            aliOssProperties.getBucketName()

            );
  }
}
