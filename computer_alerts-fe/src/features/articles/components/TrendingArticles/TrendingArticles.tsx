import { fetchAllsArticles } from "../../api/getAllArticles";
import React, { useEffect, useState, useRef } from "react";
import { ArticleRequestModel } from "../../models/ArticleRequestModel";
import { useNavigate } from "react-router-dom";
import { likeArticle } from "../../api/likeArticle";
import { unlikeArticle } from "../../api/unlikeArticle";
import { HeartAnimation } from "../../components/animations/HeartAnimation";
import axios from "axios";
import "./TrendingArticles.css";

const TrendingArticles: React.FC = () => {
  const [trendingArticles, setTrendingArticles] = useState<
    ArticleRequestModel[]
  >([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [likedArticles, setLikedArticles] = useState<{
    [articleId: string]: boolean;
  }>({});
  const heartRefs = useRef<{ [key: string]: HTMLDivElement | null }>({});
  const navigate = useNavigate();

  const refreshLikedState = (articles: ArticleRequestModel[]) => {
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

  useEffect(() => {
    const fetchTrendingArticles = async () => {
      try {
        const data = await fetchAllsArticles();
        const sortedArticles = data.sort(
          (a, b) => b.requestCount - a.requestCount,
        );
        const topArticles = sortedArticles.slice(0, 3);
        setTrendingArticles(topArticles);
        refreshLikedState(topArticles);
      } catch (err) {
        console.error("Error fetching trending articles:", err);
        setError("Failed to fetch trending articles.");
      } finally {
        setLoading(false);
      }
    };

    fetchTrendingArticles();
  }, []);

  const handleLikeToggle = async (articleId: string) => {
    if (articleId) {
      try {
        const isLiked = likedArticles[articleId];
        const heartElement = heartRefs.current[articleId];

        if (isLiked) {
          await unlikeArticle(
            articleId,
            "06a7d573-bcab-4db3-956f-773324b92a80",
          );
          localStorage.setItem(`article-${articleId}-liked`, "false");
        } else {
          if (heartElement) {
            const animation = new HeartAnimation(heartElement);
            animation.play();
          }
          await likeArticle(articleId, "06a7d573-bcab-4db3-956f-773324b92a80");
          localStorage.setItem(`article-${articleId}-liked`, "true");
        }
        setLikedArticles((prev) => ({
          ...prev,
          [articleId]: !isLiked,
        }));
      } catch (err) {
        console.error("Error toggling like/unlike:", err);
      }
    }
  };

  const patchArticleTrend = async (articleId: string) => {
    try {
      await axios.patch(`/articles/${articleId}`);
      console.log("Article trend updated successfully.");
    } catch (err) {
      console.error("Error updating article trend:", err);
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

  if (loading) return <p>Loading trending articles...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="trending-articles container">
      <div className="row">
        {trendingArticles.length === 0 ? (
          <p>No trending articles found.</p>
        ) : (
          <>
            {/* Big article on the left */}
            <div className="col-lg-8 col-md-6 mb-4 bigBoy">
              {trendingArticles[0] && (
                <div className="card h-100">
                  <img
                    src={trendingArticles[0].photoUrl}
                    className="card-img-top"
                    alt={trendingArticles[0]?.title || "Big article image"}
                  />
                  <div className="card-body">
                    <h5
                      className="card-title"
                      onClick={() =>
                        handleArticleClick(trendingArticles[0].articleId)
                      }
                    >
                      {trendingArticles[0].title}
                    </h5>
                    <div className="like-section">
                      <div
                        id="heart"
                        ref={(el) =>
                          (heartRefs.current[
                            trendingArticles[0].articleId || ""
                          ] = el)
                        }
                        className={`button ${
                          likedArticles[trendingArticles[0].articleId || ""]
                            ? "active"
                            : ""
                        }`}
                        onClick={() =>
                          trendingArticles[0].articleId &&
                          handleLikeToggle(trendingArticles[0].articleId)
                        }
                      ></div>
                      <p className="like-count">
                        {likedArticles[trendingArticles[0].articleId || ""]
                          ? 1
                          : 0}
                      </p>
                    </div>
                  </div>
                </div>
              )}
            </div>

            {/* Two smaller articles on the right */}
            <div className="col-lg-4 col-md-6 smallBoys">
              {trendingArticles.slice(1).map((article) => (
                <div key={article.articleId} className="card mb-4 h-100">
                  <img
                    src={article.photoUrl}
                    className="card-img-top"
                    alt={article.title || "Trending article image"}
                  />
                  <div className="card-body">
                    <h5
                      className="card-title"
                      onClick={() => handleArticleClick(article.articleId)}
                    >
                      {article.title}
                    </h5>
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
                          article.articleId &&
                          handleLikeToggle(article.articleId)
                        }
                      ></div>
                      <p className="like-count">
                        {likedArticles[article.articleId || ""] ? 1 : 0}
                      </p>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default TrendingArticles;
