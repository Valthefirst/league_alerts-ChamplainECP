package com.calerts.computer_alertsbe.utils;



import com.calerts.computer_alertsbe.articleservice.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.articleservice.presentationlayer.ArticleResponseModel;
import com.calerts.computer_alertsbe.readerservice.dataaccesslayer.Reader;
import com.calerts.computer_alertsbe.readerservice.presentationlayer.ReaderResponseModel;
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
        return articleResponseModel;
    }
}
