package com.blue.bluearchive.naver_kakao.entity;



import com.blue.bluearchive.admin.entity.Category;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "naver_blog")
public class NaverBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "naver_blog_id")
    private int naverBlogId;

    @Column(name = "naver_blog_title", length = 500)
    private String naverBlogTitle;

    @Column(name = "naver_blog_link", length = 2000)
    private String naverBlogLink;

    @Column(name = "naver_blog_Img", length = 2000)
    private String naverBlogImg;

    @Column(name = "naver_blog_description", length = 2000)
    private String naverBlogDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


}
