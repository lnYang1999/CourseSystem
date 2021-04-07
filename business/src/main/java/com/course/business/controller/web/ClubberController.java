package com.course.business.controller.web;

import com.course.server.dto.ClubberDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ClubberService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("webClubberController")
@RequestMapping("/web/clubber")
public class ClubberController {

    private static final Logger LOG = LoggerFactory.getLogger(ClubberController.class);
    public static final String BUSINESS_NAME = "会员";

    @Resource
    private ClubberService clubberService;

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/register")
    public ResponseDto register(@RequestBody ClubberDto clubberDto) {
        // 保存校验
        ValidatorUtil.require(clubberDto.getMobile(), "手机号");
        ValidatorUtil.length(clubberDto.getMobile(), "手机号", 11, 11);
        ValidatorUtil.require(clubberDto.getPassword(), "密码");
        ValidatorUtil.length(clubberDto.getName(), "昵称", 1, 50);
        ValidatorUtil.length(clubberDto.getPhoto(), "头像url", 1, 200);

        // 密码加密
        clubberDto.setPassword(DigestUtils.md5DigestAsHex(clubberDto.getPassword().getBytes()));

        ResponseDto responseDto = new ResponseDto();
        clubberService.save(clubberDto);
        responseDto.setContent(clubberDto);
        return responseDto;
    }
}
