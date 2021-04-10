package com.course.business.controller.admin;

import com.course.server.dto.LineDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.LineService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/line")
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

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody LineDto lineDto) {
        // 保存校验
        ValidatorUtil.require(lineDto.getTitle(), "标题");
        ValidatorUtil.length(lineDto.getTitle(), "标题", 1, 100);
        ValidatorUtil.require(lineDto.getDesc(), "详细描述");
        ValidatorUtil.length(lineDto.getDesc(), "详细描述", 1, 200);
        ValidatorUtil.length(lineDto.getImage(), "头像", 1, 100);

        ResponseDto responseDto = new ResponseDto();
        lineService.save(lineDto);
        responseDto.setContent(lineDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        lineService.delete(id);
        return responseDto;
    }
}
