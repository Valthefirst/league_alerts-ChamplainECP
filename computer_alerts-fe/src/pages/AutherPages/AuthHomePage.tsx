import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchAllsArticles } from "features/articles/api/getAllArticles";
import { ArticleRequestModel } from "features/articles/models/ArticleRequestModel";
import ArticleMainComponent from "../ArticlePages/ArticleMainComponent";

const AuthHomePage: React.FC = () => {
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
  if (loading) return <p>Loading article details...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="container">
      <div className="row">
        <h1>Recently Added Articles</h1>
      </div>
      <div className="row">
        {articles.slice(0, 4).map((article) => (
          <ArticleMainComponent
            key={article.articleId}
            imageURL={article.photoUrl}
            title={article.title}
            description={`Word Count: ${article.wordCount}`}
            category={article.category}
            onClick={() => handleArticleClick(article.articleId)}
          />
        ))}
      </div>
    </div>
  );
};

export default AuthHomePage;
