import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Article } from "../models/Article";
import { fectchArticleBytag } from "../api/getAllArticleBySports";


const ArticleCard: React.FC = () => {

    const { tag } = useParams<{ tag: string }>(); // The tag passed via route
    const [article, setArticles] = useState<Article[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const loadArticles = async () => {
            try {
                if (tag) {
                    const data = await fectchArticleBytag(tag);
                    setArticles(data);
                }
            } catch (err) {
                setError("Failed to fetch the articles");
            } finally {
                setLoading(false);
            }
        };

        loadArticles();
    }, [tag]);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    return article.length > 0 ? (
        <div className="article-card">
            {article.map((article) => (
                <div key={article.id} className="article-card-content">
                    <h3>{article.title}</h3>
                    <p>
                        <strong>
                            Tags:
                        </strong>
                        {article.tags}
                    </p>
                    <p>
                        <strong>
                            Date Posted:
                        </strong>
                        {article.timePosted}
                    </p>
                </div>
            ))}
        </div>
    ) : (
        <p>No articles found</p>
    );
  
}

export default ArticleCard;

   


   