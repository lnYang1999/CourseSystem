package com.course.server.dto;


public class CourseLineDto {

    /**
     * id
     */
    private String id;

    /**
     * 学习路线id
     */
    private String lineId;

    /**
     * 课程id
     */
    private String courseId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", lineId=").append(lineId);
        sb.append(", courseId=").append(courseId);
        sb.append("]");
        return sb.toString();
    }
}