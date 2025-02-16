import TrendingArticles from "../../features/articles/components/TrendingArticles/TrendingArticles";
import FetchAllArticlesBySport from "./Components/GetBySport";
import { Link } from "react-router-dom";
import styles from "./HomePage.module.css";

export default function HomePage(): JSX.Element {
  return (
    <>
      <div className="container">
        <div className="row">
          <TrendingArticles />
        </div>

        <hr />
        <div className="row">
          <div className={styles.sportsArticles}>
            <Link to="/articles/categories/NBA" className={styles.link}>
              NBA
            </Link>
            <FetchAllArticlesBySport prop="NBA" />
            <hr />
            <Link to="/articles/categories/NHL" className={styles.link}>
              NHL
            </Link>
            <FetchAllArticlesBySport prop="NHL" />
            <hr />
            <Link to="/articles/categories/NFL" className={styles.link}>
              NFL
            </Link>
            <FetchAllArticlesBySport prop="NFL" />
            <hr />
            <Link to="/articles/categories/UFC" className={styles.link}>
              UFC
            </Link>
            <FetchAllArticlesBySport prop="UFC" />
            <hr />
            <Link to="/articles/categories/MLB" className={styles.link}>
              MLB
            </Link>
            <FetchAllArticlesBySport prop="MLB" />
          </div>
        </div>
      </div>
    </>

    //Build Something that showcases the articles for each sport.
  );
}
