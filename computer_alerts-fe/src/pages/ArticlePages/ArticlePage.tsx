import React, { useEffect, useState } from "react";
import ArticleCard from "../../features/articles/components/ArticleCard/ArticleCard";
import { useParams } from "react-router-dom";
import { ArticleRequestModel } from "features/articles/models/ArticleRequestModel";
// import ArticleSearchError from "../ArticlePages/ArticleSearchError/ArticleSearchError";
import { fetchArticleByCategory } from "features/articles/api/getAllArticleBySports";
import { searchArticlesByCategoryAndQuery } from "features/articles/api/searchArticles";
import "./ArticlePage.Module.css";

export default function ArticlesPage(): JSX.Element {
  const { category } = useParams<{ category: string }>();
  const [articles, setArticles] = useState<ArticleRequestModel[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [searchQuery, setSearchQuery] = useState<string>("");

  // Load all articles for the given tag when the page loads
  useEffect(() => {
    const loadArticles = async () => {
      try {
        setLoading(true);
        if (category) {
          const data = await fetchArticleByCategory(category);
          setArticles(data); // Load all articles
        }
      } catch (err) {
        setError("Failed to fetch the articles");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadArticles();
  }, [category]);

  // Perform search when the query changes
  useEffect(() => {
    const searchArticles = async () => {
      try {
        if (searchQuery.trim() === "") {
          // If the query is empty, show all articles for the tag
          const data = await fetchArticleByCategory(category!);
          setArticles(data);
        } else {
          // Otherwise, fetch articles that match the search query
          const data = await searchArticlesByCategoryAndQuery(
            category!,
            searchQuery,
          );
          setArticles(data);
        }
      } catch (err) {
        setError("Failed to fetch the search results");
        console.error(err);
      }
    };

    if (category) {
      searchArticles();
    }
  }, [searchQuery, category]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="container">
      <div className="row">
        <div className="col-5">
          <h1>{category} Articles</h1>
        </div>
        <div className="col-7">
          {/* Search Input */}
          <div className="search-bar-container">
            <input
              className="search-bar"
              type="text"
              placeholder="Search articles..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
          </div>
        </div>
      </div>

      <div className="row">
        {articles.length > 0 ? (
          <ArticleCard articles={articles} />
        ) : (
          // <ArticleSearchError />
          //////////////////////////////////////
          <div> </div>
          ///// Add Article Error Component

          ///////////////////////////////////////
        )}
      </div>
    </div>
  );
}
