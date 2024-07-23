package com.beyond.board.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class AopLogService {
    // AOP의 대상(공통화의 대상) 이 되는 controller, service 등의 위치를 명시(기준이 어노테이션임)
    @Pointcut("within(@org.springframework.stereotype.Controller *)") // 모든 컨트롤러를 대상으로 함
    public void controllerPointcut() {}

//    @Around("controllerPointcut()")
//    //jointPoint 은 사용자가 실행하고자 하는 코드를 의미하고 위에서 정의한 pointCut을 의미한다.
//    public Object controllerLogger(ProceedingJoinPoint joinPoint){
//        // 방법 1.
//        log.info("aop start");
//        log.info("Method명 : " + joinPoint.getSignature().getName());
//
//        // 직접 HttpServletRequest 객체를 꺼내는 법
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        log.info("HTTP 메서드" + request.getMethod());
//
//        Map<String, String[] > parameterMap =  request.getParameterMap();
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.valueToTree(parameterMap);
//        ObjectNode objectNode = objectMapper.valueToTree(parameterMap);
//        log.info("user inputs : " + objectNode);
//
//        Object result = null;
//        try {
//            // 사용자가 실행하고자 하는 코드 실행부
//            result = joinPoint.proceed();
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
//        log.info("aop end");
//        return result;
//    }
    // 사용방법 2 . Before, After 어노테이션
//    @Before("controllerPointcut()")
//    public void beforeController(JoinPoint joinPoint){
//
//        log.info("aop start");
//        log.info("Method명 : " + joinPoint.getSignature().getName());
//
//        // 직접 HttpServletRequest 객체를 꺼내는 법
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        log.info("HTTP 메서드" + request.getMethod());
//    }
//    @After("controllerPointcut()")
//    public void afterController(JoinPoint joinPoint){
//        log.info("aop end");
//    }
}
