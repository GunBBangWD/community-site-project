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

    @Column(name = "board_total_volume")
    private Float totalVolume;
    @Column(name = "board_total_kcal")
    private Float totalKcal;
    @Column(name = "board_total_carb")
    private Float totalCarb;
    @Column(name = "board_total_protein")
    private Float totalProtein;
    @Column(name = "board_total_fat")
    private Float totalFat;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "boardFood", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardFoodImg> boardfoodImgList;
    @OneToMany(mappedBy = "boardFood", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardFoodPart> boardFoodPartList;


}
