package com.course.business.controller.web;

import com.course.server.dto.CourseLinePageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseLineService;
import com.course.server.util.ValidatorUtil;
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
@RestController("webCourseLineController")
@RequestMapping("/web/courseLine")
public class CourseLineController {

    private static final Logger LOG = LoggerFactory.getLogger(com.course.business.controller.web.CourseLineController.class);
    public static final String BUSINESS_NAME = "课程路线";

    @Resource
    private CourseLineService courseLineService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody CourseLinePageDto courseLinePageDto) {
        ResponseDto responseDto = new ResponseDto();
        ValidatorUtil.require(courseLinePageDto.getLineId(), "学习路线ID");
        courseLineService.list(courseLinePageDto);
        responseDto.setContent(courseLinePageDto);
        return responseDto;
    }
}
