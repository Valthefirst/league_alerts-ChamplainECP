import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchAllsArticles} from "../../../features/articles/api/getAllArticles";
import { ArticleRequestModel } from "../../../features/articles/models/ArticleRequestModel";
import "./ReviewArticles.css";

const ReviewArticles: React.FC = () => {
    const navigate = useNavigate();
    const [articles, setArticles] = useState<ArticleRequestModel[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchArticles = async () => {
            try {
                const allArticles = await fetchAllsArticles();
                const articlesToReview = allArticles.filter(
                    (article: ArticleRequestModel) => article.articleStatus === "ARTICLE_REVIEW"
                );
                setArticles(articlesToReview);
            } catch (err) {
                setError("Failed to fetch articles. Please try again later.");
            } finally {
                setLoading(false);
            }
        };
        fetchArticles();
    }, []);

    const handleProductClick = (id: string) => {
        navigate(`/article/${id}`);
      };

    if (loading) return <p>Loading articles...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div className="review-articles">
            <h1>Here are your articles to review</h1>
            {articles.length > 0 ? (
                <ul className="review-articles__list">
                    {articles.map((article) => (
                        <li key={article.articleId} className="review-articles__item">
                            <h2 onClick={() => handleProductClick(article.articleId)}>
                                {article.title}
                            </h2>
                            <p>Word Count: {article.wordCount}</p>
                            <p>Tags: {article.tags}</p>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No articles to review at the moment.</p>
            )}
        </div>
    );
};

export default ReviewArticles;
