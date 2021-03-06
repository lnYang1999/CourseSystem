package com.course.server.dto;


public class LineDto {

    /**
     * id
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 详细描述
     */
    private String desc;

    /**
     * 头像
     */
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", desc=").append(desc);
        sb.append(", image=").append(image);
        sb.append("]");
        return sb.toString();
    }
}