import "./App.css";
import { AppRoutePaths } from "./shared/models/path.routes";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  useLocation,
} from "react-router-dom";
import AppNavBar from "./layouts/AppNavBar";
import AuthorNavBar from "./layouts/AutherDashboard/AutherNavBar";
import HomePage from "./pages/Home/HomePage";
import ArticleDetails from "features/articles/components/ArticleDetails/ArticleDetails";
import CreateUserForm from "./features/readers/components/CreateUser";
import AuthorPage from "pages/AuthorPages/AuthorPage";
import AuthorsPage from "pages/AuthorPages/AuthorsPage";
import ArticlesPage from "./pages/ArticlePages/ArticlePage";
import AuthHomePage from "pages/AutherPages/AuthHomePage";
import AutherYourArticles from "pages/AutherPages/AutherYourParticles";
import AutherCreateArticle from "pages/AutherPages/AutherCreateArticle";
import AdminHomePage from "pages/AdminPages/Home-Page/AdminHomePage";
import AdminReviewArticles from "pages/AdminPages/Review-Articles/ReviewArticles";
import AdminNavBar from "./layouts/AdminDashboard/AdminNavBar";
import AdminArticleDetails from "./pages/AdminPages/AdminArticleDetails/AdminArticleDetails";
import ArtifleDrafts from "pages/AutherPages/ArticleDrafts/ArticleDrafts";
import EditArticlePage from "pages/ArticlePages/EditArticle/EditArticlePage";
import AdminAuthorsPage from "pages/AdminPages/AdminAuthors/AdminAuthorsPage";
import Footer from "assets/Footer/Footer";
import AdminCreateAuthor from "pages/AdminPages/AdminAuthors/AdminCreateAuthor/AdminCreateAuthor";
import UnAuthorized from "assets/UnAuthorizedMessage/UnAuthorized";

const Navbar = () => {
  const location = useLocation();

  if (
    location.pathname.startsWith("/authDashboard") ||
    location.pathname.startsWith("/authHome") ||
    location.pathname.startsWith("/authCreateArticle") ||
    location.pathname.startsWith("/authYourArticles") ||
    location.pathname.startsWith("/authYourDrafts")
  ) {
    return <AuthorNavBar />;
  } else if (
    location.pathname.startsWith("/adminHomePage") ||
    location.pathname.startsWith("/adminReviewArticles") ||
    location.pathname.startsWith("/adminAuthors")
  ) {
    return <AdminNavBar />;
  }

  return <AppNavBar />;
};

function App(): JSX.Element {
  return (
    <div>
      <Router>
        <Navbar />

        <Routes>
          <Route path={AppRoutePaths.HomePage} element={<HomePage />} />
          <Route
            path={AppRoutePaths.CREATE_ACCOUNT}
            element={<CreateUserForm />}
          />

          <Route
            path={AppRoutePaths.ArticlesByCategory}
            element={<ArticlesPage />}
          />

          <Route path="/articles/:id" element={<ArticleDetails />} />
          <Route path={AppRoutePaths.Authors} element={<AuthorsPage />} />
          <Route path="/authors/:authorId" element={<AuthorPage />} />
          <Route
            path={AppRoutePaths.EditArticle}
            element={<EditArticlePage />}
          />

          <Route
            path={AppRoutePaths.AuthorHomePage}
            element={<AuthHomePage />}
          />
          <Route
            path={AppRoutePaths.AutherYourArticle}
            element={<AutherYourArticles />}
          />
          <Route path="/authCreateArticle" element={<AutherCreateArticle />} />

          <Route
            path={AppRoutePaths.AuthorHomePage}
            element={<AdminHomePage />}
          />
          <Route
            path={AppRoutePaths.AdminAuthors}
            element={<AdminAuthorsPage />}
          />
          <Route
            path={AppRoutePaths.AdminReviewArticles}
            element={<AdminReviewArticles />}
          />
          <Route
            path={AppRoutePaths.AdminCreateAuthor}
            element={<AdminCreateAuthor />}
          />
          <Route path="/article/:articleId" element={<AdminArticleDetails />} />

          <Route
            path={AppRoutePaths.AutherDrafts}
            element={<ArtifleDrafts />}
          />
          <Route
            path="/unauthorized"
            element={<UnAuthorized />}
          />
          <Route path={AppRoutePaths.Authors} element={<AuthorPage />} />
        </Routes>
      </Router>

      <Footer />
    </div>
  );
}

export default App;
