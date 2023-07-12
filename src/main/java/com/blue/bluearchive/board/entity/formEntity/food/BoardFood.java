package com.blue.bluearchive.board.entity.formEntity.food;

import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.BoardImg;
import com.blue.bluearchive.report.entity.Report;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "board_food")
public class BoardFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_food_id")
    private int boardFoodId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "boardFood", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardFoodImg> boardfoodImgList;
    @OneToMany(mappedBy = "boardFood", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardFoodPart> boardFoodPartList;


}
