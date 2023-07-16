package com.blue.bluearchive.board.repository.formRepository.food;

import com.blue.bluearchive.board.entity.formEntity.food.BoardFood;
import com.blue.bluearchive.board.entity.formEntity.food.BoardFoodPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardFoodPartRepository extends JpaRepository<BoardFoodPart, Integer> {
    List<BoardFoodPart> findByBoardFood(BoardFood boardFood);
}
