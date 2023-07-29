package com.fastcampus.sns.model.entity;


import com.fastcampus.sns.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "post")
@SQLDelete(sql = "UPDATE \"post\" SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
@NoArgsConstructor
public class UserEntity {
    @Id
    private Integer id;
    @Column(name = "user_name")
    private String userName;

    @Column(name= "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER; //default지정

    @Column(name= "registered_at")
    private Timestamp registeredAt;

    @Column(name= "updated_at")
    private Timestamp updatedAt;

    @Column(name= "deleted_at")
    private Timestamp deletedAt;

    //저장되는 순간을 기록함.
    @PrePersist
    void registredAt(){
        this.registeredAt = Timestamp.from(Instant.now());
    }
    @PreUpdate
    void updatedAt(){
        this.updatedAt = Timestamp.from(Instant.now());
    }

    //DTO를 위한 USER class
    //DB에 저장하기 위한 USER ENTITY
    //둘은 혼용하지 않는다. 서비스에서는 DTO만 사용
    public static UserEntity of(String username, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(username);
        userEntity.setPassword(password);

        return userEntity;
    }
}
