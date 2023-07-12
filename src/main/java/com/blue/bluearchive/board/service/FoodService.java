package com.blue.bluearchive.board.service;


import com.blue.bluearchive.board.entity.formEntity.food.FoodInformation;
import com.blue.bluearchive.board.repository.formRepository.food.FoodInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodInformationRepository foodInformationRepository;

    public Float mathPercent(Float value, Float percent){
        Float val;
        if (value==null||value.isNaN()){
            val=null;
        }else {
            val=(Math.round(value*percent) * 100.0f) / 100.0f;
        }
        return val;
    }



    public Page<FoodInformation> searchFood(String searchQuery, Pageable pageable){
        System.out.println("===================서비스 진입===================="+searchQuery);
        Page<FoodInformation> searchFood=foodInformationRepository.getSearchFood(searchQuery,pageable);
        Float percent;
        for (FoodInformation foodInformation : searchFood.getContent()){
            if (foodInformation.getFoodInformationSize()!=100.0f){
                percent = Math.round((100.0f/foodInformation.getFoodInformationSize()) * 100.0f) / 100.0f;
                foodInformation.setFoodInformationSize(100.0f);
                foodInformation.setFoodInformationCarb(mathPercent(foodInformation.getFoodInformationCarb(),percent));
                foodInformation.setFoodInformationProtein(mathPercent(foodInformation.getFoodInformationProtein(),percent));
                foodInformation.setFoodInformationFat(mathPercent(foodInformation.getFoodInformationFat(),percent));
                foodInformation.setFoodInformationKcal(mathPercent(foodInformation.getFoodInformationKcal(),percent));
            }
            System.out.println("데이터 가져옴==========="+foodInformation);
        }
        return searchFood;
    }
}
