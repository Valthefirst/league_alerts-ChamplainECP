import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getAuthorById } from "features/authors/api/getAuthorById";
import { fetchArticleByArticleId } from "../../features/articles/api/getSpecificArticle";
import { Author } from "features/authors/model/Author";
import { ArticleRequestModel } from "features/articles/models/ArticleRequestModel";
import { Button } from "react-bootstrap";

const AuthorPage: React.FC = () => {
  const { authorId } = useParams<{ authorId: string }>();
  const [author, setAuthor] = useState<Author | null>(null);
  const [articles, setArticles] = useState<ArticleRequestModel[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchAuthorAndArticles = async () => {
      try {
        if (!authorId) {
          setError("Author ID is not defined");
          setLoading(false);
          return;
        }

        const authorData = await getAuthorById(authorId);
        setAuthor(authorData);

        // Check if the author has articles
        if (authorData.articles?.articleList) {
          // Fetch each article individually
          const articleFetchPromises = authorData.articles.articleList.map(
            (articleRef) => fetchArticleByArticleId(articleRef.articleId),
          );

          const articlesData = await Promise.all(articleFetchPromises);
          setArticles(articlesData);
        } else {
          setArticles([]);
        }
      } catch (err) {
        setError("Failed to fetch author details or articles");
      } finally {
        setLoading(false);
      }
    };

    fetchAuthorAndArticles();
  }, [authorId]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="container">
      {author ? (
        <div>
          <h1>
            {author.firstName} {author.lastName}
          </h1>
          <p>
            <h3>Author BIO</h3> {author.biography}
          </p>
          <Button id="btn-1">Contact Author</Button>
          <h2>Recent Articles</h2>
          {articles.length > 0 ? (
            <div>
              {articles.map((article, index) => (
                <div key={article.articleId}>
                  <div className="article-header">
                    <h3>{article.title}</h3>
                    <i className="bi bi-arrow-90deg-right"></i>
                  </div>
                  <p>{article.body}</p>
                  {index < articles.length - 1 && <hr className="separator" />}
                </div>
              ))}
            </div>
          ) : (
            <p>No articles found for this author.</p>
          )}
        </div>
      ) : (
        <p>No author found</p>
      )}
    </div>
  );
};

export default AuthorPage;
