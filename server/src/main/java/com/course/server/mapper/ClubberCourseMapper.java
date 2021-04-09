package com.course.server.mapper;

import com.course.server.domain.ClubberCourse;
import com.course.server.domain.ClubberCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClubberCourseMapper {
    long countByExample(ClubberCourseExample example);

    int deleteByExample(ClubberCourseExample example);

    int deleteByPrimaryKey(String id);

    int insert(ClubberCourse record);

    int insertSelective(ClubberCourse record);

    List<ClubberCourse> selectByExample(ClubberCourseExample example);

    ClubberCourse selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ClubberCourse record, @Param("example") ClubberCourseExample example);

    int updateByExample(@Param("record") ClubberCourse record, @Param("example") ClubberCourseExample example);

    int updateByPrimaryKeySelective(ClubberCourse record);

    int updateByPrimaryKey(ClubberCourse record);
}