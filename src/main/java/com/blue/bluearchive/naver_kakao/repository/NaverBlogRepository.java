package com.blue.bluearchive.naver_kakao.repository;


import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.naver_kakao.entity.NaverBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NaverBlogRepository extends JpaRepository<NaverBlog,Integer> {
    @Transactional
    long deleteByCategory(Category category);

    List<NaverBlog> findByCategory(Category category);
}
