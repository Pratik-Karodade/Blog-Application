package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String body;

    /*
        FechType.Lazy tells hybernate to only fech
        the related entities from database when you use the relationship
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
