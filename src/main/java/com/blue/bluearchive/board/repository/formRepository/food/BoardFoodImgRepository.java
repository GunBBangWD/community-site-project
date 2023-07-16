package com.blue.bluearchive.board.repository.formRepository.food;

import com.blue.bluearchive.board.entity.formEntity.food.BoardFood;
import com.blue.bluearchive.board.entity.formEntity.food.BoardFoodImg;
import com.blue.bluearchive.board.entity.formEntity.food.BoardFoodPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardFoodImgRepository extends JpaRepository<BoardFoodImg, Integer> {
    List<BoardFoodImg> findByBoardFood(BoardFood boardFood);
}
