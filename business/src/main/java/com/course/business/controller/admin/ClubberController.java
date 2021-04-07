package com.course.business.controller.admin;

import com.course.server.dto.ClubberDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ClubberService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/clubber")
public class ClubberController {

    private static final Logger LOG = LoggerFactory.getLogger(ClubberController.class);
    public static final String BUSINESS_NAME = "会员";

    @Resource
    private ClubberService clubberService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        clubberService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ClubberDto clubberDto) {
        // 保存校验
        ValidatorUtil.length(clubberDto.getMobile(), "手机号", 1, 11);
        ValidatorUtil.require(clubberDto.getPassword(), "密码");
        ValidatorUtil.length(clubberDto.getName(), "昵称", 1, 50);
        ValidatorUtil.length(clubberDto.getPhoto(), "头像url", 1, 200);

        ResponseDto responseDto = new ResponseDto();
        clubberService.save(clubberDto);
        responseDto.setContent(clubberDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        clubberService.delete(id);
        return responseDto;
    }
}
