import React from "react";
import "./ArticleMainComponent.css";

interface ArticleMainComponentProps {
  imageURL: string;
  title: string;
  description: string;
  category: string;
  onClick: () => void;
}

const ArticleMainComponent: React.FC<ArticleMainComponentProps> = ({
  imageURL,
  title,
  description,
  category,
  onClick,
}) => {
  return (
    <div className="article-main-component" onClick={onClick}>
      <div className="article-main-component__image">
        {/* Hardcoded placeholder image */}
        <img
          src={imageURL}
          alt="Article Thumbnail"
          className="article-main-component__thumbnail"
        />
      </div>
      <div className="article-main-component__details">
        <h2 className="article-main-component__title">{title}</h2>
        <p className="article-main-component__description">{description}</p>
        <p className="article-main-component__tags">
          <strong>Category:</strong> {category}
        </p>
      </div>
    </div>
  );
};

export default ArticleMainComponent;
