package com.blue.bluearchive.board.entity.formEntity.food;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFoodInformation is a Querydsl query type for FoodInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFoodInformation extends EntityPathBase<FoodInformation> {

    private static final long serialVersionUID = 729825391L;

    public static final QFoodInformation foodInformation = new QFoodInformation("foodInformation");

    public final NumberPath<Float> foodInformationCarb = createNumber("foodInformationCarb", Float.class);

    public final NumberPath<Float> foodInformationFat = createNumber("foodInformationFat", Float.class);

    public final NumberPath<Integer> foodInformationId = createNumber("foodInformationId", Integer.class);

    public final NumberPath<Float> foodInformationKcal = createNumber("foodInformationKcal", Float.class);

    public final StringPath foodInformationName = createString("foodInformationName");

    public final NumberPath<Float> foodInformationProtein = createNumber("foodInformationProtein", Float.class);

    public final NumberPath<Float> foodInformationSize = createNumber("foodInformationSize", Float.class);

    public QFoodInformation(String variable) {
        super(FoodInformation.class, forVariable(variable));
    }

    public QFoodInformation(Path<? extends FoodInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFoodInformation(PathMetadata metadata) {
        super(FoodInformation.class, metadata);
    }

}

