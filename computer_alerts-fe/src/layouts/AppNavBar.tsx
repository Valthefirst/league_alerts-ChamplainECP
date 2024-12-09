import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './AppNavBar.css';
import { AppRoutePaths } from '../shared/models/path.routes';
import { Navbar, Container, Nav } from 'react-bootstrap';
// import CreateUserForm from "../features/readers/components/CreateUser";

export default function AppNavBar(): JSX.Element {

  const generateTagRoute = (tagName: string) => AppRoutePaths.ArticlesByTag.replace(":tagName", tagName);

  return (
    <Navbar expand="lg" className="navbar">
      <Container>
        <Navbar.Brand
          as={Link}
          to={AppRoutePaths.HomePage}
          className="navbar-brand"
        >
          Home
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
            <Nav.Link as={Link} to={AppRoutePaths.Authors} className="nav-link">
              Your Authors
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
        {/* <CreateUserForm /> */}
      </Container>
    </Navbar>
  );
}
