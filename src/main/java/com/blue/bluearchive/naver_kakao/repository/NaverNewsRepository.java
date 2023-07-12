package com.blue.bluearchive.naver_kakao.repository;


import com.blue.bluearchive.naver_kakao.entity.NaverNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaverNewsRepository extends JpaRepository<NaverNews, Integer> {
}
