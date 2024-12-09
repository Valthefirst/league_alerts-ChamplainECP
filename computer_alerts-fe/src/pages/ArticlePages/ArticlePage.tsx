import React, { useEffect, useState } from "react";
import ArticleCard from "../../features/articles/components/ArticleCard/ArticleCard";
import { useParams } from "react-router-dom";
import { Article } from "features/articles/models/Article";
import { fetchArticleByTag } from "features/articles/api/getAllArticleBySports";

export default function NBAArticlesPage(): JSX.Element {
  const { tagName } = useParams<{ tagName: string }>();
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [articles, setArticles] = useState<Article[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadArticles = async () => {
      try {
        if (tagName) {
          const data = await fetchArticleByTag(tagName);
          console.log("Fetched articles:", data);
          setArticles(data);
        }
      } catch (err) {
        setError("Failed to fetch the articles");
        console.log("Failed to fetch articles", err);
      } finally {
        setLoading(false);
      }
    };

    loadArticles();
  }, [tagName]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      <h1>{tagName} Articles</h1>
      <p>Welcome to the {tagName} Articles Page!</p>
      <ArticleCard />
    </div>
  );
}
