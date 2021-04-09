package com.course.business.controller.web;

import com.course.server.dto.ClubberCourseDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ClubberCourseService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("webClubberCourseController")
@RequestMapping("/web/clubber-course")
public class ClubberCourseController {

    private static final Logger LOG = LoggerFactory.getLogger(ClubberCourseController.class);
    public static final String BUSINESS_NAME = "会员课程报名";

    @Resource
    private ClubberCourseService clubberCourseService;

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/enroll")
    public ResponseDto enroll(@RequestBody ClubberCourseDto clubberCourseDto) {
        // 保存校验
        ValidatorUtil.require(clubberCourseDto.getClubberId(), "会员id");
        ValidatorUtil.require(clubberCourseDto.getCourseId(), "课程id");

        ResponseDto responseDto = new ResponseDto();
        clubberCourseDto = clubberCourseService.enroll(clubberCourseDto);
        responseDto.setContent(clubberCourseDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/get-enroll")
    public ResponseDto getEnroll(@RequestBody ClubberCourseDto clubberCourseDto) {
        ResponseDto responseDto = new ResponseDto();
        clubberCourseDto = clubberCourseService.getEnroll(clubberCourseDto);
        responseDto.setContent(clubberCourseDto);
        return responseDto;
    }
}
