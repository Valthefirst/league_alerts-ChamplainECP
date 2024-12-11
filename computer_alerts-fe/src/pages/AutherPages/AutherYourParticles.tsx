import React from "react";
import { useNavigate } from "react-router-dom";

const AutherYourArticles: React.FC = () => {
  const navigate = useNavigate();

  const handleCreateArticleClick = () => {
    navigate("/authCreateArticle"); // Update this to your desired route
  };
  
  return (
    <div>
      <h1>HELLO</h1>
      <button onClick={handleCreateArticleClick}>Create Article</button>
    </div>
  );
};

export default  AutherYourArticles;