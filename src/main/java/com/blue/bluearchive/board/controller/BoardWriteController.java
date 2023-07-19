package com.blue.bluearchive.board.controller;


import com.blue.bluearchive.admin.dto.CategoryDto;
import com.blue.bluearchive.admin.service.CategoryService;
import com.blue.bluearchive.board.dto.BoardFormDto;
import com.blue.bluearchive.board.dto.formDto.food.FoodDataDto;
import com.blue.bluearchive.board.dto.formDto.food.FoodFormDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.formEntity.food.BoardFood;
import com.blue.bluearchive.board.entity.formEntity.food.FoodInformation;
import com.blue.bluearchive.board.repository.BoardRepository;
import com.blue.bluearchive.board.service.*;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardWriteController {
    private final BoardService boardService;
    private final CategoryService categoryService;
    private final FoodService foodService;
    private FoodDataDto foodDataDto;

    @GetMapping(value = "/board/Write/new")
    public String getBoardWrite(Model model){
        List<CategoryDto> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("boardFormDto",new BoardFormDto());
        return "board/boardWrite";
    }

    @PostMapping(value = "/board/Write/new")
    public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult, Model model
            , @RequestParam(value = "foodFormDtoJson",required = false)String  foodFormDtoJson  // 건희 추가
            , @RequestParam(value = "foodDataDtoListJson",required = false)String foodDataDtoListJson // 건희 추가
            , @RequestParam(value = "foodImgFile",required = false)List<MultipartFile>foodImgFileList //건희 추가
            , @RequestParam(value = "boardImgFile",required = false)List<MultipartFile>boardImgFileList){
        //건희 추가 시작
        ObjectMapper objectMapper = new ObjectMapper();
        List<FoodDataDto> foodDataDtoList = null;
        FoodFormDto foodFormDto = null;
        try {
            foodDataDtoList = objectMapper.readValue(foodDataDtoListJson, new TypeReference<List<FoodDataDto>>(){});
            foodFormDto = objectMapper.readValue(foodFormDtoJson, new TypeReference<FoodFormDto>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //건희 추가 끝
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage","게시글 등록 중 오류");
            return "/board/boardWrite";
        }
        try {
            Board board = boardService.saveBoard(boardFormDto, boardImgFileList);
            BoardFood boardFood = foodService.saveFood(foodFormDto,foodDataDtoList,board); //건희 추가
            foodService.saveFoodImg(boardFood, foodImgFileList);  // 건희 추가
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "게시글 등록 중 오류");
            System.out.println("저기 오류");
        }

        return "redirect:/board/all/"+boardFormDto.getCategory().getCategoryId();
    }




    //건희 추가 시작
    @GetMapping(value = {"/board/food/search","/board/food/search/{page}"})
    public String searchFood(@RequestParam(value = "searchQuery",required = false)String searchQuery,
            @PathVariable("page") Optional<Integer> page,Model model){

        Page<FoodInformation> foodInformationList;
        //페이지 설정
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,5);
        model.addAttribute("maxPage",5);
        
        if (searchQuery==null||searchQuery.trim().isEmpty()){
            searchQuery=null;
            foodInformationList = Page.empty(pageable);
            System.out.println("비었을때");
        }else {
            System.out.println("안비었을때");
            foodInformationList = foodService.searchFood(searchQuery,pageable);
        }

        model.addAttribute("searchQuery",searchQuery);
        model.addAttribute("foodInformationList", foodInformationList);
        return "boardCategory/foodSearch";
    }


    // POST 요청을 처리합니다.
    @PostMapping("/board/food/data")
    public ResponseEntity<String> addIngredient(@RequestBody @Valid FoodDataDto foodDataDto, BindingResult bindingResult, Model model) {
        System.out.println("컨트롤러 진입");
        System.out.println(foodDataDto);
        if(bindingResult.hasErrors()) {
            log.info("Valid 예외처리 진입");
            String errorMessage = "";
            for (ObjectError error : bindingResult.getAllErrors()) {
                String defaultMessage = error.getDefaultMessage();
                errorMessage += defaultMessage + "\n";
            }
            return ResponseEntity.badRequest().body(errorMessage);
        }

        // 클라이언트로부터 받은 데이터를 저장합니다.
        foodDataDto.setBoardFoodCarb((float) (Math.round(foodDataDto.getBoardFoodCarb()*100.0)/100.0));
        foodDataDto.setBoardFoodKcal((float) (Math.round(foodDataDto.getBoardFoodKcal()*100.0)/100.0));
        foodDataDto.setBoardFoodProtein((float) (Math.round(foodDataDto.getBoardFoodProtein()*100.0)/100.0));
        foodDataDto.setBoardFoodFat((float) (Math.round(foodDataDto.getBoardFoodFat()*100.0)/100.0));
        foodDataDto.setBoardFoodVolume((float) (Math.round(foodDataDto.getBoardFoodVolume()*100.0)/100.0));
        this.foodDataDto = foodDataDto;
//        System.out.println(foodDataDto);

        // 응답을 클라이언트에 반환합니다.
        return ResponseEntity.ok("Data saved successfully");
    }
//
    // GET 요청을 처리합니다.
    @GetMapping("board/food/data")
    public ResponseEntity<FoodDataDto> getIngredient() {
        // 저장된 데이터를 클라이언트에 반환합니다.
        FoodDataDto returnData=null;
        if(foodDataDto!=null){
            System.out.println("!!!!!!!!!!!!!!데이터 확인!!!!!!!!"+foodDataDto);
            returnData=foodDataDto;
            foodDataDto=null;
            return ResponseEntity.ok(returnData);
        }else {
            return ResponseEntity.badRequest().body(null);
        }

    }

    //건희 추가 끝





}
