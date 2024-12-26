import React, { useEffect, useState } from "react";
import ArticleCard from "../../features/articles/components/ArticleCard/ArticleCard";
import { useParams } from "react-router-dom";
import { ArticleRequestModel } from "features/articles/models/ArticleRequestModel";
import { fetchArticleByTag } from "features/articles/api/getAllArticleBySports";
import { searchArticlesByTagAndQuery } from "features/articles/api/searchArticles";
import ArticleSearchError from "../ArticlePages/ArticleSearchError/ArticleSearchError";
import "./ArticlePage.Module.css";

export default function NBAArticlesPage(): JSX.Element {
  const { tagName } = useParams<{ tagName: string }>();
  const [articles, setArticles] = useState<ArticleRequestModel[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [searchQuery, setSearchQuery] = useState<string>("");

  // Load all articles for the given tag when the page loads
  useEffect(() => {
    const loadArticles = async () => {
      try {
        setLoading(true);
        if (tagName) {
          const data = await fetchArticleByTag(tagName);
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
  }, [tagName]);

  // Perform search when the query changes
  useEffect(() => {
    const searchArticles = async () => {
      try {
        if (searchQuery.trim() === "") {
          // If the query is empty, show all articles for the tag
          const data = await fetchArticleByTag(tagName!);
          setArticles(data);
        } else {
          // Otherwise, fetch articles that match the search query
          const data = await searchArticlesByTagAndQuery(tagName!, searchQuery);
          setArticles(data);
        }
      } catch (err) {
        setError("Failed to fetch the search results");
        console.error(err);
      }
    };

    if (tagName) {
      searchArticles();
    }
  }, [searchQuery, tagName]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="container">
      <div className="row">
        <div className="col-5">
          <h1>{tagName} Articles</h1>
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
          <ArticleSearchError />
          //////////////////////////////////////

          ///// Add Article Error Component

          ///////////////////////////////////////
        )}
      </div>
    </div>
  );
}
