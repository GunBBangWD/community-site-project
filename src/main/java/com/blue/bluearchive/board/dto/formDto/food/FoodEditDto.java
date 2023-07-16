package com.blue.bluearchive.board.dto.formDto.food;

import lombok.Data;

import java.util.List;

@Data
public class FoodEditDto {
    private Float totalVolume;
    private Float totalKcal;
    private Float totalCarb;
    private Float totalProtein;
    private Float totalFat;
    private List<FoodPartDto> foodPartDtoList;
}
