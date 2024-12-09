// src/components/ArticleDetail.tsx
import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { fetchArticleByArticleId } from '../api/getSpecificArticle';
import { Article } from '../models/Article';
import { Author } from 'features/authors/model/Author';
import { getAllAuthors } from 'features/authors/api/getAllAuthors';

const ArticleDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>(); // The articleId passed via route
  const [article, setArticle] = useState<Article | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [author, setAuthor] = useState<Author | null>(null);

  useEffect(() => {
    const loadArticleAndAuthors = async () => {
      try {
        if (id) {
          const articleData = await fetchArticleByArticleId(id);
          setArticle(articleData);
        }
        const authorsData: Author[] = await getAllAuthors();

        const foundAuthor = authorsData.find(author =>
          author.articles.articleList?.some(article => article.articleId === id)
        );
        setAuthor(foundAuthor || null);
      } catch (err) {
        setError('Failed to fetch data');
      } finally {
        setLoading(false);
      }
    };

    loadArticleAndAuthors();
  }, [id]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return article ? (
    <div>
      <h1>{article.title}</h1>
      <p>
        <strong>Body:</strong> {article.body}
      </p>
      <p>
        <strong>Tags:</strong> {article.tags}
      </p>
      <p>
        <strong>Status:</strong> {article.articleStatus}
      </p>
      <p>
        <strong>Word Count:</strong> {article.wordCount}
      </p>
      <p>
        <strong>Time Posted:</strong>{' '}
        {new Date(article.timePosted).toLocaleString()}
      </p>
      {author ? (
        <p>
          <strong>Author:</strong>{' '}
          <Link to={`/authors/${author.authorId}`}>
            {author.firstName} {author.lastName}
          </Link>
        </p>
      ) : (
        <p>No author found for this article</p>
      )}
    </div>
  ) : (
    <p>No article found.</p>
  );
};

export default ArticleDetails;
