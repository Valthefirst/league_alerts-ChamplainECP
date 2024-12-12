import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchAllsArticles } from "features/articles/api/getAllArticles";
import { ArticleRequestModel } from "features/articles/models/ArticleRequestModel";
import ArticleMainComponent from "../ArticlePages/ArticleMainComponent";
import "./AutherYourParticles.css"

const AutherYourArticles: React.FC = () => {
  const navigate = useNavigate();
  const [articles, setArticles] = useState<ArticleRequestModel[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const handleCreateArticleClick = () => {
    navigate("/authCreateArticle"); // Update this to your desired route
  };

  const handleArticleClick = (articleId: string | undefined) => {
    if (articleId) {
      navigate(`/articles/${articleId}`);
      
    } else {
      console.error("Invalid articleId. Cannot navigate.");
    }
  };


  useEffect(() =>{
    const fetchArticles = async () =>{
      try {
        const allArticles = await fetchAllsArticles();
        const articlesToReview = allArticles.filter(
            (article: ArticleRequestModel) => article.articleStatus === "PUBLISHED"
        );
        setArticles(articlesToReview);
    } catch (err) {
        setError("Failed to fetch articles. Please try again later.");
    } finally {
        setLoading(false);
    }
};
fetchArticles();
    }
  ,[]);

  if (loading) return <p>Loading trending articles...</p>;
  if (error) return <p>{error}</p>;

  
  return (
    <div className="container">
      <div className="row firstRowIntroduction">
        <div className="col-6">
        <h1>These are Your Articles</h1>
        </div>
        <div className="col-6">
        <button className="btn btn-primary mt-25 createButton" onClick={handleCreateArticleClick}>Create Article</button>
        </div>     
      </div>
      <div className="row">

      <div className="review-articles__list">
                    {articles.map((article) => (
                        <ArticleMainComponent
                            key={article.articleId}
                            title={article.title}
                            description={`Word Count: ${article.wordCount}`}
                            tags={article.tags}
                            onClick={() => {handleArticleClick(article.articleId)}}
                        />
                    ))}
                </div>

      </div>
      
      
    </div>
  );
};

export default  AutherYourArticles;