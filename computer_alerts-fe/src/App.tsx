import "./App.css";
import { AppRoutePaths } from "./shared/models/path.routes";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AppNavBar from "./layouts/AppNavBar";
import HomePage from "./pages/Home/HomePage";
import ArticleDetails from "features/articles/components/ArticleDetails/ArticleDetails";
import CreateUserForm from "./features/readers/components/CreateUser";
import AuthorPage from "pages/AuthorPages/AuthorPage";
import AuthorsPage from "pages/AuthorPages/AuthorsPage";
import ArticlesPage from "./pages/ArticlePages/ArticlePage";

function App(): JSX.Element {
  return (
    <div>
      <Router>
        <AppNavBar />
        <Routes>
          <Route path={AppRoutePaths.HomePage} element={<HomePage />} />
          <Route
            path={AppRoutePaths.CREATE_ACCOUNT}
            element={<CreateUserForm />}
          />

          <Route path="/articles/:id" element={<ArticleDetails />} />
          <Route path={AppRoutePaths.Authors} element={<AuthorsPage />} />
          <Route path="/authors/:authorId" element={<AuthorPage />} />
          <Route
            path={AppRoutePaths.ArticlesByTag}
            element={<ArticlesPage />}
          />
          <Route path={AppRoutePaths.Authors} element={<AuthorPage />} />
          <Route
            path={AppRoutePaths.ArticlesByTag}
            element={<ArticlesPage />}
          />
        </Routes>
      </Router>
      {/* <ReaderList/> */}
    </div>
  );
}

export default App;
