import "./App.css";
import { AppRoutePaths } from "./shared/models/path.routes";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AppNavBar from "./layouts/AppNavBar";
import HomePage from "./pages/Home/HomePage";
import ArticleDetails from "features/articles/components/ArticleDetails";
import NBAArticlesPage from "./pages/ArticlePages/NBAArticlePage";

// import LogoutButton from "./Logout";

function App(): JSX.Element {
  return (
    <div>
      <Router>
        <AppNavBar />
        <Routes>
          <Route path={AppRoutePaths.HomePage} element={<HomePage />} />
          <Route path="/articles/:id" element={<ArticleDetails />} />
          <Route path={AppRoutePaths.NBA} element={<NBAArticlesPage />} />
        </Routes>
      </Router>
      <h1>Hello</h1>
      {/* <ReaderList/> */}
    </div>
  );
}

export default App;
