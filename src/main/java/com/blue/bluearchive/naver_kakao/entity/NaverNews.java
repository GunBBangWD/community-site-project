package com.blue.bluearchive.naver_kakao.entity;


import com.blue.bluearchive.admin.entity.Category;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "naver_news")
public class NaverNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "naver_news_id")
    private int naverNewsId;

    @Column(name = "naver_news_title", length = 500)
    private String naverNewsTitle;

    @Column(name = "naver_news_link", length = 2000)
    private String baverNewsLink;

    @Column(name = "naver_news_Img", length = 2000)
    private String naverNewsImg;

    @Column(name = "naver_news_description", length = 2000)
    private String naverNewsDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
