package com.blue.bluearchive.admin.controller;


import com.blue.bluearchive.admin.dto.CategoryDto;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.admin.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminCategoryController {
    private final CategoryService categoryService;


    @GetMapping("/commMgt")
    public String commMgt(Model model) {
        List<CategoryDto> categoryStatisticsList = categoryService.getTotal();
        model.addAttribute("categoryStatisticsList", categoryStatisticsList);
        return "adminPage/manageCommunity";
    }

    @GetMapping("/newcategory")
    public String newCate() {
        return "adminPage/newCategory";
    }

    @GetMapping("/update/{id}")
    public String categoryUpdate(@PathVariable int id, Model model) {
        Category categoryDto = categoryService.getCategoryById(id);
        model.addAttribute("categoryDto", categoryDto);
        return "adminPage/categoryUpdate";
    }
    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/createCate")
    public String newCate(@ModelAttribute CategoryDto categoryDto) throws Exception {
        try {
            categoryService.save(categoryDto);
            return "redirect:/admin/commMgt";

        } catch (IllegalArgumentException e) {
            return "redirect:/admin/newcategory?duplicateError&categoryName=" + URLEncoder.encode(categoryDto.getCategoryName(), "UTF-8");
        }
    }

    @PostMapping("/updateCategory")
    public String categoryUpdate(@ModelAttribute CategoryDto categoryDto) throws Exception {
        try {
            categoryService.update(categoryDto);
            return "redirect:/admin/commMgt";
        } catch (IllegalArgumentException e) {
            return "redirect:/admin/update/" + categoryDto.getCategoryId()
                    + "?duplicateError&categoryName=" + URLEncoder.encode(categoryDto.getCategoryName(), "UTF-8");
        }
    }



}
