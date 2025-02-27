import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./AdminNavBar.css";
import { AppRoutePaths } from "../../shared/models/path.routes";
import { Navbar, Container, Nav, NavDropdown } from "react-bootstrap";
import LeagueImage from "../../assets/LeagueAlertsImg.jpg";

export default function AuthorNavBar(): JSX.Element {
  return (
    <Navbar expand="lg" className="navbar">
      <Container>
        <Navbar.Brand
          as={Link}
          to={AppRoutePaths.AdminHomePage}
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
            <Nav.Link
              as={Link}
              to={AppRoutePaths.AdminReports}
              className="nav-link"
            >
              Reports
            </Nav.Link>

            <NavDropdown title="Others" id="others-dropdown">
              <NavDropdown.Item as={Link} to={AppRoutePaths.AddCategory}>
                Add New Category
              </NavDropdown.Item>
              {/* You can add more dropdown items here if needed */}
            </NavDropdown>

            <Nav.Link
              as={Link}
              to={AppRoutePaths.CREATE_ACCOUNT}
              className="nav-link"
            >
              Sign Up / Login
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
