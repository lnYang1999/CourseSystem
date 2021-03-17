package com.course.server.exception;

/**
 * @project: course
 * @description: 校验异常类
 * @author: ysp
 * @create: 2021/03/17
 */
public class ValidatorException extends RuntimeException{

    public ValidatorException(String message) {
        super(message);
    }
}
