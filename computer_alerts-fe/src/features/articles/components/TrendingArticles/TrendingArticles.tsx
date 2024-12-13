import React, { useEffect, useState } from "react";
import { fetchAllsArticles } from "../../api/getAllArticles";
import { ArticleRequestModel } from "../../models/ArticleRequestModel";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./TrendingArticles.css";

const TrendingArticles: React.FC = () => {
  const [trendingArticles, setTrendingArticles] = useState<
    ArticleRequestModel[]
  >([]);
  const [loading, setLoading] = useState<boolean>(true);
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchTrendingArticles = async () => {
      try {
        const data = await fetchAllsArticles();
        const sortedArticles = data.sort(
          (a, b) => b.requestCount - a.requestCount,
        );
        setTrendingArticles(sortedArticles.slice(0, 3));
      } catch (err) {
        console.error("Error fetching trending articles:", err);
        setError("Failed to fetch trending articles.");
      } finally {
        setLoading(false);
      }
    };

    fetchTrendingArticles();
  }, []);

  const patchArticleTrend = async (articleId: string) => {
    try {
      await axios.patch(`/articles/${articleId}`);
      console.log("Article trend updated successfully.");
    } catch (err) {
      console.error("Error updating article trend:", err);
    }
  };

  const handleArticleClick = (articleId: string | undefined) => {
    if (articleId) {
      navigate(`/articles/${articleId}`);
      patchArticleTrend(articleId);
    } else {
      console.error("Invalid articleId. Cannot navigate.");
    }
  };

  if (loading) return <p>Loading trending articles...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="trending-articles container">
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
                    alt={trendingArticles[0]?.title || "Big article image"}
                  />
                  <div className="card-body">
                    <h5
                      className="card-title"
                      onClick={() =>
                        handleArticleClick(trendingArticles[0].articleId)
                      }
                    >
                      {trendingArticles[0].title}
                    </h5>
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
                    alt={article.title || "Trending article image"}
                  />
                  <div className="card-body">
                    <h5
                      className="card-title"
                      onClick={() => handleArticleClick(article.articleId)}
                    >
                      {article.title}
                    </h5>
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
