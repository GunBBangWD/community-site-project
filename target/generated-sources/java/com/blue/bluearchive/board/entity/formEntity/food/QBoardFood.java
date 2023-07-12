package com.blue.bluearchive.board.entity.formEntity.food;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardFood is a Querydsl query type for BoardFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardFood extends EntityPathBase<BoardFood> {

    private static final long serialVersionUID = 1221113765L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardFood boardFood = new QBoardFood("boardFood");

    public final com.blue.bluearchive.board.entity.QBoard board;

    public final NumberPath<Integer> boardFoodId = createNumber("boardFoodId", Integer.class);

    public final ListPath<BoardFoodImg, QBoardFoodImg> boardfoodImgList = this.<BoardFoodImg, QBoardFoodImg>createList("boardfoodImgList", BoardFoodImg.class, QBoardFoodImg.class, PathInits.DIRECT2);

    public final ListPath<BoardFoodPart, QBoardFoodPart> boardFoodPartList = this.<BoardFoodPart, QBoardFoodPart>createList("boardFoodPartList", BoardFoodPart.class, QBoardFoodPart.class, PathInits.DIRECT2);

    public QBoardFood(String variable) {
        this(BoardFood.class, forVariable(variable), INITS);
    }

    public QBoardFood(Path<? extends BoardFood> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardFood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardFood(PathMetadata metadata, PathInits inits) {
        this(BoardFood.class, metadata, inits);
    }

    public QBoardFood(Class<? extends BoardFood> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.blue.bluearchive.board.entity.QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

