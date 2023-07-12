package com.blue.bluearchive.board.entity.formEntity.food;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardFoodImg is a Querydsl query type for BoardFoodImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardFoodImg extends EntityPathBase<BoardFoodImg> {

    private static final long serialVersionUID = -172750370L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardFoodImg boardFoodImg = new QBoardFoodImg("boardFoodImg");

    public final QBoardFood boardFood;

    public final NumberPath<Integer> boardFoodImgId = createNumber("boardFoodImgId", Integer.class);

    public final StringPath boardFoodImgUrl = createString("boardFoodImgUrl");

    public QBoardFoodImg(String variable) {
        this(BoardFoodImg.class, forVariable(variable), INITS);
    }

    public QBoardFoodImg(Path<? extends BoardFoodImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardFoodImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardFoodImg(PathMetadata metadata, PathInits inits) {
        this(BoardFoodImg.class, metadata, inits);
    }

    public QBoardFoodImg(Class<? extends BoardFoodImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardFood = inits.isInitialized("boardFood") ? new QBoardFood(forProperty("boardFood"), inits.get("boardFood")) : null;
    }

}

