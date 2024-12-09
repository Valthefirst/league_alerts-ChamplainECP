import React, { useEffect, useState } from "react";
import { fetchArticleByTag } from "../../api/getAllArticleBySports";
import { Article } from "../../models/Article";
import "./TrendingArticles.css"; 

interface TrendingArticlesProps {
  tagName: string; // Accept tagName as a prop
}

const TrendingArticles: React.FC<TrendingArticlesProps> = ({ tagName }) => {
  const [trendingArticles, setTrendingArticles] = useState<Article[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchTrendingArticles = async () => {
      try {
        const data = await fetchArticleByTag(tagName); // Use tagName passed as a prop
        const sortedArticles = data.sort((a, b) => b.requestCount - a.requestCount); // Sort by requestCount
        setTrendingArticles(sortedArticles.slice(0, 3)); // Top 3
      } catch (err) {
        console.error("Error fetching trending articles:", err);
        setError("Failed to fetch trending articles.");
      } finally {
        setLoading(false);
      }
    };

    fetchTrendingArticles();
  }, [tagName]); // Re-fetch when tagName changes

  if (loading) return <p>Loading trending articles...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="trending-articles container">
      <h3>Trending Articles</h3>
      <div className="row">
        {trendingArticles.length === 0 ? (
          <p>No trending articles found.</p>
        ) : (
          <>
            {/* Big article on the left */}
            <div className="col-lg-8 col-md-6 mb-4">
              <div className="card h-100">
                <div className="card-body">
                  <h5 className="card-title">{trendingArticles[0].title}</h5>
                  <p className="card-text">Request Count: {trendingArticles[0].requestCount}</p>
                </div>
              </div>
            </div>

            {/* Two smaller articles on the right */}
            <div className="col-lg-4 col-md-6">
              {trendingArticles.slice(1).map((article) => (
                <div key={article.articleId} className="card mb-4">
                  <div className="card-body">
                    <h5 className="card-title">{article.title}</h5>
                    <p className="card-text">Request Count: {article.requestCount}</p>
                  </div>
                </div>
              ))}
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default TrendingArticles;
