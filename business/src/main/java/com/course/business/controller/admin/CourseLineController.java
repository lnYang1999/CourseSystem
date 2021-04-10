package com.course.business.controller.admin;

import com.course.server.dto.CourseLineDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseLineService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/courseLine")
public class CourseLineController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseLineController.class);
    public static final String BUSINESS_NAME = "课程路线";

    @Resource
    private CourseLineService courseLineService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        courseLineService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseLineDto courseLineDto) {
        // 保存校验
        ValidatorUtil.require(courseLineDto.getLineId(), "学习路线id");
        ValidatorUtil.require(courseLineDto.getCourseId(), "课程id");

        ResponseDto responseDto = new ResponseDto();
        courseLineService.save(courseLineDto);
        responseDto.setContent(courseLineDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        courseLineService.delete(id);
        return responseDto;
    }
}
