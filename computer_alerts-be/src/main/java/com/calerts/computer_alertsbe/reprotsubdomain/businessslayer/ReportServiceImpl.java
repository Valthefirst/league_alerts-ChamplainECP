package com.calerts.computer_alertsbe.reprotsubdomain.businessslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Comment;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.CommentRepository;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleRepository;
import com.calerts.computer_alertsbe.reprotsubdomain.dataaccesslayer.Report;
import com.calerts.computer_alertsbe.reprotsubdomain.dataaccesslayer.ReportIdentifier;
import com.calerts.computer_alertsbe.reprotsubdomain.dataaccesslayer.ReportRepository;
import com.calerts.computer_alertsbe.reprotsubdomain.dataaccesslayer.TopArticle;
import com.calerts.computer_alertsbe.reprotsubdomain.presentationlayer.ReportResponseModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
//    private final AuthorRepository authorRepository;

    public ReportServiceImpl(ReportRepository reportRepository, ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.reportRepository = reportRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

//    @Override
//    public Mono<ReportResponseModel> createReport() {
//        return articleRepository.findAll()
//                .collectList()
//                .map(articleList -> {
//                    Map<String, TopArticle> articleMap = new HashMap<>();
//
//                    for (Article article : articleList) {
//                        String articleId = article.getArticleIdentifier().getArticleId();
//                        articleMap.computeIfAbsent(articleId, id -> new TopArticle(id, 0, 0, 0, 0));
//
//                        TopArticle topArticle = articleMap.get(articleId);
//                        topArticle.setLikeCount(topArticle.getLikeCount() + article.getLikeCount());
//                        topArticle.setShareCount(topArticle.getShareCount() + article.getShareCount());
//                        topArticle.setRequestCount(topArticle.getRequestCount() + article.getRequestCount());
//
//                        int totalInteractions = topArticle.getLikeCount() + topArticle.getShareCount() + topArticle.getRequestCount();
//                        topArticle.setPoints(totalInteractions);
//                    }
//
//                    // Sort articles by total interactions descending
//                    List<TopArticle> sortedArticles = articleMap.values().stream()
//                            .sorted(Comparator.comparingInt(TopArticle::getPoints).reversed())
//                            .limit(10)
//                            .collect(Collectors.toList());
//
//                    return Report.builder()
//                            .reportIdentifier(new ReportIdentifier())
//                            .timestamp(LocalDateTime.now())
//                            .topArticles(sortedArticles)
//                            .build();
//                })
//                .flatMap(reportRepository::save)
//                .map(this::convertToResponseModel);
//    }

    @Override
    public Mono<ReportResponseModel> createReport() {
        Mono<List<Article>> articlesMono = articleRepository.findAll().collectList();
        Mono<List<Comment>> commentsMono = commentRepository.findAll().collectList();

        return Mono.zip(articlesMono, commentsMono)
                .map(tuple -> {
                    List<Article> articles = tuple.getT1();
                    List<Comment> comments = tuple.getT2();

                    // Map to store article interactions
                    Map<String, TopArticle> articleMap = new HashMap<>();

                    // Process articles
                    for (Article article : articles) {
                        String articleId = article.getArticleIdentifier().getArticleId();
                        articleMap.computeIfAbsent(articleId, id -> new TopArticle(id, 0, 0, 0, 0, 0));

                        TopArticle topArticle = articleMap.get(articleId);
                        topArticle.setLikeCount(topArticle.getLikeCount() + article.getLikeCount());
                        topArticle.setShareCount(topArticle.getShareCount() + article.getShareCount());
                        topArticle.setRequestCount(topArticle.getRequestCount() + article.getRequestCount());
                    }

                    // Process comments
                    for (Comment comment : comments) {
                        String articleId = comment.getArticleId().getArticleId();
                        articleMap.computeIfAbsent(articleId, id -> new TopArticle(id, 0, 0, 0, 0, 0));

                        TopArticle topArticle = articleMap.get(articleId);
                        topArticle.setCommentCount(topArticle.getCommentCount() + 1); // Increment comment count
                    }

                    // Calculate total points (likes + shares + requests + comments)
                    articleMap.values().forEach(article -> {
                        int totalInteractions = article.getLikeCount() + article.getShareCount() +
                                article.getRequestCount() + article.getCommentCount();
                        article.setPoints(totalInteractions);
                    });

                    // Sort by total interactions (descending order)
                    List<TopArticle> sortedArticles = articleMap.values().stream()
                            .sorted(Comparator.comparingInt(TopArticle::getPoints).reversed())
                            .limit(10)
                            .collect(Collectors.toList());

                    return Report.builder()
                            .reportIdentifier(new ReportIdentifier())
                            .timestamp(LocalDateTime.now())
                            .topArticles(sortedArticles)
                            .build();
                })
                .flatMap(reportRepository::save)
                .map(this::convertToResponseModel);
    }


    private ReportResponseModel convertToResponseModel(Report report) {
        ReportResponseModel responseModel = new ReportResponseModel();
        responseModel.setReportIdentifier(report.getReportIdentifier().getReportId());
        responseModel.setTimestamp(report.getTimestamp());
        responseModel.setTopArticles(report.getTopArticles());
        return responseModel;
    }
}
