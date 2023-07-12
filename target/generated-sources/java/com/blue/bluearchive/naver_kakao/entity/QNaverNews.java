package com.blue.bluearchive.naver_kakao.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNaverNews is a Querydsl query type for NaverNews
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNaverNews extends EntityPathBase<NaverNews> {

    private static final long serialVersionUID = 1558171615L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNaverNews naverNews = new QNaverNews("naverNews");

    public final StringPath baverNewsLink = createString("baverNewsLink");

    public final com.blue.bluearchive.admin.entity.QCategory category;

    public final StringPath naverNewsDescription = createString("naverNewsDescription");

    public final NumberPath<Integer> naverNewsId = createNumber("naverNewsId", Integer.class);

    public final StringPath naverNewsImg = createString("naverNewsImg");

    public final StringPath naverNewsTitle = createString("naverNewsTitle");

    public QNaverNews(String variable) {
        this(NaverNews.class, forVariable(variable), INITS);
    }

    public QNaverNews(Path<? extends NaverNews> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNaverNews(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNaverNews(PathMetadata metadata, PathInits inits) {
        this(NaverNews.class, metadata, inits);
    }

    public QNaverNews(Class<? extends NaverNews> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.blue.bluearchive.admin.entity.QCategory(forProperty("category")) : null;
    }

}

