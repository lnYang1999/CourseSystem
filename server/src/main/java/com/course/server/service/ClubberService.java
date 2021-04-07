package com.course.server.service;

import com.course.server.domain.Clubber;
import com.course.server.domain.ClubberExample;
import com.course.server.dto.ClubberDto;
import com.course.server.dto.LoginClubberDto;
import com.course.server.dto.PageDto;
import com.course.server.exception.BusinessException;
import com.course.server.exception.BusinessExceptionCode;
import com.course.server.mapper.ClubberMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ClubberService {

    private static final Logger LOG = LoggerFactory.getLogger(ClubberService.class);

    @Resource
    private ClubberMapper clubberMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ClubberExample clubberExample = new ClubberExample();
        List<Clubber> clubberList = clubberMapper.selectByExample(clubberExample);
        PageInfo<Clubber> pageInfo = new PageInfo<>(clubberList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ClubberDto> clubberDtoList = CopyUtil.copyList(clubberList, ClubberDto.class);
        pageDto.setList(clubberDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(ClubberDto clubberDto) {
        Clubber clubber = CopyUtil.copy(clubberDto, Clubber.class);
        if (StringUtils.isEmpty(clubberDto.getId())) {
            this.insert(clubber);
        } else {
            this.update(clubber);
        }
    }

    /**
     * 新增
     */
    private void insert(Clubber clubber) {
                Date now = new Date();
        clubber.setId(UuidUtil.getShortUuid());
        clubber.setRegisterTime(now);
        clubberMapper.insert(clubber);
    }

    /**
     * 更新
     */
    private void update(Clubber clubber) {
        clubberMapper.updateByPrimaryKey(clubber);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        clubberMapper.deleteByPrimaryKey(id);
    }

    /**
     * 按手机号查找
     * @param mobile
     * @return
     */
    public Clubber selectByMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return null;
        }
        ClubberExample example = new ClubberExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<Clubber> clubberList = clubberMapper.selectByExample(example);
        if (clubberList == null || clubberList.size() == 0) {
            return null;
        } else {
            return clubberList.get(0);
        }

    }

    /**
     * 登录
     * @param clubberDto
     */
    public LoginClubberDto login(ClubberDto clubberDto) {
        Clubber clubber = selectByMobile(clubberDto.getMobile());
        if (clubber == null) {
            LOG.info("手机号不存在, {}", clubberDto.getMobile());
            throw new BusinessException(BusinessExceptionCode.LOGIN_CLUBBER_ERROR);
        } else {
            if (clubber.getPassword().equals(clubberDto.getPassword())) {
                // 登录成功
                LoginClubberDto loginClubberDto = CopyUtil.copy(clubber, LoginClubberDto.class);
                return loginClubberDto;
            } else {
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", clubberDto.getPassword(), clubber.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_CLUBBER_ERROR);
            }
        }
    }
}
