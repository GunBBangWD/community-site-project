package com.blue.bluearchive.admin.service;


import com.blue.bluearchive.admin.dto.*;
import com.blue.bluearchive.admin.repository.*;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.constant.MemberStat;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.report.entity.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserCareSerivce {
    private final AdminUserCareRepository memberRepository;
    private final AdminBoardRepository boardRepository;
    private final AdminCommentRepository commentRepository;
    private final AdminCommentsCommentRepository commentsCommentRepository;



    private Page<AdminUserCareDto> getMember(Page<Member> members) {
        List<AdminUserCareDto> memberDtos = new ArrayList<>();
        for (Member member : members) {
            Integer report = 0;
            AdminUserCareDto userCareDto = new AdminUserCareDto();
            userCareDto.setMemberIdx(member.getIdx());
            userCareDto.setUserCreateBy(member.getCreatedBy());
            userCareDto.setUpdateTime(member.getUpdateTime());
            userCareDto.setUserEmail(member.getEmail());
            userCareDto.setUserName(member.getName());
            userCareDto.setUserId(member.getId());
            userCareDto.setUserState(member.getMemberStat().getKoreanName());
            userCareDto.setUserUntreatedReport(report);
            userCareDto.setAccumulateReport(getAllReportsForMember(member).size());
            userCareDto.setUserUntreatedReport(getAllReportsForMember2(member).size());
            userCareDto.setRegTime(member.getRegTime());
            memberDtos.add(userCareDto);
        }
        long total = members.getTotalElements();
        System.out.println("total===="+total);
        return new PageImpl<>(memberDtos,members.getPageable(),total);
    }

    @Transactional(readOnly = true)
    public Page<AdminUserCareDto> getMemberBoardPageMain(String categoryId, AdminSearchDto adminSearchDto, Pageable pageable) {
        System.out.println("service===0"+categoryId);
        Page<AdminUserCareDto> adminUserCareDtos = null;
        Page<Member> memberPage = null;
        if(categoryId.equals("0")){
            System.out.println("00000000000000000");
            memberPage = memberRepository.getMemberCaretPage(adminSearchDto, pageable);
            adminUserCareDtos = getMember(memberPage);
        }else{
            System.out.println("카테고리값이 0 이외의 멤버상태 조회시 이거 동작");
            memberPage = memberRepository.getMemberPage(categoryId, adminSearchDto, pageable);
            adminUserCareDtos = getMember(memberPage);
        }
        if (memberPage.isEmpty()) {
            return Page.empty(); // 검색 결과가 없을 경우 빈 페이지 반환
        }
        return adminUserCareDtos;
    }
    //누적 신고수
    public List<Report> getAllReportsForMember(Member member) {
        List<Board> boards = boardRepository.findBoardByMember(member);
        List<Comment> comments = commentRepository.findByCreatedBy(String.valueOf(member.getIdx()));
        List<CommentsComment> commentsComments = commentsCommentRepository.findByCreatedBy(String.valueOf(member.getIdx()));
        List<Report> reports = new ArrayList<>();
        for (Board board : boards) {
            reports.addAll(board.getReport());
        }
        for (Comment comment : comments) {
            reports.addAll(comment.getReport());
        }
        for (CommentsComment commentsComment : commentsComments) {
            reports.addAll(commentsComment.getReport());
        }
        System.out.println("reports:"+reports.size());
        return reports;
    }
    // 미처리 신고수
    public List<Report> getAllReportsForMember2(Member member) {
        List<Board> boards = boardRepository.findBoardByMember(member);
        List<Comment> comments = commentRepository.findByCreatedBy(String.valueOf(member.getIdx()));
        List<CommentsComment> commentsComments = commentsCommentRepository.findByCreatedBy(String.valueOf(member.getIdx()));
        List<Report> reports = new ArrayList<>();
        for (Board board : boards) {
            for (Report report : board.getReport()) {
                if (!report.isReportStatus()) {
                    reports.add(report);
                }
            }
        }

        for (Comment comment : comments) {
            for (Report report : comment.getReport()) {
                if (!report.isReportStatus()) {
                    reports.add(report);
                }
            }
        }

        for (CommentsComment commentsComment : commentsComments) {
            for (Report report : commentsComment.getReport()) {
                if (!report.isReportStatus()) {
                    reports.add(report);
                }
            }
        }
        return reports;
    }


    public String findUser(Long selectUser) {
        Optional<Member> member = memberRepository.findById(selectUser);
        return member.get().getName();
    }

    //    public List<AdminCareMemberBoard> getBoards(Long selectUser) {
//        List<AdminCareMemberBoard> boards = new ArrayList<>();
//        Optional<Member> member = memberRepository.findById(selectUser);
//        List<Board> boardList = boardRepository.findBoardByMember(member.get());
//        for(Board board : boardList){
//            AdminCareMemberBoard adminCareMemberBoard = new AdminCareMemberBoard();
//            adminCareMemberBoard.setBoardTitle(board.getBoardTitle());
//            adminCareMemberBoard.setBoardReportCnt(board.getBoardReportsCount());
//            adminCareMemberBoard.setBoardCnt(board.getBoardCount());
//            adminCareMemberBoard.setRegTime(board.getRegTime());
//            boards.add(adminCareMemberBoard);
//        }
//        return boards;
//    }
    //유저관리 게시물쪽
    public Page<AdminMemberBoard> getBoards(Page<Board> userBoards) {
        List<AdminMemberBoard> boards = new ArrayList<>();
        for(Board board : userBoards){
            AdminMemberBoard adminCareMemberBoard = new AdminMemberBoard();
            adminCareMemberBoard.setBoardTitle(board.getBoardTitle());
            adminCareMemberBoard.setBoardReportCnt(board.getBoardReportsCount());
            adminCareMemberBoard.setBoardCnt(board.getBoardCount());
            adminCareMemberBoard.setRegTime(board.getRegTime());
            adminCareMemberBoard.setBoardLikeCnt(board.getBoardLikeCount());
            adminCareMemberBoard.setBoardHateCnt(board.getBoardHateCount());
            boards.add(adminCareMemberBoard);
        }  long total = userBoards.getTotalElements();
        return new PageImpl<>(boards,userBoards.getPageable(),total);
    }
    @Transactional(readOnly = true)
    public Page<AdminMemberBoard> getMemberBoardPageMain(Long selectUser, Pageable pageable) {
        Page<AdminMemberBoard> boards = null;
        Page<Board> memberPage = boardRepository.getMemberPage(selectUser, pageable);
        boards = getBoards(memberPage);

        return boards;
    }

    //유저 관리 댓글
    public Page<AdminMemberCommentDto> getComment(Page<Comment> userComments) {
        List<AdminMemberCommentDto> comments = new ArrayList<>();
        for(Comment comment : userComments){
            AdminMemberCommentDto adminCareMemberComment = new AdminMemberCommentDto();
            adminCareMemberComment.setCommentContent(comment.getCommentContent());
            adminCareMemberComment.setHateCnt(comment.getCommentHateCount());
            adminCareMemberComment.setLikeCnt(comment.getCommentLikeCount());
            adminCareMemberComment.setReoortCnt(comment.getCommentReportsCount());
            adminCareMemberComment.setRegTime(comment.getRegTime());
            comments.add(adminCareMemberComment);
        }  long total = userComments.getTotalElements();
        return new PageImpl<>(comments,userComments.getPageable(),total);
    }
    @Transactional(readOnly = true)
    public Page<AdminMemberCommentDto> getMemberCommentPageMain(Long selectUser, Pageable pageable) {
        Page<AdminMemberCommentDto> comments = null;
        Page<Comment> memberPage = commentRepository.getMemberPage(selectUser, pageable);
        comments = getComment(memberPage);
        return comments;
    }
    //유저관리 대댓글
    public Page<AdminMemberCommentsCommentDto> getCommentsComment(Page<CommentsComment> userCommentsComment) {
        List<AdminMemberCommentsCommentDto> commentsCommentDtos = new ArrayList<>();
        for(CommentsComment commentsComment : userCommentsComment){
            AdminMemberCommentsCommentDto adminCareMemberCommentsComment = new AdminMemberCommentsCommentDto();
            adminCareMemberCommentsComment.setCommentsComment(commentsComment.getCommentsCommentContent());
            adminCareMemberCommentsComment.setHateCnt(commentsComment.getCommentsCommentHateCount());
            adminCareMemberCommentsComment.setLikeCnt(commentsComment.getCommentsCommentLikeCount());
            adminCareMemberCommentsComment.setRegTime(commentsComment.getRegTime());
            adminCareMemberCommentsComment.setReoortCnt(commentsComment.getCommentsCommentReportsCount());
            commentsCommentDtos.add(adminCareMemberCommentsComment);
        }  long total = userCommentsComment.getTotalElements();
        return new PageImpl<>(commentsCommentDtos,userCommentsComment.getPageable(),total);
    }
    @Transactional(readOnly = true)
    public Page<AdminMemberCommentsCommentDto> getMemberCommentsCommentPageMain(Long selectUser, Pageable pageable) {
        Page<AdminMemberCommentsCommentDto> commentsCommentDtos = null;
        Page<CommentsComment> memberCommentsCommentPage = commentsCommentRepository.getMemberCommentsComment(selectUser, pageable);
        commentsCommentDtos = getCommentsComment(memberCommentsCommentPage);

        return commentsCommentDtos;
    }

    @Transactional(readOnly = false)
    public void changeUser(List<Long> userIdxs,String statName) {
        for(Long userIdx : userIdxs){
            Member member = memberRepository.findById(userIdx).orElseThrow(NoSuchElementException::new);
            member.setMemberStat(MemberStat.fromString(statName));
            memberRepository.save(member);
        }
    }


}
