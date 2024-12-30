import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import ArticleForm from "pages/ArticlePages/CreateArticle/ArticleForm";
import styles from "./AutherCreateArticle.module.css";

const AutherCreateArticle: React.FC = () => {
  return (
    <div className="container">
      <div className="row">
        <h1 className={styles.headerTitle}>Create an Article</h1>
        <ArticleForm />
      </div>
    </div>
  );
};

export default AutherCreateArticle;
