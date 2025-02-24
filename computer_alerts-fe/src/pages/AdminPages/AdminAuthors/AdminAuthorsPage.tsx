import React from "react";
import { Link } from "react-router-dom";
import AuthorList from "features/authors/components/AuthorList";
import styles from "./AdminAuthor.module.css";

const AdminAuthorsPage: React.FC = () => {
  return (
    <div className="container">
      <div className="row">
        <Link to="/admin/createAuthor" className={styles.fontBold}>
          ➕ Create Author
        </Link>
        <AuthorList />
      </div>
    </div>
  );
};
export default AdminAuthorsPage;
