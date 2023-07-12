package com.blue.bluearchive.admin.controller;

import com.blue.bluearchive.admin.dto.*;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.admin.service.*;
import com.blue.bluearchive.board.service.BoardService;
import com.blue.bluearchive.board.service.CommentService;
import com.blue.bluearchive.board.service.CommentsCommentService;
import com.blue.bluearchive.constant.MemberStat;
import com.blue.bluearchive.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    private final AdminReportService adminReportService;
    private final  AdminCommunityCareService adminCommunityCareService;
    private final AdminUserCareSerivce userCareSerivce;


    @GetMapping("/main")
    public String adminMain(Model model) {
        List<CategoryDailyData> categoryDailyDataList = adminCommunityCareService.getCategoryDailyData();
        model.addAttribute("categoryDailyDataList", categoryDailyDataList);
        return "adminPage/manageMain";
    }

@GetMapping("/userMgt/{page}")
public String userMgt(@RequestParam String userState, Model model,AdminSearchDto adminSearchDto,  @PathVariable("page") Optional<Integer> page) {
    Pageable pageable = PageRequest.of(page.get(),5);
    Page<AdminUserCareDto> userCareDtos = userCareSerivce.getMemberBoardPageMain(userState,adminSearchDto,pageable);
    System.out.println("검색했을떄===="+userCareDtos);
    model.addAttribute("maxPage",10);
    model.addAttribute("userCareDtos", userCareDtos);
    model.addAttribute("searchDto", adminSearchDto);
    model.addAttribute("memberStatEnum", MemberStat.values());
    model.addAttribute("id", userState);
    return "adminPage/manageUser";
}

    @GetMapping("/shopMgt")
    public String shopMgt() {
        return "adminPage/manageShop";
    }

    @GetMapping("/sellUserMgt")
    public String sellUserMgt() {
        return "/adminPage/manageUserSell";
    }


    @GetMapping("/userReportList/{id}")
    public String userUntreatedReportList(@PathVariable Long id, Model model, @RequestParam(defaultValue = "1") int page) {
        System.out.println("id====" + id);
        int pageSize = 5; // 한 페이지에 보여줄 게시글 수
        ReportPageDto reportPageDto = adminReportService.getNoReportResult(id, page, pageSize);
        List<ReportDto> reportDtos = adminReportService.okReportResult(id);
        model.addAttribute("userId", id);
        model.addAttribute("reportPageDto", reportPageDto);
        model.addAttribute("reportDtos", reportDtos);
        return "adminPage/untreatedReportList";
    }
//    @GetMapping("/boardMgt/{page}")
//    public String getBoardMgt(@RequestParam int id, Model model, AdminSearchDto adminSearchDto,
//                              @PathVariable("page") Optional<Integer> page) {
//
//        List<CategoryDto> categoryDtoList = categoryService.getAllCategory();
//        model.addAttribute("categoryList", categoryDtoList);
//        //페이지 설정
//        Pageable pageable = PageRequest.of(page.get(),5);
//        model.addAttribute("maxPage",5);
//        //리스트 데이터들 페이지 객체화
//        Page<AdminBoardDto> boardDtoList =  adminBoardService.getBoardByCreatedBy(id, adminSearchDto, pageable);
//        model.addAttribute("boardDtoList", boardDtoList);
//        model.addAttribute("searchDto", adminSearchDto);
//        model.addAttribute("id", id);
//        return "/adminPage/manageCommunity_board";  // Return as JSON
//    }
    @GetMapping("/userRecord/{page}")
    public String userActivityBoardRecord(@RequestParam Long selectUser, Model model, @PathVariable("page") Optional<Integer> page){
        String name = userCareSerivce.findUser(selectUser);
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        Page<AdminMemberBoard> memberBoards = userCareSerivce.getMemberBoardPageMain(selectUser,pageable);
        model.addAttribute("memberBoard",memberBoards);
        model.addAttribute("name", name);
        model.addAttribute("selectUser", selectUser);
        return "adminPage/userActivityBoardRecord";
    }
    @GetMapping("/userCommentRecord/{page}")
    public String userActivityCommentRecord(@RequestParam Long selectUser, Model model, @PathVariable("page") Optional<Integer> page){
        String name = userCareSerivce.findUser(selectUser);
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        Page<AdminMemberCommentDto> membercomments = userCareSerivce.getMemberCommentPageMain(selectUser,pageable);
        model.addAttribute("comments",membercomments);
        model.addAttribute("name", name);
        model.addAttribute("selectUser", selectUser);
        return "adminPage/userActivityCommentRecord";
    }
    @GetMapping("/userCommentsCommentRecord/{page}")
    public String userActivityCommentsCommentRecord(@RequestParam Long selectUser, Model model, @PathVariable("page") Optional<Integer> page){
        String name = userCareSerivce.findUser(selectUser);
        Pageable pageable = PageRequest.of(page.get(),5);
        model.addAttribute("maxPage",5);
        Page<AdminMemberCommentsCommentDto> memberCommentsComment = userCareSerivce.getMemberCommentsCommentPageMain(selectUser,pageable);
        model.addAttribute("memberCommentsComment",memberCommentsComment);
        model.addAttribute("name", name);
        model.addAttribute("selectUser", selectUser);
        return "adminPage/userActivityCommentsCommentRecord";
    }

    @PostMapping("/userStatChange/{statName}")
    public ResponseEntity<?> changeUserState(@RequestBody List<Long> userIdxs,@PathVariable String statName) {
        try {
            userCareSerivce.changeUser(userIdxs,statName);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}