package com.calerts.computer_alertsbe.utils;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Like;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.LikeResponseModel;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Comment;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.CommentRequestModel;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.CommentResponseModel;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;

import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleRequestModel;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleResponseModel;
import com.calerts.computer_alertsbe.authorsubdomain.datalayer.Author;
import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorResponseModel;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.Reader;
import com.calerts.computer_alertsbe.readersubdomain.presentationlayer.ReaderResponseModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.UUID;

public class EntityModelUtil {

    public static ReaderResponseModel toReaderResponseModel(Reader reader) {
        ReaderResponseModel readerResponseModel = new ReaderResponseModel();
        BeanUtils.copyProperties(reader, readerResponseModel);
        return readerResponseModel;
    }

    public static ArticleResponseModel toArticleResponseModel(Article article) {
        ArticleResponseModel articleResponseModel = new ArticleResponseModel();
        BeanUtils.copyProperties(article, articleResponseModel);
        articleResponseModel.setArticleId(article.getArticleIdentifier().getArticleId());
        articleResponseModel.setLikeCount(article.getLikeCount());
        articleResponseModel.setArticleStatus(article.getArticleStatus());
        articleResponseModel.setRequestCount(article.getRequestCount());
        articleResponseModel.setPhotoUrl(article.getPhotoUrl());
        return articleResponseModel;
    }

    public static Article toArticleEntity(ArticleRequestModel articleRequestModel) {
        return  Article.builder()
                .articleIdentifier(new ArticleIdentifier())
                .body(articleRequestModel.getBody())
                .tagsTag(articleRequestModel.getTagsTag())
                .tags(articleRequestModel.getTags())
                .title(articleRequestModel.getTitle())
                .articleStatus(articleRequestModel.getArticleStatus())
                .wordCount(articleRequestModel.getBody().split(" ").length)
                .timePosted(articleRequestModel.getTimePosted())
                .authorIdentifier(articleRequestModel.getAuthorIdentifier())
                .photoUrl(articleRequestModel.getPhotoUrl())
                .build();
    }


    public static AuthorResponseModel toAuthorResponseModel(Author author) {
        AuthorResponseModel authorResponseModel = new AuthorResponseModel();
        BeanUtils.copyProperties(author, authorResponseModel);
        authorResponseModel.setAuthorId(author.getAuthorIdentifier().getAuthorId());
        authorResponseModel.setBiography(author.getBiography().getBiography());
        authorResponseModel.setArticles(author.getArticles());
        return authorResponseModel;
    }

    public static LikeResponseModel toLikeResponseModel(Like like) {
        LikeResponseModel likeResponseModel = new LikeResponseModel();
        BeanUtils.copyProperties(like, likeResponseModel);


        likeResponseModel.setLikeId(like.getLikeIdentifier().getLikeId());
        likeResponseModel.setArticleId(like.getArticleIdentifier().getArticleId());
        likeResponseModel.setTimestamp(like.getTimestamp());

        return likeResponseModel;
    }


    public static CommentResponseModel toCommentResponseModel(Comment comment) {
        CommentResponseModel commentResponseModel = new CommentResponseModel();
        BeanUtils.copyProperties(comment, commentResponseModel);
        commentResponseModel.setCommentId(comment.getCommentId().getCommentId());
        commentResponseModel.setArticleId(comment.getArticleId().getArticleId());
        commentResponseModel.setReaderId(comment.getReaderId());
        return commentResponseModel;
    }

    public static Comment toCommentEntity(CommentRequestModel commentRequestModel) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentRequestModel, comment);
        comment.setArticleId(new ArticleIdentifier(commentRequestModel.getArticleId()));
        return comment;
    }

    public static String generateUUIDString(){
        return new ArticleIdentifier(UUID.randomUUID().toString()).toString();
    }




}
