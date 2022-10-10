package com.blog.app.blog.role.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Setter
@Getter
public class Role {
    @Id
    private Integer id;
    private String name;

}
