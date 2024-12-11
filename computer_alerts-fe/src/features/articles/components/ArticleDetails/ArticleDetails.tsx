import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { fetchArticleByArticleId } from "../../api/getSpecificArticle";
import { ArticleRequestModel } from "../../models/ArticleRequestModel";
import "./ArticleDetails.css"; // Import the CSS file
import { Author } from "features/authors/model/Author";
import { getAllAuthors } from "features/authors/api/getAllAuthors";

const NotFound: React.FC = () => (
  <div className="not-found-container">
    <h1 className="not-found-title">404</h1>
    <p className="not-found-message">Uh oh, you lost yourself.</p>
  </div>
);

const ArticleDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>(); // The articleId passed via route
  const [article, setArticle] = useState<ArticleRequestModel | null>(null);
  const [author, setAuthor] = useState<Author | null>(null);
  const [comments, setComments] = useState<string[]>([]); // Comment section state
  const [newComment, setNewComment] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadArticleAndAuthor = async () => {
      try {
        if (id) {
          const articleData = await fetchArticleByArticleId(id);
          if (!articleData || Object.keys(articleData).length === 0) {
            setError("Article not found");
          } else {
            setArticle(articleData);

            const authorsData: Author[] = await getAllAuthors();
            const foundAuthor = authorsData.find((author) =>
              author.articles.articleList?.some((a) => a.articleId === id),
            );
            setAuthor(foundAuthor || null);
          }
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

      <h1 className="article-title">{article.title}</h1>
      <p className="article-body">{article.body}</p>

      {author && (
        // <p className="article-author">
        //   <strong>Author:</strong> {author.firstName} {author.lastName}
        // </p>
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
