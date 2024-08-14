package com.sky.aspect;


import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import jdk.internal.classfile.MethodSignature;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {


  /**
   * 切点
   */
  @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
  public void autoFillPointCut() {

  }

  /**
   * 通知，前置通知
   */
  @Before("autoFillPointCut()")
  public void autoFill(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
    OperationType operationType = autoFill.value();
    Object[] args = JoinPoint.getArgs();
    Object entity = args[0];
    LocalDateTime now = LocalDateTime.now();
    Long id = BaseContext.getCurrentId();
    if (operationType == operationType.INSERT) {
      Method setCreateTime = entity.getClass().getDeclaredMethod(, LocalDateTime.class);
      Method entity.getClass().getDeclaredMethod(, Long.class);
      setCreateTime.invoke(entity, now);
    } else {

    }


  }
}
