package com.chathurya.service;

import com.chathurya.modal.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long issueId, Long userId, String comment) throws Exception;

    void deleteComment(Long issueId, Long userId)throws Exception;

    List<Comment> findCommentByIssueId(Long issueId) throws Exception;
}
