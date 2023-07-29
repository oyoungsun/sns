package com.fastcampus.sns.model.entity;

import com.fastcampus.sns.model.UserRole;
import com.fastcampus.sns.repository.PostEntityRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "\"post\"")
@SQLDelete(sql = "UPDATE \"post\" SET removed_at = NOW() WHERE id=?")
@Where(clause = "removed_at is NULL")
@NoArgsConstructor
public class PostEntity {
    @Id
    private Integer id;
    @Column(name = "title")
    private String title;

    @Column(name= "body", columnDefinition = "TEXT")
    private String body;

    @ManyToOne //하나의 유저에 여러개의 포스트
    @JoinColumn(name = "user_id")
    private UserEntity user; //유저를 물고있다

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

    public static PostEntity of(String title, String body, UserEntity userEntity){
        PostEntity entity = new PostEntity();
        entity.setTitle(title);
        entity.setBody(body);
        entity.setUser(userEntity);
        return entity;
    }
}
