package com.blue.bluearchive.admin.controller;


import com.blue.bluearchive.admin.dto.*;
import com.blue.bluearchive.admin.service.*;
import com.blue.bluearchive.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminBoardController {
    private final CategoryService categoryService;
    private final BoardService boardService;
    private final AdminBoardService adminBoardService;


    @GetMapping("/boardMgt/{page}")
    public String getBoardMgt(@RequestParam int id, Model model, AdminSearchDto adminSearchDto,
                              @PathVariable("page") Optional<Integer> page) {

        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryDtoList);
        //페이지 설정
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        //리스트 데이터들 페이지 객체화
        Page<AdminBoardDto> boardDtoList =  adminBoardService.getBoardByCreatedBy(id, adminSearchDto, pageable);
        model.addAttribute("boardDtoList", boardDtoList);
        model.addAttribute("searchDto", adminSearchDto);
        model.addAttribute("id", id);
        return "/adminPage/manageCommunity_board";  // Return as JSON
    }

    @GetMapping("/adminUpdate2/{updateCategory}/{boardId}")
    public String changeBoardCategoryName(@PathVariable int updateCategory, @PathVariable int boardId) {
        adminBoardService.updateCategoryNameById(boardId, updateCategory);

        return "redirect:/admin/boardMgt/0?id=0"; // 원하는 리다이렉션 경로로 변경해주세요.
    }
    @GetMapping("/adminUpdate/{boardId}")
    public String boardCategoryUpdate(@PathVariable int boardId,Model model){
        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        String categoryName = boardService.getBoardById(boardId).getCategory().getCategoryName();
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("boardId", boardId);
        model.addAttribute("categoryList", categoryDtoList);
        return "adminPage/adminUpdateCategory";
    }

    @PostMapping("/deleteBoard")
    public ResponseEntity<?> deleteBoard(@RequestBody List<Integer> boardIds) {
        try {
            adminBoardService.deleteBoards(boardIds);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
