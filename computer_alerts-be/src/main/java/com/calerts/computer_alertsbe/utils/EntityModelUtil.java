package com.calerts.computer_alertsbe.utils;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Like;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.LikeResponseModel;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Comment;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.CommentRequestModel;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.CommentResponseModel;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleResponseModel;
import com.calerts.computer_alertsbe.authorsubdomain.datalayer.Author;
import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorResponseModel;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.Reader;
import com.calerts.computer_alertsbe.readersubdomain.presentationlayer.ReaderResponseModel;
import org.springframework.beans.BeanUtils;

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
        return articleResponseModel;
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
}
