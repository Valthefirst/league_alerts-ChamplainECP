import React from "react";
import ArticleCard from "../../features/articles/components/ArticleCard";

export default function NBAArticlesPage(): JSX.Element {
  return (
    <div>
      <h1>NBA Articles</h1>
      <p>Welcome to the NBA Articles Page!</p>
      <ArticleCard />
    </div>
  );
}