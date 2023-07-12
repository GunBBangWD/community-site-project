package com.blue.bluearchive.admin.controller;


import com.blue.bluearchive.admin.dto.ReportDto;
import com.blue.bluearchive.admin.dto.ReportPageDto;
import com.blue.bluearchive.admin.service.*;
import com.blue.bluearchive.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminReportController {
    private final CategoryService categoryService;
    private final BoardService boardService;
    private final AdminReportService adminReportService;
    private final AdminBoardService adminBoardService;
    private final AdminCommentService adminCommentService;
    private final AdminCommentsCommentService adminCommentsCommentService;
    private final  AdminCommunityCareService adminCommunityCareService;
    private final AdminUserCareSerivce userCareSerivce;


    //신고 처리 버튼
    @PostMapping("/confirmReports")
    public ResponseEntity<?> confirmReports(@RequestBody List<Integer> reportIds) {
        try {
            int handledReportCount = adminReportService.confirmReports(reportIds);
            return ResponseEntity.ok(handledReportCount);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //    게시글 신고 클릭시 팝업
    @GetMapping("/boardReport/{boardId}")
    public String boardReportResult(@PathVariable int boardId, @RequestParam(defaultValue = "1") int page, Model model){
        int pageSize = 5; // 한 페이지에 보여줄 게시글 수
        ReportPageDto reportPageDto = adminReportService.getReportsForBoard(boardId, page, pageSize);
        List<ReportDto> reportDtos = adminReportService.getReportsForBoard(boardId);
        model.addAttribute("reportPageDto", reportPageDto);
        model.addAttribute("reportDtos", reportDtos);
        model.addAttribute("boardId", boardId);

        return "adminPage/boardReportResult";
    }

    //댓글 신고 클릭시 팝업
    @GetMapping("/commentReport/{commentId}")
    public String commentReportResult(@PathVariable int commentId, @RequestParam(defaultValue = "1") int page, Model model){
        int pageSize = 5; // 한 페이지에 보여줄 게시글 수
        ReportPageDto reportPageDto = adminReportService.getReportsForComment(commentId, page, pageSize);
        List<ReportDto> reportDtos = adminReportService.getReportsForComment(commentId);
        model.addAttribute("commentId", commentId);
        model.addAttribute("reportDtos", reportDtos);
        model.addAttribute("reportPageDto", reportPageDto);

        return "adminPage/commentReportResult";
    }

    //대댓글 신고 클릭시 팝업
    @GetMapping("/commentsComment/{commentsCommentId}")
    public String commentsCommentReportResult(@PathVariable int commentsCommentId, @RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 5; // 한 페이지에 보여줄 게시글 수
        ReportPageDto reportPageDto = adminReportService.getReportsForCommentsComment(commentsCommentId, page, pageSize);
        List<ReportDto> reportDtos = adminReportService.getReportsForCommentsComment(commentsCommentId);
        model.addAttribute("commentsCommentId", commentsCommentId);
        model.addAttribute("reportPageDto", reportPageDto);
        model.addAttribute("reportDtos", reportDtos);

        return "adminPage/commentsCommentReportResult";
    }
}
