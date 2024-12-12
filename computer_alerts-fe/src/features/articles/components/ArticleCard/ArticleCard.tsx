import React, { useEffect, useState, useRef } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./ArticleCard.css";
import { ArticleRequestModel } from "../../models/ArticleRequestModel";
import { fetchArticleByTag } from "../../api/getAllArticleBySports";
import { likeArticle } from "../../api/likeArticle";
import { unlikeArticle } from "../../api/unlikeArticle";
import axios from "axios";
import { HeartAnimation } from "../../components/animations/HeartAnimation"; // Import the animation

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

const patchArticleTrend = async (articleId: string) => {
  try {
    await axios.patch(`/articles/${articleId}`);
    console.log("Article trend updated successfully.");
  } catch (err) {
    console.error("Error updating article trend:", err);
  }
};

const ArticleCard: React.FC = () => {
  const { tagName } = useParams<{ tagName: string }>();
  const navigate = useNavigate();
  const [articles, setArticles] = useState<ArticleRequestModel[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [likedArticles, setLikedArticles] = useState<{
    [articleId: string]: boolean;
  }>({});
  const heartRefs = useRef<{ [key: string]: HTMLDivElement | null }>({}); // Refs for animations

  // Function to refresh liked state from localStorage
  const refreshLikedState = (articles: ArticleRequestModel[]) => {
    const initialLikedState = articles.reduce((acc, article) => {
      if (article.articleId) {
        acc[article.articleId] =
          localStorage.getItem(`article-${article.articleId}-liked`) === "true";
      }
      return acc;
    }, {} as { [articleId: string]: boolean });
    setLikedArticles(initialLikedState);
  };

  useEffect(() => {
    const loadArticles = async () => {
      try {
        if (tagName) {
          const data = await fetchArticleByTag(tagName);
          setArticles(data);
          refreshLikedState(data); // Refresh liked state on load
        }
      } catch (err) {
        console.error("Error fetching articles:", err);
        setError("Failed to fetch articles.");
      } finally {
        setLoading(false);
      }
    };

    loadArticles();
  }, [tagName]);

  // Refresh like state when navigating back
  useEffect(() => {
    refreshLikedState(articles);
  }, [articles]);

  const handleLikeToggle = async (articleId: string) => {
    if (articleId) {
      try {
        const isLiked = likedArticles[articleId];
        const heartElement = heartRefs.current[articleId];

        if (isLiked) {
          await unlikeArticle(articleId, "06a7d573-bcab-4db3-956f-773324b92a80"); // Replace with actual reader ID
          localStorage.setItem(`article-${articleId}-liked`, "false");
        } else {
          if (heartElement) {
            const animation = new HeartAnimation(heartElement);
            animation.play();
          }
          await likeArticle(articleId, "06a7d573-bcab-4db3-956f-773324b92a80"); // Replace with actual reader ID
          localStorage.setItem(`article-${articleId}-liked`, "true");
        }
        setLikedArticles((prev) => ({
          ...prev,
          [articleId]: !isLiked,
        }));
      } catch (err) {
        console.error("Error toggling like:", err);
      }
    }
  };

  const handleArticleClick = (articleId: string | undefined) => {
    if (articleId) {
      navigate(`/articles/${articleId}`);
      patchArticleTrend(articleId);
    } else {
      console.error("Invalid articleId. Cannot navigate.");
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="article-card">
      {articles.length > 0 ? (
          articles.filter((article) => article.articleStatus === 'PUBLISHED')
              .map((article) => (

          <div
            key={article.articleId}
            className="article-card-content"
            style={{ cursor: article.articleId ? "pointer" : "not-allowed" }}
          >
            <div
              className="article-image-placeholder"
              onClick={() => handleArticleClick(article.articleId)}
            ></div>
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
              <div className="like-section">
                <div
                  id="heart"
                  ref={(el) => (heartRefs.current[article.articleId || ""] = el)}
                  className={`button ${
                    likedArticles[article.articleId || ""] ? "active" : ""
                  }`}
                  onClick={() =>
                    article.articleId && handleLikeToggle(article.articleId)
                  }
                ></div>
                <p className="like-count">
                  {likedArticles[article.articleId || ""] ? 1 : 0}
                </p>
              </div>
            </div>
          </div>
        ))
      ) : (
        <p>No articles found.</p>
      )}
    </div>
  );
};

export default ArticleCard;