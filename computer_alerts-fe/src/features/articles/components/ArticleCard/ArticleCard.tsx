import React, { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./ArticleCard.css";
import { ArticleRequestModel } from "../../models/ArticleRequestModel";
import { likeArticle } from "../../api/likeArticle";
import { unlikeArticle } from "features/articles/api/unlikeArticle";
import { shareArticle } from "../../api/shareArticle";
import { HeartAnimation } from "../../components/animations/HeartAnimation";
import shareIcon from "../../../../assets/share-icon.png"; // Import the share icon image
import { SaveModel } from "../../../savedArticles/model/SaveModel";
import { addSave } from "../../../savedArticles/api/addSave";
import { deleteSave } from "../../../savedArticles/api/deleteSave";
import { Button } from "react-bootstrap";
import { getAllSaves } from "features/savedArticles/api/getAllSaves";
import saveIcon from "../../../../assets/saveIcon.png";
import savedIcon from "../../../../assets/savedIcon.png";


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
  articles: ArticleRequestModel[];
}

const ArticleCard: React.FC<ArticleCardProps> = ({ articles }) => {
  const navigate = useNavigate();
  const [likedArticles, setLikedArticles] = useState<{
    [articleId: string]: boolean;
  }>({});
  const heartRefs = useRef<{ [key: string]: HTMLDivElement | null }>({});
  const [showToast, setShowToast] = useState(false);
  const [savedArticles, setSavedArticles] = useState<{
    [articleId: string]: SaveModel | null;
  }>({});
  const readerId = "06a7d573-bcab-4db3-956f-773324b92a80";

  useEffect(() => {
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

  useEffect(() => {
    const initializeSavedState = () => {
      // Fetch saved states from API instead
      const fetchSavedStates = async () => {
        try {
          const response = await getAllSaves(readerId);
          const data = response;
          const savedStates = data.reduce(
            (acc: { [key: string]: SaveModel }, save: SaveModel) => {
              acc[save.articleId] = save;
              return acc;
            },
            {},
          );
          setSavedArticles(savedStates);
        } catch (err) {
          console.error("Error fetching saved states:", err);
        }
      };
      fetchSavedStates();
    };

    initializeSavedState();
  }, [readerId]);

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

  const handleSaveToggle = async (articleId: string) => {
    try {
      const isSaved = savedArticles[articleId];

      if (isSaved) {
        // Remove the save
        await deleteSave(isSaved.saveId);
        setSavedArticles((prev) => ({
          ...prev,
          [articleId]: null,
        }));
      } else {
        // Add a new save
        const newSave: SaveModel = await addSave({
          articleId,
          readerId,
        });
        setSavedArticles((prev) => {
          const updated = { ...prev };
          updated[articleId] = newSave;
          return updated;
        });
      }
    } catch (err) {
      console.error("Error toggling save:", err);
    }
  };

  const handleArticleClick = (articleId: string | undefined) => {
    if (articleId) {
      navigate(`/articles/${articleId}`);
    } else {
      console.error("Invalid articleId. Cannot navigate.");
    }
  };

  const handleShareClick = async (articleId: string | undefined) => {
    if (articleId) {
      try {
        await navigator.clipboard.writeText(
          window.location.origin + `/articles/${articleId}`,
        );
        await shareArticle(articleId, "READER_ID"); // Replace with actual reader ID
        setShowToast(true);
        setTimeout(() => setShowToast(false), 3000);
      } catch (err) {
        console.error("Error sharing article:", err);
      }
    }
  };

  if (!articles.length) return <p>No articles found.</p>;

  return (
    <>
      <div className="article-card">
        {articles
          .filter((article) => article.articleStatus === "PUBLISHED")
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

                <div className="like-share-section">
                  {/* Like (Heart) */}
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

                  {/* Save Icon */}
                  <div
                    className="save-icon-card"
                    onClick={() =>
                      article.articleId && handleSaveToggle(article.articleId)
                    }
                    title={savedArticles[article.articleId] ? "Unsave" : "Save"}
                  >
                    <img
                      src={savedArticles[article.articleId] ? savedIcon : saveIcon}
                      alt={savedArticles[article.articleId] ? "Unsave" : "Save"}
                      className="save-icon"
                    />
                  </div>

                  {/* Share Icon */}
                  <img
                    src={shareIcon}
                    alt="Share"
                    className="share-icon-card"
                    onClick={() => handleShareClick(article.articleId)}
                  />
                </div>

              </div>
            </div>
          ))}
      </div>

      {showToast && (
        <div className="toast">Link copied and share registered!</div>
      )}
    </>
  );
};

export default ArticleCard;
