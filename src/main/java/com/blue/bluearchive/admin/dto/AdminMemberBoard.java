package com.blue.bluearchive.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminMemberBoard {
    private String boardTitle; //게시글 제목
    private LocalDateTime regTime; //
    private Integer boardCnt;
    private Integer boardReportCnt;
    private Integer boardLikeCnt;
    private Integer boardHateCnt;
}
