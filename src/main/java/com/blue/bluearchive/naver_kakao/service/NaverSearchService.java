package com.blue.bluearchive.naver_kakao.service;


import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.admin.repository.CategoryRepository;
import com.blue.bluearchive.naver_kakao.dto.NaverSearchDto;
import com.blue.bluearchive.naver_kakao.entity.NaverBlog;
import com.blue.bluearchive.naver_kakao.repository.NaverBlogRepository;
import com.blue.bluearchive.naver_kakao.repository.NaverNewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NaverSearchService {
    private final NaverBlogRepository naverBlogRepository;
    private final NaverNewsRepository naverNewsRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;


    public void naverSearchSave(List<NaverBlog> naverBlogList, Category category){
        naverBlogRepository.deleteByCategory(category);
        naverBlogRepository.saveAll(naverBlogList);
    }

    public List<NaverSearchDto> getAllBlog(){
        List<NaverSearchDto> naverSearchDtos = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAll();
        for (Category category : categoryList) {
            List<NaverBlog> naverBlogs = naverBlogRepository.findByCategory(category);

            NaverSearchDto naverSearchDto = new NaverSearchDto();
            naverSearchDto.setNaverBlogList(naverBlogs);
            naverSearchDto.setCategoryId(category.getCategoryId());
            naverSearchDto.setCategoryName(category.getCategoryName());
            naverSearchDtos.add(naverSearchDto);
        }
        return naverSearchDtos;
    }
}
