import React, { useEffect, useState } from "react";
import { fetchAllsArticles } from "../../api/getAllArticles"; // Updated API import
import { ArticleRequestModel } from "../../models/ArticleRequestModel";
import "./TrendingArticles.css";

const TrendingArticles: React.FC = () => {
  const [trendingArticles, setTrendingArticles] = useState<ArticleRequestModel[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchTrendingArticles = async () => {
      try {
        const data = await fetchAllsArticles(); // Fetch all articles
        const sortedArticles = data.sort((a, b) => b.requestCount - a.requestCount); // Sort by requestCount
        setTrendingArticles(sortedArticles.slice(0, 3)); // Keep top 3 articles
      } catch (err) {
        console.error("Error fetching trending articles:", err);
        setError("Failed to fetch trending articles.");
      } finally {
        setLoading(false);
      }
    };

    fetchTrendingArticles();
  }, []); // Fetch articles on mount

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
            <div className="col-lg-8 col-md-6 mb-4 bigBoy">
              {trendingArticles[0] && (
                <div className="card h-100">
                  <img
                    src="https://www.mozaics.com/wp-content/uploads/2021/09/SQUARE-GREY-GLOSSY-FLAT-en-o2SweAjKc1RFyXJY.jpg"
                    className="card-img-top"
                    alt="Article Image"
                  />
                  <div className="card-body">
                    <h5 className="card-title">{trendingArticles[0].title}</h5>
                    <p className="card-text">Request Count: {trendingArticles[0].requestCount}</p>
                  </div>
                </div>
              )}
            </div>

            {/* Two smaller articles on the right */}
            <div className="col-lg-4 col-md-6 smallBoys">
              {trendingArticles.slice(1).map((article) => (
                <div key={article.articleId} className="card mb-4">
                  <img
                    src="https://www.mozaics.com/wp-content/uploads/2021/09/SQUARE-GREY-GLOSSY-FLAT-en-o2SweAjKc1RFyXJY.jpg"
                    className="card-img-top"
                    alt="Article Image"
                  />
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
