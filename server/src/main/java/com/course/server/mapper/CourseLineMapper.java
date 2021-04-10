package com.course.server.mapper;

import com.course.server.domain.CourseLine;
import com.course.server.domain.CourseLineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseLineMapper {
    long countByExample(CourseLineExample example);

    int deleteByExample(CourseLineExample example);

    int deleteByPrimaryKey(String id);

    int insert(CourseLine record);

    int insertSelective(CourseLine record);

    List<CourseLine> selectByExample(CourseLineExample example);

    CourseLine selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CourseLine record, @Param("example") CourseLineExample example);

    int updateByExample(@Param("record") CourseLine record, @Param("example") CourseLineExample example);

    int updateByPrimaryKeySelective(CourseLine record);

    int updateByPrimaryKey(CourseLine record);
}