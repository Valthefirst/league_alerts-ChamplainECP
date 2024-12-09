import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { fetchArticleByArticleId } from "../../api/getSpecificArticle";
import { Article } from "../../models/Article";
import "./ArticleDetails.css"; // Import the CSS file

const NotFound: React.FC = () => (
  <div className="not-found-container">
    <h1 className="not-found-title">404</h1>
    <p className="not-found-message">Uh oh, you lost yourself.</p>
  </div>
);

const ArticleDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>(); // The articleId passed via route
  const [article, setArticle] = useState<Article | null>(null);
  const [comments, setComments] = useState<string[]>([]); // Comment section state
  const [newComment, setNewComment] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadArticle = async () => {
      try {
        if (id) {
          const data = await fetchArticleByArticleId(id);
          if (!data || Object.keys(data).length === 0) {
            // If no article is returned, set a 404 error
            setError("Article not found");
          } else {
            setArticle(data);
          }
        } else {
          setError("Invalid article ID");
        }
      } catch (err) {
        console.error("Error fetching article:", err);
        setError("Failed to fetch the article");
      } finally {
        setLoading(false);
      }
    };

    loadArticle();
  }, [id]);

  const addComment = () => {
    if (newComment.trim()) {
      setComments([...comments, newComment]);
      setNewComment("");
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) {
    return <NotFound />;
  }

  return article ? (
    <div className="article-container">
      <div className="article-image">
        <div className="image-placeholder">
          <p>No Image Available</p>
        </div>
      </div>

      <h1>
        This is the article ID: {article.articleId || "No ID available"}
      </h1>

      <h1 className="article-title">{article.title}</h1>
      <p className="article-body">{article.body}</p>

      <hr className="divider" />

      <div className="comments-section">
        <h2 className="comments-title">Comments</h2>
        <div className="comments-list">
          {comments.length > 0 ? (
            comments.map((comment, index) => (
              <div key={index} className="comment-item">
                {comment}
              </div>
            ))
          ) : (
            <p className="no-comments">No comments yet. Be the first to comment!</p>
          )}
        </div>

        <textarea
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
          placeholder="Write a comment..."
          className="comment-input"
          rows={3}
        />
        <button onClick={addComment} className="add-comment-button">
          Add Comment
        </button>
      </div>
    </div>
  ) : (
    <NotFound />
  );
};

export default ArticleDetails;
