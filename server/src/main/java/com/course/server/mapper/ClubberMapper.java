package com.course.server.mapper;

import com.course.server.domain.Clubber;
import com.course.server.domain.ClubberExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClubberMapper {
    long countByExample(ClubberExample example);

    int deleteByExample(ClubberExample example);

    int deleteByPrimaryKey(String id);

    int insert(Clubber record);

    int insertSelective(Clubber record);

    List<Clubber> selectByExample(ClubberExample example);

    Clubber selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Clubber record, @Param("example") ClubberExample example);

    int updateByExample(@Param("record") Clubber record, @Param("example") ClubberExample example);

    int updateByPrimaryKeySelective(Clubber record);

    int updateByPrimaryKey(Clubber record);
}