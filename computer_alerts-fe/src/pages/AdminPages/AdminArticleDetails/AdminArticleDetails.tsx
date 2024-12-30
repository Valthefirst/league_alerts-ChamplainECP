import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { fetchArticleByArticleIdWithNoPatch } from "../../../features/articles/api/getSpecificArticle";
import { ArticleRequestModel } from "../../../features/articles/models/ArticleRequestModel";
import { acceptArticle } from "../../../features/articles/api/acceptArticle";
import CircleCheckButton from "../../../assets/CheckMark/CheckMark";
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
      setTimeout(() => setSuccessMessage(null), 2000);
    } catch (err) {
      setError("Failed to accept article. Please try again later.");
    }
  };

  const handleCancelButton = async () => {
    return null;
  };

  if (loading) return <p>Loading article details...</p>;
  if (error) return <p>{error}</p>;
  if (!article) return <p>No article found.</p>;

  return (
    <div className="container con-color">
      <br />
      <br />
      <div className="admin-article-details">
        <div className="row">
          <div className="sameLine space-between">
            <h1>Review Article</h1>

            <div className="row sameLine "></div>
          </div>
        </div>

        <br />
        <hr />
        <br />

        <div className="row">
          <div className="col-12">
            <div className="sameLine space-between">
              <h2 className="bold">
                Name of Article: <span>{article.title}</span>
              </h2>
              <CircleCheckButton messageProp="Title" />
            </div>
          </div>
        </div>

        <br />
        <hr />
        <br />

        <div className="row">
          <div className="col-12">
            <div className="sameLine space-between">
              <h2 className="bold">
                Tags for Article: <span>{article.tags}</span>
              </h2>
              <CircleCheckButton messageProp="Tags" />
            </div>
          </div>
        </div>

        <br />
        <hr />
        <br />

        {/* <div className="row">
          <div className="col-12">
            <h2>
              SubHeading for Article: <span>{article.subheading}</span>
            </h2>
            <CircleCheckButton messageProp="SubHeading" />
          </div>
        </div> */}

        <div className="row">
          <div className="col-12">
            <div className="sameLine space-between">
              <h2 className="bold">Text of Article:</h2>
              <CircleCheckButton messageProp="Body" />
            </div>

            <p className="bodyArticle">{article.body}</p>
          </div>
        </div>

        <br />
        <hr />
        <br />

        <div className="row">
          <button className="btn btn-danger center">Change is needed</button>
        </div>

        <div className="row">
          <div className="col-6">
            <button
              onClick={handleCancelButton}
              className="btn btn-secondary cancelButton"
            >
              Cancel
            </button>
          </div>
          <div className="col-6">
            <button
              onClick={handleAcceptArticle}
              className="btn btn-success acceptButton"
            >
              Accept Article
            </button>
          </div>
        </div>

        {successMessage && (
          <div className="popup-message success">
            <p>{successMessage}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminArticleDetails;
