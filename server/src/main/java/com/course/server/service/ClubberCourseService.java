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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
        clubberCourse.setAt(now);
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

    /**
     * 报名，先判断是否已报名
     * @param clubberCourseDto
     */
    public ClubberCourseDto enroll(ClubberCourseDto clubberCourseDto) {
        ClubberCourse clubberCourseDb = this.select(clubberCourseDto.getClubberId(), clubberCourseDto.getCourseId());
        if (clubberCourseDb == null) {
            ClubberCourse clubberCourse = CopyUtil.copy(clubberCourseDto, ClubberCourse.class);
            this.insert(clubberCourse);
            // 将数据库信息全部返回，包括id, at
            return CopyUtil.copy(clubberCourse, ClubberCourseDto.class);
        } else {
            // 如果已经报名，则直接返回已报名的信息
            return CopyUtil.copy(clubberCourseDb, ClubberCourseDto.class);
        }
    }

    /**
     * 根据clubberId和courseId查询记录
     */
    public ClubberCourse select(String clubberId, String courseId) {
        ClubberCourseExample example = new ClubberCourseExample();
        example.createCriteria()
                .andCourseIdEqualTo(courseId)
                .andClubberIdEqualTo(clubberId);
        List<ClubberCourse> clubberCourseList = clubberCourseMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(clubberCourseList)) {
            return null;
        } else {
            return clubberCourseList.get(0);
        }
    }

    /**
     * 获取报名信息
     */
    public ClubberCourseDto getEnroll(ClubberCourseDto clubberCourseDto) {
        ClubberCourse clubberCourse = this.select(clubberCourseDto.getClubberId(), clubberCourseDto.getCourseId());
        return CopyUtil.copy(clubberCourse, ClubberCourseDto.class);
    }
}
