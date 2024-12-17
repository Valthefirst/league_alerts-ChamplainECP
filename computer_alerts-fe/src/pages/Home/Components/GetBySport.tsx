import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { ArticleRequestModel } from "../../../features/articles/models/ArticleRequestModel";
import { fetchAllsArticles } from "features/articles/api/getAllArticles";
import ArticleMainComponent from "../../ArticlePages/ArticleMainComponent";

// Define props interface
interface FetchAllArticlesBySportProps {
  prop: string; // Prop used to filter articles
}

const FetchAllArticlesBySport: React.FC<FetchAllArticlesBySportProps> = ({ prop }) => {
  const navigate = useNavigate();
  const [articles, setArticles] = useState<ArticleRequestModel[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchArticles = async () => {
      try {
        const allArticles = await fetchAllsArticles();

        const articlesToReview = allArticles.filter(
          (article: ArticleRequestModel) =>
            article.articleStatus === "PUBLISHED" && article.tagsTag === prop
        );

        setArticles(articlesToReview);
      } catch (err) {
        setError("Failed to fetch articles. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchArticles();
  }, [prop]); // Re-run the effect if the prop changes

  const handleArticleClick = (id: string) => {
    navigate(`/article/${id}`);
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <>
      {articles.length > 0 ? (
        <div className="review-articles__list">
          {articles.map((article) => (
            <ArticleMainComponent
              key={article.articleId}
              imageURL={article.photoUrl}
              title={article.title}
              description={`Word Count: ${article.wordCount}`}
              tags={article.tags}
              onClick={() => handleArticleClick(article.articleId)}
            />
          ))}
        </div>
      ) : (
        <p>No articles found for this category.</p>
      )}
    </>
  );
};

export default FetchAllArticlesBySport;
