import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getAuthorById } from "features/authors/api/getAuthorById";
import { Author } from "features/authors/model/Author";

const AuthorDetails: React.FC = () => {
  const { authorId } = useParams<{ authorId: string }>();
  const [author, setAuthor] = useState<Author | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchAuthor = async () => {
      try {
        if (authorId) {
          const authorData = await getAuthorById(authorId);
          setAuthor(authorData);
        } else {
          setError("Author ID is not defined");
        }
      } catch (err) {
        setError("Failed to fetch author details");
      } finally {
        setLoading(false);
      }
    };

    fetchAuthor();
  }, [authorId]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      {author ? (
        <div>
          <h1 style={{ textAlign: "center" }}>
            {author.firstName} {author.lastName}
          </h1>
          <h3>Author BIO</h3> {author.biography}
          <h2>Recent Articles</h2>
          {/* <ul>
            {author.articles.map((article) => (
              <li key={article.articleId}>{article.articleId}</li>
            ))}
          </ul> */}
        </div>
      ) : (
        <p>No author found</p>
      )}
    </div>
  );
};

export default AuthorDetails;
