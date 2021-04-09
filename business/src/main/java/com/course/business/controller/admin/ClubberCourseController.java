package com.course.business.controller.admin;

import com.course.server.dto.ClubberCourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ClubberCourseService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/clubberCourse")
public class ClubberCourseController {

    private static final Logger LOG = LoggerFactory.getLogger(ClubberCourseController.class);
    public static final String BUSINESS_NAME = "会员课程报名";

    @Resource
    private ClubberCourseService clubberCourseService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        clubberCourseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ClubberCourseDto clubberCourseDto) {
        // 保存校验
        ValidatorUtil.require(clubberCourseDto.getClubberId(), "会员id");
        ValidatorUtil.require(clubberCourseDto.getCourseId(), "课程id");
        ValidatorUtil.require(clubberCourseDto.getAt(), "报名时间");

        ResponseDto responseDto = new ResponseDto();
        clubberCourseService.save(clubberCourseDto);
        responseDto.setContent(clubberCourseDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        clubberCourseService.delete(id);
        return responseDto;
    }
}
