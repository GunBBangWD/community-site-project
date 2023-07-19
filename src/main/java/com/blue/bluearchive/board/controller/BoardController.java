package com.blue.bluearchive.board.controller;

import com.blue.bluearchive.admin.service.CategoryService;
import com.blue.bluearchive.admin.dto.CategoryDto;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.dto.*;
import com.blue.bluearchive.board.dto.formDto.ImgDto;
import com.blue.bluearchive.board.dto.formDto.food.*;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.formEntity.food.BoardFood;
import com.blue.bluearchive.board.repository.BoardRepository;
import com.blue.bluearchive.board.service.*;
import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.FileInfo;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.*;


@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final CommentsCommentService commentsCommentService;
    private final BoardRepository boardRepository;
    private final BoardLikeHateService boardLikeHateService;
    private final FoodService foodService;
    private final ModelMapper modelMapper;
//    private String[] boardImgFiles; // boardImgFiles 필드


    private void processCommentCounts(Page<Board> boardList) {
        for (Board board : boardList) {
            int boardId = board.getBoardId();

            List<CommentDto> commentDtoList = commentService.getCommentByBoardId(boardId);
            Map<Integer, List<CommentsCommentDto>> commentsCommentMap = new HashMap<>();

            int totalCommentCount = 0;

            for (CommentDto commentDto : commentDtoList) {
                int commentId = commentDto.getCommentId();
                List<CommentsCommentDto> commentsCommentDtoList = commentsCommentService.getCommentsCommentByCommentId(commentId);
                commentsCommentMap.put(commentId, commentsCommentDtoList);

                totalCommentCount += commentsCommentDtoList.size();
            }

            int commentCount = commentDtoList.size() + totalCommentCount;
            board.setCommentCount(commentCount);
            boardService.updateCommentCount(board);
        }
    }
    @GetMapping(value = {"/board/all","/board/all/{page}"})
    public String getBoardList(BoardSearchDto boardSearchDto,
                               @PathVariable("page") Optional<Integer> page,Model model) {
//        List<BoardDto> boardList = boardService.getAllBoards();
//        model.addAttribute("boardList", boardList);
        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);

        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,5);
        Page<Board> boards = boardService.getBoardPage(boardSearchDto,pageable);
        processCommentCounts(boards);

        model.addAttribute("boards",boards);
        model.addAttribute("boardSearchDto",boardSearchDto);
        model.addAttribute("maxPage",5);


        return "board/list";
    }


    // POST 방식으로 "/board/{categoryId}" 경로에 접근할 때의 처리
    @GetMapping(value = {"/board/{categoryId}","/board/{categoryId}/{page}"})
    public String getBoardsByCategory(@PathVariable int categoryId, BoardSearchDto boardSearchDto,
                                      @PathVariable("page") Optional<Integer> page,Model model) {
        Category category = categoryService.getCategoryById(categoryId); // categoryId에 해당하는 카테고리를 가져옵니다.
//        List<BoardDto> boardList = boardService.getBoardsByCategory(category); // 해당 카테고리에 속한 게시물들을 가져옵니다.
//        model.addAttribute("boardList", boardList); // boardList를 모델에 추가합니다.
        //processCommentCounts(boardList);
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,5);
        Page<Board> boards = boardService.getCategoryBoardPage(category,boardSearchDto,pageable);
        processCommentCounts(boards);

        model.addAttribute("boards",boards);
        model.addAttribute("boardSearchDto",boardSearchDto);
        model.addAttribute("maxPage",5);


        List<CategoryDto> categoryList = categoryService.getAllCategory(); // 모든 카테고리를 가져옵니다.
        model.addAttribute("categoryList", categoryList); // categoryList를 모델에 추가합니다.


        return "board/list"; // board/list 뷰를 반환합니다.
    }
    @GetMapping("/board/Detail/{boardId}")
    public String getBoardDetails(@PathVariable int boardId, Model model) {
        boardService.incrementBoardCount(boardId);
        BoardDto board = boardService.getBoardById(boardId);
        model.addAttribute("board", board);
        BoardFormDto boardFormDto= boardService.getBoardImgById(boardId);
        model.addAttribute("boardFormDto",boardFormDto);

        BoardFoodDto boardFoodDto = foodService.getFoodData(boardId);
        System.out.println("==================푸드 데이터 컨트롤러 진입 후==============");
        System.out.println(boardFoodDto);
        model.addAttribute("boardFoodDto",boardFoodDto);

        List<CommentDto> commentList = commentService.getCommentByBoardId(boardId);
        model.addAttribute("commentList", commentList);
        Map<Integer, List<CommentsCommentDto>> commentsCommentMap = new HashMap<>();
        for (CommentDto comment : commentList) {
            int commentId = comment.getCommentId();
            List<CommentsCommentDto> commentsCommentList = commentsCommentService.getCommentsCommentByCommentId(commentId);
            commentsCommentMap.put(commentId, commentsCommentList);
        }
        model.addAttribute("commentsCommentMap", commentsCommentMap);

        return "board/boardDetail_HAN";
    }


    @GetMapping(value = "board/Edit/{boardId}")
    public String boardEditForm(@PathVariable("boardId")int boardId, Model model){
        try{
            List<CategoryDto> categoryList = categoryService.getAllCategory();
            model.addAttribute("categoryList", categoryList);
            BoardFormDto boardFormDto= boardService.getBoardImgById(boardId);
            //카테고리 선택 추가
            int selectCategoryId= boardFormDto.getCategory().getCategoryId();
            model.addAttribute("selectCategoryId",selectCategoryId);
            model.addAttribute("boardFormDto",boardFormDto);

            //건희 추가 시작
            List<ImgDto> foodImgList = new ArrayList<>();
            BoardFoodDto boardFoodDto = foodService.getFoodData(boardId);
            FoodEditDto foodEditDto = modelMapper.map(boardFoodDto,FoodEditDto.class);
            System.out.println("==================수정중==============");
            System.out.println(foodEditDto);
            model.addAttribute("boardFoodDto",foodEditDto);
            for (FoodImgDto foodImg : boardFoodDto.getFoodImgDtoList()){
                ImgDto foodImgDto = new ImgDto();
                foodImgDto.setImgUrl(foodImg.getBoardFoodImgUrl());
                foodImgDto.setImgId("foodImgFile");
                foodImgDto.setImgListType("foodImgFile");
                foodImgList.add(foodImgDto);
            }
            model.addAttribute("foodImageUrls", foodImgList); // 이미지 URL 목록을 모델에 추가하여 View로 전달합니다.
            //건희 추가 끝

            //기존코드 수정
            List<BoardImgDto> imageList = boardFormDto.getBoardImgDtoList(); // 이미지 목록을 가져온다고 가정합니다.
            List<ImgDto> imgDtoList = new ArrayList<>();
            for (BoardImgDto image : imageList) {
                ImgDto imgDto = new ImgDto();
                imgDto.setImgUrl(image.getBoardImgUrl());
                imgDto.setImgId("View_area");
                imgDto.setImgListType("boardImgFile");
                imgDtoList.add(imgDto); // 이미지 URL 정보를 가져온다고 가정합니다.
                System.out.println(imgDto);
            }
            model.addAttribute("imageUrls", imgDtoList); // 이미지 URL 목록을 모델에 추가하여 View로 전달합니다.

        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage","오류");
            return  "redirect:/board/Detail/"+boardId;
        }
        return "/board/boardWrite";
    }
    @PostMapping(value = "/board/Edit/{boardId}")
    public String boardUpdate(BoardFormDto boardFormDto,BindingResult bindingResult
            ,@RequestParam(value = "foodFormDtoJson",required = false)String  foodFormDtoJson  // 건희 추가
            ,@RequestParam(value = "foodDataDtoListJson",required = false)String foodDataDtoListJson // 건희 추가
            ,@RequestParam(value = "foodImgFile",required = false)List<MultipartFile>foodImgFileList // 건희 추가
            ,@RequestParam(value = "foodImgFileUrl",required = false)List<String>foodImgUrlList //건희 추가
            ,@RequestParam(value = "boardImgFile",required = false)List<MultipartFile>boardImgFileList
            ,@RequestParam(value = "boardImgFileUrl",required = false)List<String>boardImgUrlList,Model model){
        if(bindingResult.hasErrors()){
            return "board/boardWrite";
        }
        //건희 추가 시작
        ObjectMapper objectMapper = new ObjectMapper();
        List<FoodDataDto> foodDataDtoList = null;
        FoodFormDto foodFormDto = null;
        try {
            foodDataDtoList = objectMapper.readValue(foodDataDtoListJson, new TypeReference<List<FoodDataDto>>(){});
            foodFormDto = objectMapper.readValue(foodFormDtoJson, new TypeReference<FoodFormDto>(){});
            System.out.println("멀티파이 출력 =========="+foodImgFileList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("수정 포스트 진입 ===========");
        System.out.println("리스트 내용 ==========="+foodDataDtoList);
        System.out.println("토탈 내용 ==========="+foodFormDto);
        System.out.println("이미지 리스트 내용 ==========="+foodImgUrlList);
        //건희 추가 끝
        try{
            Board board = boardService.updateBoard(boardFormDto,boardImgUrlList,boardImgFileList);
            BoardFood boardFood = foodService.updateFoodImg(board,foodImgUrlList, foodFormDto);//건희 추가
            System.out.println("이미지수정 직후");
            foodService.updateFoodPart(boardFood,foodDataDtoList); //건희 추가
            System.out.println("리스트 수정 직후");
            foodService.saveFoodImg(boardFood, foodImgFileList);  // 건희 추가
            System.out.println("이미지저장 직후");
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage","게시글 수정 중 에러 발생");
            System.out.println("예외 발생 ~~");
            return "board/boardWrite";
        }
       return "redirect:/board/Detail/"+boardFormDto.getBoardId();
    }


    @PostMapping("/boardlikeHate")
    @ResponseBody
    public BoardLikeHateDto handleBoardLikeHateRequest(@RequestBody BoardLikeHateDto boardLikeHateDto) {
        if (boardLikeHateDto.isHate()) {
            boardLikeHateService.hateBoard(boardLikeHateDto.getBoardId(), boardLikeHateDto.getIdx());
        } else if (boardLikeHateDto.isLike()) {
            boardLikeHateService.likeBoard(boardLikeHateDto.getBoardId(), boardLikeHateDto.getIdx());
        }
        return new BoardLikeHateDto(
                boardService.getBoardLikeCount(boardLikeHateDto.getBoardId()),
                boardService.getBoardHateCount(boardLikeHateDto.getBoardId())
        );
    }


    @GetMapping(value = "/board/Delete/{boardId}")
    public String deleteBoard(@PathVariable int boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        boardRepository.delete(board);

        return "redirect:/board/"+board.getCategory().getCategoryId();
    }


}
