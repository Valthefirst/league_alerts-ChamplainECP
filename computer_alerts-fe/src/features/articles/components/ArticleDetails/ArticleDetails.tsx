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
import CommentList from "features/comments/components/CommentList";
import { addComment } from "features/comments/api/addComment";
import { CommentModel } from "features/comments/model/CommentModel";
import { shareArticle } from "../../api/shareArticle";
import shareIcon from "../../../../assets/share-icon.png"; // Import the share icon image
import EditArticle from "../EditArticle/EditArticleForm";

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
  const [newComment, setNewComment] = useState<string>("");
  const [isLiked, setIsLiked] = useState<boolean>(false);
  const [likeCount, setLikeCount] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [showSharePopup, setShowSharePopup] = useState(false);
  const [showToast, setShowToast] = useState(false);
  const heartRef = useRef<HTMLDivElement | null>(null);
  const [isEditing, setIsEditing] = useState(false);
  //const navigate = useNavigate();

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
      heartEl.classList.remove("active");
      new HeartAnimation(heartEl);
    }
  }, []);

  const handleLikeToggle = async () => {
    if (id && heartRef.current) {
      try {
        const readerId = "06a7d573-bcab-4db3-956f-773324b92a80";
        if (isLiked) {
          await unlikeArticle(id, readerId);
          setLikeCount((prevCount) => Math.max(prevCount - 1, 0));
          localStorage.setItem(`article-${id}-liked`, "false");
        } else {
          const animation = new HeartAnimation(heartRef.current);
          animation.play();
          await likeArticle(id, readerId);
          setLikeCount((prevCount) => prevCount + 1);
          localStorage.setItem(`article-${id}-liked`, "true");
        }
        setIsLiked(!isLiked);
      } catch (err) {
        console.error("Error toggling like/unlike:", err);
      }
    }
  };

  const postComment = () => {
    if (newComment.trim().split(/\s+/).length > 50) {
      alert("Comment is too long. Please keep it under 50 words.");
      return;
    }
    if (newComment.trim() && article) {
      const comment: Partial<CommentModel> = {
        content: newComment,
        articleId: article.articleId,
        readerId: "06a7d573-bcab-4db3-956f-773324b92a80",
      };
      addComment(comment);
      setNewComment("");
    }
  };

  const openEditPage = () => {
    if (article) {
      //navigate(`/articles/edit/${article.articleId}`, { state: { article } });
      setIsEditing(true);
      setShowSharePopup(false);
    }
  };


  const toggleSharePopup = () => setShowSharePopup(!showSharePopup);

  const copyToClipboard = async (text: string) => {
    const articleId = id;
    const readerId = "06a7d573-bcab-4db3-956f-773324b92a80";

    try {
      await navigator.clipboard.writeText(text);
      if (articleId) {
        await shareArticle(articleId, readerId);
      }
      setShowToast(true);
      setTimeout(() => setShowToast(false), 3000);
    } catch (error) {
      console.error("Error sharing or copying the link:", error);
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <NotFound />;

  return (
    <>
     
      <div className="article-container">
        <div className="article-image">
          {article?.photoUrl ? (
            <img src={article.photoUrl} alt={article.title} className="article-image" />
          ) : (
            <div className="image-placeholder">
              <p>No Image Available</p>
            </div>
          )}
        </div>
        <div className="like-section">
          <div className="like-container">
            <div
              id="heart"
              className={`button ${isLiked ? "active" : ""}`}
              ref={heartRef}
              onClick={handleLikeToggle}
            >
              <p className="like-count">{likeCount}</p>
            </div>
            <img
              src={shareIcon}
              alt="Share"
              className="share-icon"
              onClick={toggleSharePopup}
            />
          </div>
          <button className="edit-button" onClick={openEditPage}>
            Edit Article
          </button>
        </div>
        <h1 className="article-title">{article?.title}</h1>
        <p className="article-body">{article?.body}</p>
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
          {article?.articleId && (
            <CommentList articleId={{ articleId: article.articleId }} />
          )}
          <textarea
            value={newComment}
            onChange={(e) => setNewComment(e.target.value)}
            placeholder="Write a comment..."
            className="comment-input"
            rows={3}
          />
          <button onClick={postComment} className="add-comment-button">
            Add Comment
          </button>
        </div>
      </div>

     
      
      {showSharePopup && (
        <>
          <div className="modal-backdrop" onClick={toggleSharePopup}></div>
          <div className="share-modal">
            <p>Share this article using the link below!</p>
            <div className="share-link-container">
              <span className="share-link">{window.location.href}</span>
              <button
                onClick={() => copyToClipboard(window.location.href)}
                className="copy-button"
              >
                Copy
              </button>
            </div>
            <button onClick={toggleSharePopup} className="close-button">
              Close
            </button>
          </div>
        </>
      )}
       {isEditing && article && (
          <div
            className="edit-article-overlay"
            onClick={() => setIsEditing(false)} // Close form on overlay click
          >
            <div
              className="edit-article-container"
              onClick={(e) => e.stopPropagation()} // Prevent overlay click when interacting with the form
            >
              <EditArticle article={article} setIsEditing={setIsEditing} />
            </div>
          </div>
        )}
      {showToast && <div className="toast">Link copied and share registered!</div>}
    </>
  );
};

export default ArticleDetails;
