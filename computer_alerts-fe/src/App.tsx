import "./App.css";
import { AppRoutePaths } from "./shared/models/path.routes";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AppNavBar from "./layouts/AppNavBar";
import HomePage from "./pages/Home/HomePage";
import ArticleDetails from "features/articles/components/ArticleDetails";
import CreateUserForm from "./features/readers/components/CreateUser";
import AuthorPage from 'pages/AuthorPage';
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
          <Route path={AppRoutePaths.Authors} element={<AuthorPage />} />
          <Route path={AppRoutePaths.ArticlesByTag} element={<ArticlesPage />} />
        </Routes>
      </Router>
      <h1>Hello</h1>
      {/* <ReaderList/> */}
    </div>
  );
}

export default App;
