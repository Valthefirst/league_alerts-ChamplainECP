import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleForm from "pages/ArticlePages/CreateArticle/ArticleForm";
import styles from "./AutherCreateArticle.module.css";

const AutherCreateArticle: React.FC = () => {
  return (
    <div className={styles.articleFormContainer}>
      <div className="row">
      
        <ArticleForm />
      </div>
    </div>
  );
};

export default AutherCreateArticle;
