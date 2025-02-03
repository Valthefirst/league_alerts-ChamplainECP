import React, { useEffect, useState } from "react";
import TopArticlesChart from "./TopArticlesChart";
import { ReportModel } from "../model/ReportModel";
import { TopArticleModel } from "../model/TopArticleModel";
import { createReport } from "../api/createReport";
import { ArticleResponseModel } from "features/articles/models/ArticleResponseModel";
import { fetchArticleByArticleId } from "features/articles/api/getSpecificArticle";

const Dashboard: React.FC = () => {
  const [report, setReport] = useState<ReportModel | null>(null);
  const [articlesWithTitles, setArticlesWithTitles] = useState<TopArticleModel[]>([]);

  useEffect(() => {
    createReport().then(async (data: ReportModel) => {
      setReport(data);

      // Fetch titles for all articles
      const articlesWithTitles = await Promise.all(
        data.topArticles.map(async (article) => {
          try {
            const articleData: ArticleResponseModel = await fetchArticleByArticleId(article.articleId);
            return { ...article, articleId: articleData.title }; // Replace articleId with title
          } catch (error) {
            console.error(`Error fetching article ${article.articleId}:`, error);
            return { ...article, articleId: "Unknown Title" }; // If API fails
          }
        })
      );

      setArticlesWithTitles(articlesWithTitles);
    });
  }, []);

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">Top Articles Report</h1>
      {articlesWithTitles.length > 0 ? (
        <TopArticlesChart articles={articlesWithTitles} />
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default Dashboard;
