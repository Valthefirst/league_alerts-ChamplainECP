import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./AdminNavBar.css";
import { AppRoutePaths } from "../../shared/models/path.routes";
import { Navbar, Container, Nav } from "react-bootstrap";

export default function AuthorNavBar(): JSX.Element {
  return (
    <Navbar expand="lg" className="navbar">
      <Container>
        <Navbar.Brand
          as={Link}
          to={AppRoutePaths.AdminHomePage}
          className="navbar-brand"
        >
          Home
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav">
          <span>☰</span>
        </Navbar.Toggle>
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto">
            <Nav.Link
              as={Link}
              to={AppRoutePaths.AdminAuthors}
              className="nav-link"
            >
              AdminAuthors
            </Nav.Link>
            <Nav.Link
              as={Link}
              to={AppRoutePaths.AdminReviewArticles}
              className="nav-link"
            >
              Review Pending Articles
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
