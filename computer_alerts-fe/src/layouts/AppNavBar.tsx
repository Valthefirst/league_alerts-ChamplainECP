import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./AppNavBar.css";
import { AppRoutePaths } from "../shared/models/path.routes";
import { Navbar, Container, Nav, NavDropdown } from "react-bootstrap";
import { fetchAllsArticles } from "../features/categories/api/getAllCategories";
import LeagueImage from "../assets/LeagueAlertsImg.jpg"
import SVGIcon from "assets/MenuDetails/SVGIcon";

interface Category {
  id: string;
  categoryName: string;
}

export default function AppNavBar(): JSX.Element {
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    const getCategories = async () => {
      try {
        const data = await fetchAllsArticles();
        const formattedData: Category[] = data.map((category) => ({
          id: category.id,
          categoryName: category.categoryName,
        }));
        setCategories(formattedData);
      } catch (err) {
        console.error("Failed to fetch categories:", err);
      }
    };

    getCategories();
  }, []);

  const generateTagRoute = (category: string) =>
    AppRoutePaths.ArticlesByCategory.replace(":category", category);

    const handleLanguageChange = (lang: string) => {
      const googleTranslateElement = document.querySelector(
        ".goog-te-combo"
      ) as HTMLSelectElement;
  
      if (googleTranslateElement) {
        googleTranslateElement.value = lang;
        googleTranslateElement.dispatchEvent(new Event("change"));
      } else {
        console.error("Google Translate dropdown not found");
      }
    };


  return (
    <>
    <div id="google_translate_element" style={{ display: "none" }}></div>
    <Navbar expand="lg" className="navbar">
      <Container>
      <Navbar.Brand
          as={Link}
          to={AppRoutePaths.HomePage}
          className="navbar-brand"
        >
          <img src={LeagueImage} alt="Logo" className="logo" />
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav">
          <span>☰</span>
        </Navbar.Toggle>
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto">
            <Nav.Link as={Link} to={generateTagRoute("NBA")} className="nav-link">
              NBA
            </Nav.Link>
            <Nav.Link as={Link} to={generateTagRoute("NHL")} className="nav-link">
              NHL
            </Nav.Link>
            <Nav.Link as={Link} to={generateTagRoute("NFL")} className="nav-link">
              NFL
            </Nav.Link>
            <Nav.Link as={Link} to={generateTagRoute("UFC")} className="nav-link">
              UFC
            </Nav.Link>
            <Nav.Link as={Link} to={generateTagRoute("MLB")} className="nav-link">
              MLB
            </Nav.Link>

            {/* "Other" dropdown for dynamic categories */}
            {categories.length > 0 && (
              <NavDropdown title="Other" id="other-categories-dropdown">
                {categories.map((category) => (
                  <NavDropdown.Item key={category.id} as={Link} to={generateTagRoute(category.categoryName)}>
                    {category.categoryName}
                  </NavDropdown.Item>
                ))}
              </NavDropdown>
            )}

            {/* <Nav.Link as={Link} to={AppRoutePaths.SavedArticles} className="nav-link">
              Saved Articles
            </Nav.Link>
            <Nav.Link as={Link} to={AppRoutePaths.CREATE_ACCOUNT} className="nav-link">
              Sign Up/Login
            </Nav.Link>
            <Nav.Link as={Link} to={AppRoutePaths.Authors} className="nav-link">
              Your Authors
            </Nav.Link> */}
            
          
            <NavDropdown title="Language" id="language-dropdown">
                <NavDropdown.Item onClick={() => handleLanguageChange("en")}>
                  English
                </NavDropdown.Item>
                <NavDropdown.Item onClick={() => handleLanguageChange("fr")}>
                  French
                </NavDropdown.Item>
              </NavDropdown>
              <SVGIcon/>


          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
    </>
  );
}
