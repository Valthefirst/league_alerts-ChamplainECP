import { useState, useEffect, useRef } from "react";
import { fetchArticleByArticleIdWithNoPatch } from "../../articles/api/getSpecificArticle";
import { ArticleRequestModel } from "../../articles/models/ArticleRequestModel";
import { SaveModel } from "../model/SaveModel";
import { Button } from "react-bootstrap";
import { deleteSave } from "../api/deleteSave";
import "./SavedArticlesList.css";
import { unlikeArticle } from "features/articles/api/unlikeArticle";
import { HeartAnimation } from "features/articles/components/animations/HeartAnimation";
import { likeArticle } from "features/articles/api/likeArticle";
import { Link } from "react-router-dom";

interface SavedArticlesListProps {
  readerId: string;
}

const SavedArticlesList: React.FC<SavedArticlesListProps> = ({ readerId }) => {
  const [saves, setSaves] = useState<SaveModel[]>([]);
  const [articles, setArticles] = useState<ArticleRequestModel[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [removingArticles, setRemovingArticles] = useState<Set<string>>(
    new Set()
  );
  const [likedArticles, setLikedArticles] = useState<{
    [articleId: string]: boolean;
  }>({});
  const heartRefs = useRef<{ [key: string]: HTMLDivElement | null }>({});

  // useEffect for SSE connection
  useEffect(() => {
    const fetchSaves = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/v1/interactions/saves/${readerId}`
        );
        if (!response.ok) {
          throw new Error("Failed to fetch saves");
        }
        const data: SaveModel[] = await response.json();
        setSaves(data);
      } catch (err) {
        setError("Failed to fetch saves");
        console.error("Error fetching saves:", err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchSaves();
  }, [readerId]);

  // useEffect to fetch articles when saves change
  useEffect(() => {
    const fetchArticles = async () => {
      try {
        const articlePromises = saves.map((save) =>
          fetchArticleByArticleIdWithNoPatch(save.articleId)
        );
        const fetchedArticles = await Promise.all(articlePromises);
        setArticles(fetchedArticles);
      } catch (err) {
        setError("Failed to fetch articles");
        console.error("Error fetching articles:", err);
      } finally {
        setIsLoading(false);
      }
    };

    if (saves.length > 0) {
      fetchArticles();
    }
  }, [saves]);

  // useEffect for likes
  useEffect(() => {
    const initializeLikedState = () => {
      const initialLikedState = articles.reduce((acc, article) => {
        if (article.articleId) {
          acc[article.articleId] =
            localStorage.getItem(`article-${article.articleId}-liked`) ===
            "true";
        }
        return acc;
      }, {} as { [articleId: string]: boolean });
      setLikedArticles(initialLikedState);
    };

    initializeLikedState();
  }, [articles]);

  if (isLoading) {
    return <div>Loading Saved Articles...</div>;
  }

  if (error) {
    return <div style={{ color: "red" }}>{error}</div>;
  }

  const handleUnsave = async (articleId: string, saveId: string) => {
    try {
      setRemovingArticles(
        (prev) => new Set(Array.from(prev).concat(articleId))
      );
      await deleteSave(saveId);

      // Wait for animation
      await new Promise((resolve) => setTimeout(resolve, 300));

      setSaves((prevSaves) =>
        prevSaves.filter((save) => save.saveId !== saveId)
      );
      setArticles((prevArticles) =>
        prevArticles.filter((a) => a.articleId !== articleId)
      );
    } catch (error) {
      console.error("Error unsaving article:", error);
      setRemovingArticles((prev) => {
        const newSet = new Set(prev);
        newSet.delete(articleId);
        return newSet;
      });
    }
  };

  const handleLikeToggle = async (articleId: string) => {
    try {
      const isLiked = likedArticles[articleId];
      const heartElement = heartRefs.current[articleId];

      if (isLiked) {
        await unlikeArticle(articleId, readerId);
        localStorage.setItem(`article-${articleId}-liked`, "false");
      } else {
        if (heartElement) {
          const animation = new HeartAnimation(heartElement);
          animation.play();
        }
        await likeArticle(articleId, readerId);
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

  return (
    <div className="saved-articles-container">
      {articles.length > 0 ? (
        articles.map((article, index) => (
          <div
            key={article.articleId}
            className={`saved-article-item ${
              removingArticles.has(article.articleId) ? "removing" : ""
            }`}
          >
            <div className="article-header">
              <Link
                to={`/articles/${article.articleId}`}
                className="article-title-link"
              >
                <h1>{article.title}</h1>
              </Link>
              <i className="bi bi-arrow-90deg-right"></i>
            </div>
            <div className="article-image-container">
              <img
                src={article.photoUrl}
                alt={article.title}
                className="image"
                onError={(e) => {
                  e.currentTarget.src = "/default-article-image.jpg";
                }}
              />
            </div>
            <div className="article-content">
              <p>
                {article.body.split(" ").length > 20
                  ? article.body.split(" ").slice(0, 20).join(" ") + "..."
                  : article.body}
              </p>
            </div>
            <div className="btn-container">
              <div className="like-section">
                <div
                  id="heart"
                  ref={(el) => (heartRefs.current[article.articleId] = el)}
                  className={`button ${
                    likedArticles[article.articleId] ? "active" : ""
                  }`}
                  onClick={() => handleLikeToggle(article.articleId)}
                ></div>
                <p className="article-card-like-count">
                  {likedArticles[article.articleId] ? 1 : 0}
                </p>
              </div>
              <Button
                onClick={() => {
                  const saveToDelete = saves.find(
                    (save) => save.articleId === article.articleId
                  );
                  if (saveToDelete) {
                    handleUnsave(article.articleId, saveToDelete.saveId);
                  }
                }}
              >
                <i className="bi bi-bookmark"></i> Unsave
              </Button>
            </div>
            {index < articles.length - 1 && <hr className="separator" />}
          </div>
        ))
      ) : (
        <p>No saved articles found.</p>
      )}
    </div>
  );
};

export default SavedArticlesList;
