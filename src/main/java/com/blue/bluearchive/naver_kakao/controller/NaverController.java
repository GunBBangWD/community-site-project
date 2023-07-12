package com.blue.bluearchive.naver_kakao.controller;

import com.blue.bluearchive.admin.dto.CategoryDto;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.admin.service.CategoryService;
import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.naver_kakao.dto.NaverSearchDto;
import com.blue.bluearchive.naver_kakao.entity.NaverBlog;
import com.blue.bluearchive.naver_kakao.service.NaverSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class NaverController {
    private final NaverSearchService naverSearchService;
    private final CategoryService categoryService;

    @GetMapping("/health/recommendedPage")
    public String recommended_page(Model model){
        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);

        List<NaverSearchDto> naverSearchDtos = naverSearchService.getAllBlog();
        model.addAttribute("naverSearchDtos", naverSearchDtos);


        return "healthContent/healthPage";
    }


    @GetMapping("/naver/blog/crawling")
    public String testCrawling(@RequestParam("keyword") String keyword,
                               @RequestParam("categoryId") Category categoryId) {
        try {
            String url = "https://search.naver.com/search.naver?where=view&query="+keyword+"&sm=tab_opt&nso=so%3Ar%2Cp%3A1w%2Ca%3Aall&mode=normal&main_q=&st_coll=&topic_r_cat=";
            Document doc = Jsoup.connect(url).get();
            Elements blogEntries = doc.select("div.total_wrap.api_ani_send");
            List<NaverBlog> naverBlogList = new ArrayList<>();
            for (int i=0;i<(blogEntries.size()<5?blogEntries.size():5);i++){
                String title = blogEntries.get(i).select("a.api_txt_lines.total_tit._cross_trigger").text();
                String link = blogEntries.get(i).select("a.api_txt_lines.total_tit._cross_trigger").attr("href");
                String imgLink = blogEntries.get(i).select("img.thumb.api_get").attr("src");
                String description = blogEntries.get(i).select("div.api_txt_lines.dsc_txt").text();
                NaverBlog naverBlog = new NaverBlog();
                naverBlog.setNaverBlogTitle(title);
                naverBlog.setNaverBlogLink(link);
                naverBlog.setCategory(categoryId);
                if (!imgLink.isEmpty()) {
                    naverBlog.setNaverBlogImg(imgLink);
                }
                naverBlog.setNaverBlogDescription(description);
                naverBlogList.add(naverBlog);
            }
            naverSearchService.naverSearchSave(naverBlogList,categoryId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/health/recommendedPage";
    }





}