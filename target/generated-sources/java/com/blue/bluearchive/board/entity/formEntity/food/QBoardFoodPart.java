package com.blue.bluearchive.board.entity.formEntity.food;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardFoodPart is a Querydsl query type for BoardFoodPart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardFoodPart extends EntityPathBase<BoardFoodPart> {

    private static final long serialVersionUID = -1060096712L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardFoodPart boardFoodPart = new QBoardFoodPart("boardFoodPart");

    public final QBoardFood boardFood;

    public final NumberPath<Integer> boardFoodId = createNumber("boardFoodId", Integer.class);

    public QBoardFoodPart(String variable) {
        this(BoardFoodPart.class, forVariable(variable), INITS);
    }

    public QBoardFoodPart(Path<? extends BoardFoodPart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardFoodPart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardFoodPart(PathMetadata metadata, PathInits inits) {
        this(BoardFoodPart.class, metadata, inits);
    }

    public QBoardFoodPart(Class<? extends BoardFoodPart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardFood = inits.isInitialized("boardFood") ? new QBoardFood(forProperty("boardFood"), inits.get("boardFood")) : null;
    }

}

