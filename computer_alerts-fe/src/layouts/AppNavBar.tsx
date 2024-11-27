import { Link} from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import { AppRoutePaths } from "../shared/models/path.routes";
import { Navbar, Container } from "react-bootstrap";
import Login from "../Login";
import CreateUserForm from "../features/readers/components/CreateUser";

export default function AppNavBar(): JSX.Element {
  return (
    <Navbar>
      <Container>
        <Navbar.Brand as={Link} to={AppRoutePaths.HomePage}>
          Home
        </Navbar.Brand>
        <Navbar.Brand as={Link} to={AppRoutePaths.NBA}>
          NBA
        </Navbar.Brand>
        <Navbar.Brand as={Link} to={AppRoutePaths.NHL}>
          NHL
        </Navbar.Brand>
        <Navbar.Brand as={Link} to={AppRoutePaths.NFL}>
          NFL
        </Navbar.Brand>
        <Navbar.Brand as={Link} to={AppRoutePaths.UFC}>
          UFC
        </Navbar.Brand>
        <Navbar.Brand as={Link} to={AppRoutePaths.MLB}>
          MLB
        </Navbar.Brand>
      </Container>
      <Login />
      <CreateUserForm/>
    </Navbar>
  );
}
