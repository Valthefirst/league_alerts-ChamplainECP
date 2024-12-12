import React from "react";
import "./ArticleMainComponent.css";

interface ArticleMainComponentProps {
    title: string;
    description: string;
    tags: string;
    onClick: () => void;
}

const ArticleMainComponent: React.FC<ArticleMainComponentProps> = ({
    title,
    description,
    tags,
    onClick,
}) => {
    return (
        <div className="article-main-component" onClick={onClick}>
            <div className="article-main-component__image">
                {/* Hardcoded placeholder image */}
                <img
                    src="https://via.placeholder.com/150"
                    alt="Article Thumbnail"
                    className="article-main-component__thumbnail"
                />
            </div>
            <div className="article-main-component__details">
                <h2 className="article-main-component__title">{title}</h2>
                <p className="article-main-component__description">{description}</p>
                <p className="article-main-component__tags">
                    <strong>Tags:</strong> {tags}
                </p>
            </div>
        </div>
    );
};

export default ArticleMainComponent;
