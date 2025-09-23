package com.oocl.bootcampbackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    // 3. 核心：关联多个行程（一对多）
    @OneToMany(
            mappedBy = "user", // 指向行程端（Trip）的关联属性名“user”，表示由Trip维护外键
            fetch = FetchType.LAZY, // 延迟加载：查询用户时不自动加载所有行程，优化性能
            cascade = CascadeType.ALL, // 级联操作（可选，根据业务配置）：如保存用户时自动保存关联的行程
            orphanRemoval = true // 级联删除（可选）：删除用户时自动删除关联的行程（需谨慎使用）
    )
    private List<Trip> trips; // 1个用户有多个行程，用List存储

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public void setTrip(Trip trip){
        this.trips.add(trip);
    }

}
