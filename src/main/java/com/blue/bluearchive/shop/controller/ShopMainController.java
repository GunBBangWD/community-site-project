package com.blue.bluearchive.shop.controller;

import com.blue.bluearchive.constant.ItemCategory;
import com.blue.bluearchive.shop.dto.ItemSearchDto;
import com.blue.bluearchive.shop.dto.MainItemDto;
import com.blue.bluearchive.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopMainController {

    private final ItemService itemService;
    @GetMapping(value = {"/main/{category}"})
   	public String shopMain(HttpServletRequest request, @PathVariable("category") ItemCategory category, ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,5);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable, category);
        System.out.println(category);

        model.addAttribute("items",items);
        model.addAttribute("category",category);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);
        return "shop/shopMain";
    }



}