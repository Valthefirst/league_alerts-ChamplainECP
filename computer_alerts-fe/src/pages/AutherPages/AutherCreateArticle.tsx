import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleForm from "pages/ArticlePages/CreateArticle/ArticleForm";


const AutherCreateArticle: React.FC = () => {
  
  return (
    <div>
      <h1>Add an Article</h1>
      <ArticleForm/>
    </div>
  );
};

export default  AutherCreateArticle;