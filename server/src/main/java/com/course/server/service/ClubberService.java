package com.course.server.service;

import com.course.server.domain.Clubber;
import com.course.server.domain.ClubberExample;
import com.course.server.dto.ClubberDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ClubberMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service
public class ClubberService {

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
}
