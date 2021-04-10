package com.course.server.service;

import com.course.server.domain.CourseLine;
import com.course.server.domain.CourseLineExample;
import com.course.server.dto.CourseLineDto;
import com.course.server.dto.CourseLinePageDto;
import com.course.server.mapper.CourseLineMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseLineService {

    @Resource
    private CourseLineMapper courseLineMapper;

    /**
     * 列表查询
     */
    public void list(CourseLinePageDto courseLinePageDto) {
        PageHelper.startPage(courseLinePageDto.getPage(), courseLinePageDto.getSize());
        CourseLineExample courseLineExample = new CourseLineExample();
        CourseLineExample.Criteria criteria = courseLineExample.createCriteria();
        if (!StringUtils.isEmpty(courseLinePageDto.getLineId())) {
            criteria.andLineIdEqualTo(courseLinePageDto.getLineId());
        }
        List<CourseLine> courseLineList = courseLineMapper.selectByExample(courseLineExample);
        PageInfo<CourseLine> pageInfo = new PageInfo<>(courseLineList);
        courseLinePageDto.setTotal(pageInfo.getTotal());
        List<CourseLineDto> courseLineDtoList = CopyUtil.copyList(courseLineList, CourseLineDto.class);
        courseLinePageDto.setList(courseLineDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(CourseLineDto courseLineDto) {
        CourseLine courseLine = CopyUtil.copy(courseLineDto, CourseLine.class);
        if (StringUtils.isEmpty(courseLineDto.getId())) {
            this.insert(courseLine);
        } else {
            this.update(courseLine);
        }
    }

    /**
     * 新增
     */
    private void insert(CourseLine courseLine) {
        courseLine.setId(UuidUtil.getShortUuid());
        courseLineMapper.insert(courseLine);
    }

    /**
     * 更新
     */
    private void update(CourseLine courseLine) {
        courseLineMapper.updateByPrimaryKey(courseLine);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        courseLineMapper.deleteByPrimaryKey(id);
    }
}
