package com.blue.bluearchive.board.repository.formRepository.food;

import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.formEntity.food.BoardFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFoodRepository extends JpaRepository<BoardFood,Integer> {

    BoardFood findByBoard_BoardId(int boardId);

    BoardFood findByBoard(Board board);
}
