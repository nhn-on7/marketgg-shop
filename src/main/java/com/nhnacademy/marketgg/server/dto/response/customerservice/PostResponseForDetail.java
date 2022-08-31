package com.nhnacademy.marketgg.server.dto.response.customerservice;

import com.nhnacademy.marketgg.server.dto.info.MemberNameResponse;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(name = "게시글 번호", description = "게시글의 식별번호입니다.", example = "1")
    private final Long id;

    @Schema(name = "카테고리 번호", description = "게시글이 포함된 카테고리의 식별번호입니다.", example = "701")
    private final String categoryCode;

    @Schema(name = "게시글 제목", description = "게시글의 제목입니다.", example = "안녕 디지몬")
    private final String title;

    @Schema(name = "게시글 내용", description = "게시글의 내용입니다.", example = "반갑습니다잉")
    private final String content;

    @Schema(name = "게시글 사유", description = "게시글의 사유입니다.", example = "배송")
    private final String reason;

    @Schema(name = "게시글 상태", description = "게시글의 상태입니다.", example = "답변 완료")
    private final String status;

    @Schema(name = "작성일자", description = "게시글의 작성일자입니다.", example = "2020-07-10T15:00:00.000")
    private final LocalDateTime createdAt;

    @Schema(name = "수정일자", description = "게시글의 수정일자입니다.", example = "2020-07-10T15:00:00.000")
    private final LocalDateTime updatedAt;

    @Schema(name = "댓글목록", description = "게시글의 댓글 목록입니다.", example = "작성자, 이름, 내용을 담은 댓글 정보")
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
