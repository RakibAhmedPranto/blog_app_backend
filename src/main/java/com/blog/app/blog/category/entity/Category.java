package com.blog.app.blog.category.entity;

import com.blog.app.blog.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories",uniqueConstraints = @UniqueConstraint(columnNames = {"category_title"}))
@NoArgsConstructor
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_title", nullable = false,length = 100)
    private String title;

    @Column(name = "category_description", nullable = false, length = 300)
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
}
