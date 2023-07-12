package com.blue.bluearchive.board.entity.formEntity.food;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "food_information")
public class FoodInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_information_id")
    private int foodInformationId;

    @Column(name = "food_information_name")
    private String foodInformationName;

    @Column(name = "food_information_size")
    private Float foodInformationSize;

    @Column(name = "food_information_kcal")
    private Float foodInformationKcal;

    @Column(name = "food_information_carb")
    private Float foodInformationCarb;

    @Column(name = "food_information_protein")
    private Float foodInformationProtein;

    @Column(name = "food_information_fat")
    private Float foodInformationFat;

}
