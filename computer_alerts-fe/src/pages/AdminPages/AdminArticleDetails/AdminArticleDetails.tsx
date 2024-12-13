import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { fetchArticleByArticleIdWithNoPatch } from "../../../features/articles/api/getSpecificArticle";
import { ArticleRequestModel } from "../../../features/articles/models/ArticleRequestModel";
import { acceptArticle } from "../../../features/articles/api/acceptArticle";
import "./AdminArticleDetails.css";

const AdminArticleDetails: React.FC = () => {
  const { articleId } = useParams<{ articleId: string }>();
  const [article, setArticle] = useState<ArticleRequestModel | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  useEffect(() => {
    const fetchArticle = async () => {
      try {
        if (articleId) {
          const data = await fetchArticleByArticleIdWithNoPatch(articleId);
          setArticle(data);
        }
      } catch (err) {
        setError("Failed to fetch article details. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchArticle();
  }, [articleId]);

  const handleAcceptArticle = async () => {
    if (!articleId) return;

    try {
      await acceptArticle(articleId);
      setSuccessMessage("Article successfully accepted.");
    } catch (err) {
      setError("Failed to accept article. Please try again later.");
    }
  };

  if (loading) return <p>Loading article details...</p>;
  if (error) return <p>{error}</p>;
  if (!article) return <p>No article found.</p>;

  return (
    <div className="admin-article-details">
      <h1>Article Details</h1>
      <h2>Title: {article.title}</h2>
      <p>
        <strong>Body:</strong> {article.body}
      </p>
      <p>
        <strong>Tags:</strong> {article.tags}
      </p>
      <p>
        <strong>Status:</strong> {article.articleStatus}
      </p>
      <p>
        <strong>Word Count:</strong> {article.wordCount}
      </p>
      {/* Uncomment if needed */}
      {/* <p><strong>Time Posted:</strong> {article.timePosted}</p>
            <p><strong>Author:</strong> {article.authorIdentifier}</p> */}

      <button onClick={handleAcceptArticle} className="accept-article-button">
        Accept Article
      </button>

      {successMessage && <p className="success-message">{successMessage}</p>}
    </div>
  );
};

export default AdminArticleDetails;
