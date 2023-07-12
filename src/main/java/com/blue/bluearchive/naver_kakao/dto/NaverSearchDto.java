package com.blue.bluearchive.naver_kakao.dto;

import com.blue.bluearchive.naver_kakao.entity.NaverBlog;
import lombok.Data;

import java.util.List;

@Data
public class NaverSearchDto {
    private List<NaverBlog> naverBlogList;
    private int categoryId;
    private String categoryName;
}
