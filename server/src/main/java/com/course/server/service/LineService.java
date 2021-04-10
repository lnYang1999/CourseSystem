package com.course.server.service;

import com.course.server.domain.Line;
import com.course.server.domain.LineExample;
import com.course.server.dto.LineDto;
import com.course.server.dto.PageDto;
import com.course.server.mapper.LineMapper;
import com.course.server.util.CopyUtil;
import com.course.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LineService {

    @Resource
    private LineMapper lineMapper;

    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        LineExample lineExample = new LineExample();
        List<Line> lineList = lineMapper.selectByExample(lineExample);
        PageInfo<Line> pageInfo = new PageInfo<>(lineList);
        pageDto.setTotal(pageInfo.getTotal());
        List<LineDto> lineDtoList = CopyUtil.copyList(lineList, LineDto.class);
        pageDto.setList(lineDtoList);
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    public void save(LineDto lineDto) {
        Line line = CopyUtil.copy(lineDto, Line.class);
        if (StringUtils.isEmpty(lineDto.getId())) {
            this.insert(line);
        } else {
            this.update(line);
        }
    }

    /**
     * 新增
     */
    private void insert(Line line) {
        line.setId(UuidUtil.getShortUuid());
        lineMapper.insert(line);
    }

    /**
     * 更新
     */
    private void update(Line line) {
        lineMapper.updateByPrimaryKey(line);
    }

    /**
     * 删除
     */
    public void delete(String id) {
        lineMapper.deleteByPrimaryKey(id);
    }
}
