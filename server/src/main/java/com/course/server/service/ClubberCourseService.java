package com.course.server.service;

import com.course.server.domain.ClubberCourse;
import com.course.server.domain.ClubberCourseExample;
import com.course.server.dto.ClubberCourseDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.ClubberCourseMapper;
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
public class ClubberCourseService {

    @Resource
    private ClubberCourseMapper clubberCourseMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ClubberCourseExample clubberCourseExample = new ClubberCourseExample();
        List<ClubberCourse> clubberCourseList = clubberCourseMapper.selectByExample(clubberCourseExample);
        PageInfo<ClubberCourse> pageInfo = new PageInfo<>(clubberCourseList);
        pageDto.setTotal(pageInfo.getTotal());
        List<ClubberCourseDto> clubberCourseDtoList = CopyUtil.copyList(clubberCourseList, ClubberCourseDto.class);
        pageDto.setList(clubberCourseDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(ClubberCourseDto clubberCourseDto) {
        ClubberCourse clubberCourse = CopyUtil.copy(clubberCourseDto, ClubberCourse.class);
        if (StringUtils.isEmpty(clubberCourseDto.getId())) {
            this.insert(clubberCourse);
        } else {
            this.update(clubberCourse);
        }
    }

    /**
     * 新增
     */
    private void insert(ClubberCourse clubberCourse) {
                Date now = new Date();
        clubberCourse.setId(UuidUtil.getShortUuid());
        clubberCourseMapper.insert(clubberCourse);
    }

    /**
     * 更新
     */
    private void update(ClubberCourse clubberCourse) {
        clubberCourseMapper.updateByPrimaryKey(clubberCourse);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        clubberCourseMapper.deleteByPrimaryKey(id);
    }
}
