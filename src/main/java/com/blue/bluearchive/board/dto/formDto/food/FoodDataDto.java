package com.blue.bluearchive.board.dto.formDto.food;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class FoodDataDto {
    @NotEmpty(message = "* 음식 이름을 입력해주세요.")
    private String boardFoodName;

    @NotNull(message = "* 식사량(g)을 입력해주세요.")
    private Float boardFoodVolume;

    @NotNull(message = "* 칼로리(kcal)를 입력해주세요.")
    private Float boardFoodKcal;

    @NotNull(message = "* 탄수화물(g)을 입력해주세요.")
    private Float boardFoodCarb;

    @NotNull(message = "* 단백질(g)을 입력해주세요.")
    private Float boardFoodProtein;

    @NotNull(message = "* 지방(g) 입력해주세요.")
    private Float boardFoodFat;
}