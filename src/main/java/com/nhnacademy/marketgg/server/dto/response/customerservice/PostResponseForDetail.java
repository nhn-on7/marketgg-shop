package com.nhnacademy.marketgg.server.dto.response.customerservice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 고객센터 게시글의 상세 내용 DTO 입니다.
 *
 * @author 박세완
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public class PostResponseForDetail {

    private final Long id;

    private final String categoryCode;

    private final String title;

    private final String content;

    private final String reason;

    private final String status;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private final List<CommentResponse> commentList;

    /**
     * 게시글 상세 내용을 생성하기 위한 생성자입니다.
     *
     * @param response - Repository 에서 프로젝션한 가공하지 않은 상세 내용 DTO 입니다.
     * @param nameList - auth 서버에 uuid 를 통해 요청한 사용자의 이름 목록입니다.
     * @since 1.0.0
     */
    public PostResponseForDetail(final PostResponseForReady response, final List<MemberNameResponse> nameList) {
        this.id = response.getId();
        this.categoryCode = response.getCategoryCode();
        this.title = response.getTitle();
        this.content = response.getContent();
        this.reason = response.getReason();
        this.status = response.getStatus();
        this.createdAt = response.getCreatedAt();
        this.updatedAt = response.getUpdatedAt();
        this.commentList = this.addCommentList(response.getCommentReady(), nameList);
    }

    private List<CommentResponse> addCommentList(final List<CommentReady> ready,
                                                 final List<MemberNameResponse> nameList) {
        List<CommentResponse> response = new ArrayList<>();
        for (CommentReady commentReady : ready) {
            response.add(new CommentResponse(commentReady.getContent(), this.getName(commentReady.getUuid(), nameList),
                commentReady.getCreatedAt()));
        }
        return response;
    }

    private String getName(final String uuid, final List<MemberNameResponse> nameList) {
        for (MemberNameResponse name : nameList) {
            if (uuid.compareTo(name.getUuid()) == 0) {
                return name.getName();
            }
        }
        return null;
    }

}
