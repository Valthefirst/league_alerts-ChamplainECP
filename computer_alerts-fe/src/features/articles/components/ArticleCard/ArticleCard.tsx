import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Article } from "../../models/Article";
import { fetchArticleByTag } from "../../api/getAllArticleBySports";
import "./ArticleCard.css";
import "bootstrap/dist/css/bootstrap.min.css";

// Helper function to format date
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

const ArticleCard: React.FC = () => {
    const { tagName } = useParams<{ tagName: string }>();
    const navigate = useNavigate();
    const [articles, setArticles] = useState<Article[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const loadArticles = async () => {
            try {
                if (tagName) {
                    const data = await fetchArticleByTag(tagName);
                    console.log("Fetched Articles:", data); // Debugging output
                    setArticles(data);
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

    const handleArticleClick = (articleId: string | undefined) => {
        if (articleId) {
            navigate(`/articles/${articleId}`);
        } else {
            console.error("Invalid articleId. Cannot navigate.");
        }
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div className="article-card">
            {articles.length > 0 ? (
                articles.map((article) => (
                    <div
                        key={article.articleId}
                        className="article-card-content"
                        onClick={() => handleArticleClick(article.articleId)}
                        style={{ cursor: article.articleId ? "pointer" : "not-allowed" }}
                    >
                        <div className="article-image-placeholder"></div>
                        <div className="article-card-content-footer">
                            <h3 className="title-card">{article.title}</h3>
                            <p className="card-body">
                                <strong>Tags:</strong> {article.tags}
                            </p>
                            <p className="card-body">
                                <strong>Posted:</strong> {formatDate(article.timePosted)}
                            </p>
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
