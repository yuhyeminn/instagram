package com.media.instagram.domain;

import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "post")
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "userId")
//    @JoinColumn(name = "userId", nullable = false)
    private User writer;

    // TODO : IMAGE -> https://pinokio0702.tistory.com/142
//    @Lob
//    private byte[] image;
    private String tempImage;

    @Column(name = "post_content")  // nullable?
    private String content;

    @Column(name = "post_like", nullable = false)
    @ColumnDefault("0")
    private long like;      // 좋아요

    @ManyToOne
    @JoinColumn(name = "location_name")
    private Location location;

    // TODO : TAGGING


    public Post(User writer, String tempImage) {
        this.writer = writer;
        this.tempImage = tempImage;
    }

    public Post(User writer, String tempImage, String content, Location location) {
        this.writer = writer;
        this.tempImage = tempImage;
        this.content = content;
        this.location = location;
    }
}
