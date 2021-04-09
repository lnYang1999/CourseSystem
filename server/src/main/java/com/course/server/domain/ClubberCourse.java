package com.course.server.domain;

import java.util.Date;

public class ClubberCourse {
    private String id;

    private String clubberId;

    private String courseId;

    private Date at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClubberId() {
        return clubberId;
    }

    public void setClubberId(String clubberId) {
        this.clubberId = clubberId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Date getAt() {
        return at;
    }

    public void setAt(Date at) {
        this.at = at;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", clubberId=").append(clubberId);
        sb.append(", courseId=").append(courseId);
        sb.append(", at=").append(at);
        sb.append("]");
        return sb.toString();
    }
}