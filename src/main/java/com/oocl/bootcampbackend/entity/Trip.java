package com.oocl.bootcampbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    // 3. 核心：关联User表的外键配置
    @ManyToOne(fetch = FetchType.LAZY) // fetch=LAZY：延迟加载（避免查询行程时自动查用户，优化性能）
    @JoinColumn(
            name = "user_id", // 行程表的外键字段名（对应你说的userId，数据库字段建议用下划线命名：user_id）
            referencedColumnName = "id", // 关联User表的主键字段名（User表的id）
            nullable = false // 外键非空（确保每个行程都属于一个用户，可选，根据业务调整）
    )
    private User user; // 关联的User对象（而非直接用userId！JPA通过这个对象维护关联关系，userId会自动映射为数据库字段）

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
