package com.course.business.controller.web;

import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.LineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @project: course
 * @description:
 * @author: ysp
 * @create: 2021/04/10
 */
@RestController("webLineController")
@RequestMapping("/web/line")
public class LineController {
    private static final Logger LOG = LoggerFactory.getLogger(LineController.class);
    public static final String BUSINESS_NAME = "学习路线";

    @Resource
    private LineService lineService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        lineService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
}
