package com.oocl.bootcampbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String duration;
    private String time;
    private String location;
    private String images;
    private String experts;
    private String start;
    private String day;
    private Boolean isDone;


    // 3. 核心：关联User表的外键配置
    @ManyToOne(fetch = FetchType.LAZY) // fetch=LAZY：延迟加载（避免查询行程时自动查用户，优化性能）
    @JoinColumn(
            name = "user_id", // 行程表的外键字段名（对应你说的userId，数据库字段建议用下划线命名：user_id）
            referencedColumnName = "id", // 关联User表的主键字段名（User表的id）
            nullable = false // 外键非空（确保每个行程都属于一个用户，可选，根据业务调整）
    )
    private User user; // 关联的User对象（而非直接用userId！JPA通过这个对象维护关联关系，userId会自动映射为数据库字段）

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getExperts() {
        return experts;
    }

    public void setExperts(String experts) {
        this.experts = experts;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
