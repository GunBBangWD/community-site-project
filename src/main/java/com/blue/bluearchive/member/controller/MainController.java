package com.blue.bluearchive.member.controller;

import com.blue.bluearchive.admin.dto.CategoryDto;
import com.blue.bluearchive.admin.service.CategoryService;
import com.blue.bluearchive.naver_kakao.dto.NaverSearchDto;
import com.blue.bluearchive.naver_kakao.repository.NaverBlogRepository;
import com.blue.bluearchive.naver_kakao.service.NaverSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final CategoryService categoryService;
    private final NaverSearchService naverSearchService;
    @GetMapping("/")
    public String main(Model model){
        return "main";
    }
}
