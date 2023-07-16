package com.blue.bluearchive.board.dto.formDto.food;

import lombok.Data;

import javax.persistence.Column;

@Data
public class FoodFormDto {
    private Float totalVolume;
    private Float totalKcal;
    private Float totalCarb;
    private Float totalProtein;
    private Float totalFat;
}
