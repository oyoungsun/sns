package com.fastcampus.sns.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Getter
@Setter
@Table
public class UserEntity {
    @Id
    private Integer id;
    @Column(name = "user_name")
    private String userName;

    @Column(name= "password")
    private String password;
}
