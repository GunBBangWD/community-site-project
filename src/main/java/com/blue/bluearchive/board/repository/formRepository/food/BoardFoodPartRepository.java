package com.blue.bluearchive.board.repository.formRepository.food;

import com.blue.bluearchive.board.entity.formEntity.food.BoardFood;
import com.blue.bluearchive.board.entity.formEntity.food.BoardFoodPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardFoodPartRepository extends JpaRepository<BoardFoodPart, Integer> {
    List<BoardFoodPart> findByBoardFood(BoardFood boardFood);

    @Transactional
    @Modifying
    @Query("delete from BoardFoodPart b where b.boardFood = ?1")
    void deleteByBoardFood(BoardFood boardFood);
}
