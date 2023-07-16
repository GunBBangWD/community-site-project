package com.blue.bluearchive.board.dto.formDto.food;

import lombok.Data;

import javax.persistence.Column;

@Data
public class FoodPartDto {
    private String boardFoodName;
    private Float boardFoodVolume;
    private Float boardFoodKcal;
    private Float boardFoodCarb;
    private Float boardFoodProtein;
    private Float boardFoodFat;
}
