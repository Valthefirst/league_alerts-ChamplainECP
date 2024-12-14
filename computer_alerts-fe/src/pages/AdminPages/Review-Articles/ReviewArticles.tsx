import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchAllsArticles } from "../../../features/articles/api/getAllArticles";
import { ArticleRequestModel } from "../../../features/articles/models/ArticleRequestModel";
import ArticleMainComponent from "../../ArticlePages/ArticleMainComponent";
import "./ReviewArticles.css";

const ReviewArticles: React.FC = () => {
  const navigate = useNavigate();
  const [articles, setArticles] = useState<ArticleRequestModel[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchArticles = async () => {
      try {
        const allArticles = await fetchAllsArticles();
        const articlesToReview = allArticles.filter(
          (article: ArticleRequestModel) =>
            article.articleStatus === "ARTICLE_REVIEW",
        );
        setArticles(articlesToReview);
      } catch (err) {
        setError("Failed to fetch articles. Please try again later.");
      } finally {
        setLoading(false);
      }
    };
    fetchArticles();
  }, []);

  const handleArticleClick = (id: string) => {
    navigate(`/article/${id}`);
  };

  if (loading) return <p className="text-center">Loading articles...</p>;
  if (error) return <p className="text-center text-danger">{error}</p>;

  return (
    <div className="container review-articles">
      <h1>Here are your articles to review</h1>
      {articles.length > 0 ? (
        <div className="review-articles__list">
          {articles.map((article) => (
            <ArticleMainComponent
              key={article.articleId}
              imageURL={article.photoUrl}
              title={article.title}
              description={`Word Count: ${article.wordCount}`}
              tags={article.tags}
              onClick={() => handleArticleClick(article.articleId)}
            />
          ))}
        </div>
      ) : (
        <p className="text-center">No articles to review at the moment.</p>
      )}
    </div>
  );
};

export default ReviewArticles;
