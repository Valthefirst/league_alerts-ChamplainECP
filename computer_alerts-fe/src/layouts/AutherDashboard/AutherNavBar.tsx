import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./AutherNavBar.css";
import { AppRoutePaths } from "../../shared/models/path.routes";
import { Navbar, Container, Nav, NavDropdown } from "react-bootstrap";
import LeagueImage from "../../assets/LeagueAlertsImg.jpg";

export default function AuthorNavBar(): JSX.Element {
  return (
    <Navbar expand="lg" className="navbar">
      <Container>
        <Navbar.Brand
          as={Link}
          to={AppRoutePaths.AuthorHomePage}
          className="navbar-brand"
        >
          <img src={LeagueImage} alt="Logo" className="logo" />
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

          {/* Dropdown for "Others" */}
          <NavDropdown title="Others" id="others-dropdown">
            <NavDropdown.Item
              as={Link}
              to={AppRoutePaths.AddCategory}
              className="ms-auto"
            >
              Add New Category
            </NavDropdown.Item>
            {/* You can add more dropdown items here if needed */}
          </NavDropdown>
        </Navbar.Collapse>

        <Nav className="ms-auto">
          <Nav.Link
            as={Link}
            to={AppRoutePaths.CREATE_ACCOUNT}
            className="nav-link"
          >
            Sign Up / Login
          </Nav.Link>
        </Nav>
      </Container>
    </Navbar>
  );
}
