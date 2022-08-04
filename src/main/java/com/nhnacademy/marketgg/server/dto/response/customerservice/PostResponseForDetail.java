package com.nhnacademy.marketgg.server.dto.response.customerservice;

import com.nhnacademy.marketgg.server.dto.MemberNameResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    private List<CommentResponse> addCommentList(final List<CommentReady> ready, final List<MemberNameResponse> nameList) {
        List<CommentResponse> response = new ArrayList<>();
        for (CommentReady commentReady : ready) {
            response.add(new CommentResponse(commentReady.getContent(), this.getName(commentReady.getUuid(), nameList),
                                             commentReady.getCreatedAt()));
        }
        return response;
    }

    private String getName(final String uuid, final List<MemberNameResponse> nameList) {
        for(MemberNameResponse name : nameList) {
            if(uuid.compareTo(name.getUuid()) == 0) {
                return name.getName();
            }
        }
        return null;
    }

}
