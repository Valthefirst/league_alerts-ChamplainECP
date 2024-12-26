import errorMessage from "../../../assets/NoResultsfound2.jpg";
import styles from "./ArticleSearchError.module.css";

export default function SearchError(): JSX.Element {
  return (
    <>
      <div className="container">
        <div className="row justify-content-center">
          <img
            src={errorMessage}
            alt="Search Error Message"
            className={styles.Image}
          />
        </div>
        <div className="row">
          <div className={styles.centered}>
            <h1 className={styles.header}>No Articles Found</h1>
            <br />
          </div>
        </div>
      </div>
    </>
  );
}
