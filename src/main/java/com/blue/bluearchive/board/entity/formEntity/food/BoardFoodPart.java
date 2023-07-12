package com.blue.bluearchive.board.entity.formEntity.food;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "board_food_part")
public class BoardFoodPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_food_part_id")
    private int boardFoodId;

    @Column(name = "board_food_name")
    private String boardFoodName;

    @Column(name = "board_food_volume")
    private Float boardFoodVolume;

    @Column(name = "board_food_kcal")
    private Float boardFoodKcal;

    @Column(name = "board_food_carb")
    private Float boardFoodCarb;

    @Column(name = "board_food_protein")
    private Float boardFoodProtein;

    @Column(name = "board_food_fat")
    private Float boardFoodFat;

    @ManyToOne
    @JoinColumn(name = "board_food_id")
    private BoardFood boardFood;
}
