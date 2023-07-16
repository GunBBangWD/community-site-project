package com.blue.bluearchive.board.dto.formDto.food;


import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class BoardFoodDto {
    private Float totalVolume;
    private Float totalKcal;
    private Float totalCarb;
    private Float totalProtein;
    private Float totalFat;
    private List<FoodPartDto> foodPartDtoList;
    private List<FoodImgDto> foodImgDtoList;
}
