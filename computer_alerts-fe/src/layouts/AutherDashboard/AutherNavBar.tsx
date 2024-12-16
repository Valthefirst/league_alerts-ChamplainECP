import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./AutherNavBar.css";
import { AppRoutePaths } from "../../shared/models/path.routes";
import { Navbar, Container, Nav } from "react-bootstrap";

export default function AuthorNavBar(): JSX.Element {
  return (
    <Navbar expand="lg" className="navbar">
      <Container>
        <Navbar.Brand
          as={Link}
          to={AppRoutePaths.AuthorHomePage}
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
              to={AppRoutePaths.AutherYourArticle}
              className="nav-link"
            >
              Your Articles
            </Nav.Link>
          </Nav>
          <Nav className="ms-auto">
            <Nav.Link
              as={Link}
              to={AppRoutePaths.AutherDrafts}
              className="nav-link"
            >
              Draft Articles
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
