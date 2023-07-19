package com.blue.bluearchive.board.service;


import com.blue.bluearchive.board.dto.BoardDto;
import com.blue.bluearchive.board.dto.formDto.food.*;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.BoardImg;
import com.blue.bluearchive.board.entity.formEntity.food.BoardFood;
import com.blue.bluearchive.board.entity.formEntity.food.BoardFoodImg;
import com.blue.bluearchive.board.entity.formEntity.food.BoardFoodPart;
import com.blue.bluearchive.board.entity.formEntity.food.FoodInformation;
import com.blue.bluearchive.board.repository.formRepository.food.BoardFoodImgRepository;
import com.blue.bluearchive.board.repository.formRepository.food.BoardFoodPartRepository;
import com.blue.bluearchive.board.repository.formRepository.food.BoardFoodRepository;
import com.blue.bluearchive.board.repository.formRepository.food.FoodInformationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    @Value("${boardImgLocation}")
    private String ImgLocation;

    private final ModelMapper modelMapper;
    private final FoodInformationRepository foodInformationRepository;
    private final BoardFoodRepository boardFoodRepository;
    private final BoardFoodPartRepository boardFoodPartRepository;
    private final BoardFoodImgRepository boardFoodImgRepository;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public BoardFoodDto getFoodData(int boardId){
        BoardFood food = boardFoodRepository.findByBoard_BoardId(boardId);
        List<BoardFoodPart> foodPartList = boardFoodPartRepository.findByBoardFood(food);
        List<BoardFoodImg> foodImgList = boardFoodImgRepository.findByBoardFood(food);

        BoardFoodDto boardFoodDto = modelMapper.map(food,BoardFoodDto.class);
        List<FoodPartDto> foodPartDtoList = new ArrayList<>();
        for (BoardFoodPart foodPart : foodPartList){
            foodPartDtoList.add(modelMapper.map(foodPart, FoodPartDto.class));
        }
        List<FoodImgDto> foodImgDtoList = new ArrayList<>();
        for (BoardFoodImg foodImg : foodImgList){
            foodImgDtoList.add(modelMapper.map(foodImg, FoodImgDto.class));
        }
        boardFoodDto.setFoodPartDtoList(foodPartDtoList);
        boardFoodDto.setFoodImgDtoList(foodImgDtoList);
        return boardFoodDto;
    }


    @Transactional(readOnly = false)
    public BoardFood saveFood(FoodFormDto foodFormDto, List<FoodDataDto> foodDataDtoList, Board board){
        System.out.println("==================세이브 진입==================");
        BoardFood boardFood = null;
        System.out.println(foodFormDto);
        if(!(foodFormDto==null||foodFormDto.getTotalVolume() == 0.0f)){
            System.out.println("===================if문처리=================");
            boardFood = modelMapper.map(foodFormDto, BoardFood.class);
            boardFood.setBoard(board);
            boardFood = boardFoodRepository.save(boardFood);
        }
        List<BoardFoodPart> boardFoodPartList = new ArrayList<>();
        for (FoodDataDto data : foodDataDtoList){
            BoardFoodPart boardFoodPart = modelMapper.map(data,BoardFoodPart.class);
            boardFoodPart.setBoardFood(boardFood);
            boardFoodPartList.add(boardFoodPart);
        }
        boardFoodPartRepository.saveAll(boardFoodPartList);
        return boardFood;
    }

    public Float mathPercent(Float value, Float percent){
        Float val;
        if (value==null||value.isNaN()){
            val=null;
        }else {
            val=(Math.round(value*percent) * 100.0f) / 100.0f;
        }
        return val;
    }

    @Transactional(readOnly = true)
    public Page<FoodInformation> searchFood(String searchQuery, Pageable pageable){
        System.out.println("===================서비스 진입===================="+searchQuery);
        Page<FoodInformation> searchFood=foodInformationRepository.getSearchFood(searchQuery,pageable);
        Float percent;
        for (FoodInformation foodInformation : searchFood.getContent()){
            if (foodInformation.getFoodInformationSize()!=100.0f){
                percent = Math.round((100.0f/foodInformation.getFoodInformationSize()) * 100.0f) / 100.0f;
                foodInformation.setFoodInformationSize(100.0f);
                foodInformation.setFoodInformationCarb(mathPercent(foodInformation.getFoodInformationCarb(),percent));
                foodInformation.setFoodInformationProtein(mathPercent(foodInformation.getFoodInformationProtein(),percent));
                foodInformation.setFoodInformationFat(mathPercent(foodInformation.getFoodInformationFat(),percent));
                foodInformation.setFoodInformationKcal(mathPercent(foodInformation.getFoodInformationKcal(),percent));
            }
            System.out.println("데이터 가져옴==========="+foodInformation);
        }
        return searchFood;
    }

    @Transactional(readOnly = false)
    public void saveFoodImg(BoardFood boardFood, List<MultipartFile> boardImgFile) throws Exception{
        System.out.println("멀티파일 부분 돌아가는중");
        if (boardImgFile!=null) {
            for (MultipartFile file : boardImgFile) {
                System.out.println("멀티파일 부분 돌아가는중");
                BoardFoodImg foodImg = new BoardFoodImg();
                foodImg.setBoardFood(boardFood);

                String oriImgName = file.getOriginalFilename();
                String imgName = "";
                String imgUrl = "";

                if (!StringUtils.isEmpty(oriImgName)) {
                    imgName = fileService.uploadFile(ImgLocation, oriImgName, file.getBytes());
                    imgUrl = "/images/board/" + imgName;
                }
                //상품 이미지 정보 저장
                foodImg.setOriImgName(oriImgName);
                foodImg.setImgName(imgName);
                foodImg.setBoardFoodImgUrl(imgUrl);
                boardFoodImgRepository.save(foodImg);
            }
        }
    }

    @Transactional(readOnly = false)
    public BoardFood updateFoodImg(Board board, List<String> foodImgUrlList, FoodFormDto foodFormDto){
        BoardFood boardFood = boardFoodRepository.findByBoard(board);
        List<BoardFoodImg> boardImg = boardFoodImgRepository.findByBoardFood(boardFood);
        List<String>boardImgUrl = new ArrayList<>();
        for(BoardFoodImg imgUrl:boardImg){
            boardImgUrl.add(imgUrl.getBoardFoodImgUrl());
        }
        List<String> imagesToDelete = new ArrayList<>();
        for (String imgUrl : boardImgUrl) {
            if (!foodImgUrlList.contains(imgUrl)) {
                imagesToDelete.add(imgUrl);
                System.out.println(imgUrl);
                System.out.println("여깅");
            }
        }
        for (String imageUrl : imagesToDelete) {
            BoardFoodImg deletedImg = boardFoodImgRepository.findByBoardFoodImgUrl(imageUrl);
            if (deletedImg != null) {
                System.out.println(deletedImg.getImgName());
                // 필요한 작업 수행
                try {
                    fileService.deleteFile(imageUrl);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                boardFoodImgRepository.delete(deletedImg);
            }
        }

        boardFood.updateFood(foodFormDto);
        boardFood.setBoard(board);
        boardFoodRepository.save(boardFood);
        return boardFood;
    }
    @Transactional(readOnly = false)
    public void updateFoodPart(BoardFood boardFood, List<FoodDataDto> foodDataDtoList){
        boardFoodPartRepository.deleteByBoardFood(boardFood);
        List<BoardFoodPart> boardFoodPartList = new ArrayList<>();
        BoardFoodPart foodPart;
        for (FoodDataDto foodDto : foodDataDtoList){
            foodPart = modelMapper.map(foodDto,BoardFoodPart.class);
            foodPart.setBoardFood(boardFood);
            boardFoodPartList.add(foodPart);
        }
        boardFoodPartRepository.saveAll(boardFoodPartList);
    }



}
