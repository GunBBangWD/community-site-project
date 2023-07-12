package com.blue.bluearchive.naver_kakao.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNaverBlog is a Querydsl query type for NaverBlog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNaverBlog extends EntityPathBase<NaverBlog> {

    private static final long serialVersionUID = 1557820590L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNaverBlog naverBlog = new QNaverBlog("naverBlog");

    public final com.blue.bluearchive.admin.entity.QCategory category;

    public final StringPath naverBlogDescription = createString("naverBlogDescription");

    public final NumberPath<Integer> naverBlogId = createNumber("naverBlogId", Integer.class);

    public final StringPath naverBlogImg = createString("naverBlogImg");

    public final StringPath naverBlogLink = createString("naverBlogLink");

    public final StringPath naverBlogTitle = createString("naverBlogTitle");

    public QNaverBlog(String variable) {
        this(NaverBlog.class, forVariable(variable), INITS);
    }

    public QNaverBlog(Path<? extends NaverBlog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNaverBlog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNaverBlog(PathMetadata metadata, PathInits inits) {
        this(NaverBlog.class, metadata, inits);
    }

    public QNaverBlog(Class<? extends NaverBlog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.blue.bluearchive.admin.entity.QCategory(forProperty("category")) : null;
    }

}

