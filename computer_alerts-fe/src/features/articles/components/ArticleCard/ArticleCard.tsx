import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Article } from "../../models/Article";
import { fetchArticleByTag } from "../../api/getAllArticleBySports";
import './ArticleCard.css';
import 'bootstrap/dist/css/bootstrap.min.css';


// ArticleCard component is still not working. No articles are being fetched.

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
    const [articles, setArticles] = useState<Article[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const loadArticles = async () => {
            try {
                if (tagName) {
                    const data = await fetchArticleByTag(tagName);
                    //console.log("Fetched articles:", data); 
                    setArticles(data);
                }
            } catch (err) {
                setError("Failed to fetch the articles");
                //console.log("Failed to fetch articles", err);
            } finally {
                setLoading(false);
            }
        };

        loadArticles();
    }, [tagName]);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    return articles.length > 0 ? (
        <div className="article-card">
            {articles.map((articles) => (
                <div key={articles.id} className="article-card-content">
                <div className="article-image-placeholder"></div> {/* Empty placeholder for the image */}
                <div className="article-card-content-footer">
                    <h3 className="title-card">{articles.title}</h3>
                    <p className="card-body">
                        <strong className="card-body-content">Tags:</strong>
                        {articles.tags}
                    </p>
                    <p className="card-body">
                        <strong className="card-body-content">Date Posted:</strong>
                        {formatDate(articles.timePosted)}
                    </p>
                </div>
            </div>
            ))}
        </div>
    ) : (
        
            
        <p>No articles found</p>
         
       
       
    );
  
}

export default ArticleCard;

   


   