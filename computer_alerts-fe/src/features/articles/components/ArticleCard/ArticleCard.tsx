import React, { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./ArticleCard.css";
import { ArticleRequestModel } from "../../models/ArticleRequestModel";
import { likeArticle } from "../../api/likeArticle";
import { unlikeArticle } from "features/articles/api/unlikeArticle";
import { HeartAnimation } from "../../components/animations/HeartAnimation";

const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return new Intl.DateTimeFormat("en-US", {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  }).format(date);
};

interface ArticleCardProps {
  articles: ArticleRequestModel[]; // Pass articles as a prop
}

const ArticleCard: React.FC<ArticleCardProps> = ({ articles }) => {
  const navigate = useNavigate();
  const [likedArticles, setLikedArticles] = useState<{
    [articleId: string]: boolean;
  }>({});
  const heartRefs = useRef<{ [key: string]: HTMLDivElement | null }>({}); // Refs for animations

  useEffect(() => {
    // Initialize liked state from localStorage
    const initializeLikedState = () => {
      const initialLikedState = articles.reduce(
        (acc, article) => {
          if (article.articleId) {
            acc[article.articleId] =
              localStorage.getItem(`article-${article.articleId}-liked`) ===
              "true";
          }
          return acc;
        },
        {} as { [articleId: string]: boolean },
      );
      setLikedArticles(initialLikedState);
    };

    initializeLikedState();
  }, [articles]);

  const handleLikeToggle = async (articleId: string) => {
    try {
      const isLiked = likedArticles[articleId];
      const heartElement = heartRefs.current[articleId];

      if (isLiked) {
        await unlikeArticle(articleId, "READER_ID"); // Replace with actual reader ID
        localStorage.setItem(`article-${articleId}-liked`, "false");
      } else {
        if (heartElement) {
          const animation = new HeartAnimation(heartElement);
          animation.play();
        }
        await likeArticle(articleId, "READER_ID"); // Replace with actual reader ID
        localStorage.setItem(`article-${articleId}-liked`, "true");
      }

      setLikedArticles((prev) => ({
        ...prev,
        [articleId]: !isLiked,
      }));
    } catch (err) {
      console.error("Error toggling like:", err);
    }
  };

  const handleArticleClick = (articleId: string | undefined) => {
    if (articleId) {
      navigate(`/articles/${articleId}`);
    } else {
      console.error("Invalid articleId. Cannot navigate.");
    }
  };

  if (!articles.length) return <p>No articles found.</p>;

  return (
    <div className="article-card">
      {articles
        .filter((article) => article.articleStatus === "PUBLISHED") // Show only published articles
        .map((article) => (
          <div
            key={article.articleId}
            className="article-card-content"
            style={{ cursor: article.articleId ? "pointer" : "not-allowed" }}
          >
            {/* Article Image */}
            <div
              className="article-image-placeholder"
              onClick={() => handleArticleClick(article.articleId)}
            >
              {article.photoUrl && (
                <img
                  src={article.photoUrl}
                  alt={article.title}
                  className="article-image"
                />
              )}
            </div>

            {/* Article Details */}
            <div className="article-card-content-footer">
              <h3
                className="title-card"
                onClick={() => handleArticleClick(article.articleId)}
              >
                {article.title}
              </h3>
              <p className="card-body">
                <strong>Posted:</strong> {formatDate(article.timePosted)}
              </p>

              {/* Like Section */}
              <div className="like-section">
                <div
                  id="heart"
                  ref={(el) =>
                    (heartRefs.current[article.articleId || ""] = el)
                  }
                  className={`button ${
                    likedArticles[article.articleId || ""] ? "active" : ""
                  }`}
                  onClick={() =>
                    article.articleId && handleLikeToggle(article.articleId)
                  }
                ></div>
                <p className="article-card-like-count">
                  {likedArticles[article.articleId || ""] ? 1 : 0}
                </p>
              </div>
            </div>
          </div>
        ))}
    </div>
  );
};

export default ArticleCard;
