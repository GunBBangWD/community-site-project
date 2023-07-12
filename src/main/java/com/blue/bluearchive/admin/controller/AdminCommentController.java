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
public class AdminCommentController {
    private final CategoryService categoryService;
    private final AdminCommentService adminCommentService;
    private final AdminCommentsCommentService adminCommentsCommentService;

    @GetMapping("/commentMgt/{page}")
    public String getBoardCommentMgt(@RequestParam int id, Model model, AdminSearchDto adminSearchDto,
                                     @PathVariable("page") Optional<Integer> page) {

        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryDtoList);
        //페이지 설정
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        //리스트 데이터들 페이지 객체화
        Page<AdminCommentDto> commentDtos =  adminCommentService.getCommentByCreatedBy(id, adminSearchDto, pageable);
        model.addAttribute("commentDtos", commentDtos);
        model.addAttribute("searchDto", adminSearchDto);
        model.addAttribute("id", id);
        return "adminPage/manageCommunity_comment";  // Return as JSON
    }

    //댓글 삭제
    @PostMapping("/deleteComment")
    public ResponseEntity<?> deleteComments(@RequestBody List<Integer> commentsIds) {
        try {
            System.out.println("아이디아이디"+commentsIds);
            adminCommentService.deletes(commentsIds);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/commentsCommentMgt/{page}")
    public String getCommentsCommentMgt(@RequestParam int id,AdminSearchDto searchDto,Model model
            ,  @PathVariable("page")Optional<Integer> page) {
        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryDtoList);
        System.out.println("id 아이디 아이디 아이디 "+ id);
        //페이지 설정
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        //리스트 데이터들 페이지 객체화
        Page<AdminCommentsCommentDto> commentsCommentDtos =  adminCommentsCommentService.getCommentByCreatedBy(id, searchDto, pageable);
        model.addAttribute("commentsCommentDtos", commentsCommentDtos);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("id", id);
        return "adminPage/manageCommunity_commentsComment";  // Return as JSON
    }


    //대댓글 삭제 아작스
    @PostMapping("/deleteCommentsComment")
    public ResponseEntity<?> deleteCommentsComment(@RequestBody List<Integer> commentsCommentIds) {
        try {
            adminCommentsCommentService.deletes(commentsCommentIds);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
