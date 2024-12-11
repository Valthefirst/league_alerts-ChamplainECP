import React, { useEffect, useState, useRef } from "react";
import { Link, useParams } from "react-router-dom";
import { fetchArticleByArticleId } from "../../api/getSpecificArticle";
import { likeArticle } from "../../api/likeArticle";
import { unlikeArticle } from "../../api/unlikeArticle";
import { ArticleRequestModel } from "../../models/ArticleRequestModel";
import { HeartAnimation } from "../../components/animations/HeartAnimation";
import "./ArticleDetails.css";
import { Author } from "features/authors/model/Author";
import { getAllAuthors } from "features/authors/api/getAllAuthors";

const NotFound: React.FC = () => (
  <div className="not-found-container">
    <h1 className="not-found-title">404</h1>
    <p className="not-found-message">Uh oh, you lost yourself.</p>
  </div>
);

const ArticleDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [article, setArticle] = useState<ArticleRequestModel | null>(null);
  const [author, setAuthor] = useState<Author | null>(null);
  const [comments, setComments] = useState<string[]>([]);
  const [newComment, setNewComment] = useState<string>("");
  const [isLiked, setIsLiked] = useState<boolean>(false);
  const [likeCount, setLikeCount] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const heartRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    const loadArticleAndAuthor = async () => {
      try {
        if (id) {
          const articleData = await fetchArticleByArticleId(id);
          setArticle(articleData);
          setLikeCount(articleData.likeCount);

          const authorsData = await getAllAuthors();
          const foundAuthor = authorsData.find((author) =>
            author.articles.articleList?.some((a) => a.articleId === id)
          );
          setAuthor(foundAuthor || null);

          // Fetch like state for the current user
          const liked = localStorage.getItem(`article-${id}-liked`) === "true";
          setIsLiked(liked);
        } else {
          setError("Invalid article ID");
        }
      } catch (err) {
        console.error("Error fetching article or author:", err);
        setError("Failed to fetch the article");
      } finally {
        setLoading(false);
      }
    };

    loadArticleAndAuthor();
  }, [id]);

  useEffect(() => {
    if (heartRef.current) {
      const heartEl = heartRef.current;
      heartEl.classList.remove("active"); // Ensure the heart starts in its default state
      new HeartAnimation(heartEl);
    }
  }, []);

  const handleLikeToggle = async () => {
    if (id && heartRef.current) {
      try {
        const readerId = "06a7d573-bcab-4db3-956f-773324b92a80"; // Replace with actual reader ID
        if (isLiked) {
          await unlikeArticle(id, readerId);
          setLikeCount((prevCount) => Math.max(prevCount - 1, 0));
          localStorage.setItem(`article-${id}-liked`, "false"); // Persist state
        } else {
          const animation = new HeartAnimation(heartRef.current);
          animation.play();
          await likeArticle(id, readerId);
          setLikeCount((prevCount) => prevCount + 1);
          localStorage.setItem(`article-${id}-liked`, "true"); // Persist state
        }
        setIsLiked(!isLiked);
      } catch (err) {
        console.error("Error toggling like/unlike:", err);
      }
    }
  };

  const addComment = () => {
    if (newComment.trim()) {
      setComments([...comments, newComment]);
      setNewComment("");
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <NotFound />;

  return article ? (
    <div className="article-container">
      <div className="article-image">
        <div className="image-placeholder">
        {article.photoUrl && (
                  <img
                  src={article.photoUrl}
                  alt={article.title}
                  className="article-image"
                  style={{ width: "100%", height: "auto", borderRadius: "8px", marginBottom: "10px" }}
                />
                )}
        </div>
      </div>
      <div className="like-section">
        <div
          id="heart"
          className={`button ${isLiked ? "active" : ""}`}
          ref={heartRef}
          onClick={handleLikeToggle}
        ></div>
        <p className="like-count">{likeCount}</p>
      </div>
      <h1 className="article-title">{article.title}</h1>
      <p className="article-body">{article.body}</p>
      {author && (
        <p className="article-author">
          <strong>Author:</strong>{" "}
          <Link to={`/authors/${author.authorId}`}>
            {author.firstName} {author.lastName}
          </Link>
        </p>
      )}
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
            <p className="no-comments">
              No comments yet. Be the first to comment!
            </p>
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
