package com.calerts.computer_alertsbe.utils;



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
        articleResponseModel.setArticleStatus(article.getArticleStatus()); // Map the enum directly
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
                .wordCount(articleRequestModel.getWordCount())
                .timePosted(articleRequestModel.getTimePosted())
                .authorIdentifier(articleRequestModel.getAuthorIdentifier())
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

    public static String generateUUIDString(){
        return new ArticleIdentifier(UUID.randomUUID().toString()).toString();
    }
}
