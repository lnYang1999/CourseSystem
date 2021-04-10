package com.course.server.dto;

/**
 * @project: course
 * @description:
 * @author: ysp
 * @create: 2021/04/10
 */
public class CourseLinePageDto extends PageDto{
    private String lineId;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CourseLinePageDto{");
        sb.append("lineId='").append(lineId).append('\'');
        sb.append(", page=").append(page);
        sb.append(", size=").append(size);
        sb.append(", total=").append(total);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
