package com.course.business.controller.web;

import com.alibaba.fastjson.JSON;
import com.course.server.dto.ClubberDto;
import com.course.server.dto.LoginClubberDto;
import com.course.server.dto.ResponseDto;
import com.course.server.dto.SmsDto;
import com.course.server.enums.SmsUseEnum;
import com.course.server.exception.BusinessException;
import com.course.server.service.ClubberService;
import com.course.server.service.SmsService;
import com.course.server.util.UuidUtil;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController("webClubberController")
@RequestMapping("/web/clubber")
public class ClubberController {

    private static final Logger LOG = LoggerFactory.getLogger(ClubberController.class);
    public static final String BUSINESS_NAME = "会员";

    @Resource
    private ClubberService clubberService;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    @Resource
    private SmsService smsService;

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

        // 校验短信验证码
        SmsDto smsDto = new SmsDto();
        smsDto.setMobile(clubberDto.getMobile());
        smsDto.setCode(clubberDto.getSmsCode());
        smsDto.setUse(SmsUseEnum.REGISTER.getCode());
        smsService.validCode(smsDto);
        LOG.info("短信验证码校验通过");

        ResponseDto responseDto = new ResponseDto();
        clubberService.save(clubberDto);
        responseDto.setContent(clubberDto);
        return responseDto;
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody ClubberDto clubberDto) {
        LOG.info("用户登录开始");
        clubberDto.setPassword(DigestUtils.md5DigestAsHex(clubberDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();

        // 根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
        String imageCode = (String) redisTemplate.opsForValue().get(clubberDto.getImageCodeToken());
        LOG.info("从redis中获取到的验证码：{}", imageCode);
        if (StringUtils.isEmpty(imageCode)) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            LOG.info("用户登录失败，验证码已过期");
            return responseDto;
        }
        if (!imageCode.toLowerCase().equals(clubberDto.getImageCode().toLowerCase())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码不对");
            LOG.info("用户登录失败，验证码不对");
            return responseDto;
        } else {
            // 验证通过后，移除验证码
            redisTemplate.delete(clubberDto.getImageCodeToken());
        }

        LoginClubberDto loginClubberDto = clubberService.login(clubberDto);
        String token = UuidUtil.getShortUuid();
        loginClubberDto.setToken(token);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginClubberDto), 3600, TimeUnit.SECONDS);
        responseDto.setContent(loginClubberDto);
        return responseDto;
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout/{token}")
    public ResponseDto logout(@PathVariable String token) {
        ResponseDto responseDto = new ResponseDto();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token:{}", token);
        return responseDto;
    }

    /**
     * 校验手机号是否存在
     * 存在则success=true，不存在则success=false
     */
    @GetMapping(value = "/is-mobile-exist/{mobile}")
    public ResponseDto isMobileExist(@PathVariable(value = "mobile") String mobile) throws BusinessException {
        LOG.info("查询手机号是否存在开始");
        ResponseDto responseDto = new ResponseDto();
        ClubberDto clubberDto = clubberService.findByMobile(mobile);
        if (clubberDto == null) {
            responseDto.setSuccess(false);
        } else {
            responseDto.setSuccess(true);
        }
        return responseDto;
    }

    @PostMapping("/reset-password")
    public ResponseDto resetPassword(@RequestBody ClubberDto clubberDto) throws BusinessException {
        LOG.info("会员密码重置开始:");
        clubberDto.setPassword(DigestUtils.md5DigestAsHex(clubberDto.getPassword().getBytes()));
        ResponseDto<ClubberDto> responseDto = new ResponseDto();

        // 校验短信验证码
        SmsDto smsDto = new SmsDto();
        smsDto.setMobile(clubberDto.getMobile());
        smsDto.setCode(clubberDto.getSmsCode());
        smsDto.setUse(SmsUseEnum.FORGET.getCode());
        smsService.validCode(smsDto);
        LOG.info("短信验证码校验通过");

        // 重置密码
        clubberService.resetPassword(clubberDto);

        return responseDto;
    }
}
