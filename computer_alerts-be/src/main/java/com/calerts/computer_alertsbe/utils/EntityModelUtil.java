package com.calerts.computer_alertsbe.utils;



import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;
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
}
