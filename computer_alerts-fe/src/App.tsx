import "./App.css";
import { AppRoutePaths } from "./shared/models/path.routes";
import { BrowserRouter as Router, Routes, Route, useLocation } from "react-router-dom";
import AppNavBar from "./layouts/AppNavBar";
import AuthorNavBar from "./layouts/AuthorDashboard/AuthorNavBar";
import AutherYourArticles from "layouts/AuthorDashboard/AutherYourArticles";
import AuthorCreateArticle from "./layouts/AuthorDashboard/AutherCreateArticle"
import HomePage from "./pages/Home/HomePage";
import ArticleDetails from "features/articles/components/ArticleDetails/ArticleDetails";
import CreateUserForm from "./features/readers/components/CreateUser";
import AuthorPage from "pages/AuthorPages/AuthorPage";
import AuthorsPage from "pages/AuthorPages/AuthorsPage";
import ArticlesPage from "./pages/ArticlePages/ArticlePage";
import AuthHomePage from "layouts/AuthorDashboard/AuthHomePage";



const Navbar = () => {
  const location = useLocation();

  if (
    location.pathname.startsWith("/authDashboard") || 
    location.pathname.startsWith("/authHome") ||
    location.pathname.startsWith("/authCreateArticle")||
    location.pathname.startsWith("/authYourArticles")
  ) {
    return <AuthorNavBar />;
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
          <Route path={AppRoutePaths.CREATE_ACCOUNT} element={<CreateUserForm />} />
          <Route path="/articles/:id" element={<ArticleDetails />} />
          <Route path={AppRoutePaths.Authors} element={<AuthorsPage />} />
          <Route path="/authors/:authorId" element={<AuthorPage />} />
          <Route path={AppRoutePaths.ArticlesByTag} element={<ArticlesPage />} />
          
        
          <Route path={AppRoutePaths.AuthorHomePage} element={<AuthHomePage />} />
          <Route path={AppRoutePaths.AutherYourArticle} element={< AutherYourArticles/>} />
          <Route path="/authCreateArticle" element={<AuthorCreateArticle />} />
         

        </Routes>
      </Router>
    </div>
  );
}

export default App;