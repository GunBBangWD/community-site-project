
package com.blue.bluearchive.board.entity.formEntity.food;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "board_food_img")
public class BoardFoodImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_food_img_id")
    private int boardFoodImgId;

    @Column(name = "board_food_img_url", length = 200, nullable = false)
    private String boardFoodImgUrl;

    @ManyToOne
    @JoinColumn(name = "board_food_id")
    private BoardFood boardFood;
}
