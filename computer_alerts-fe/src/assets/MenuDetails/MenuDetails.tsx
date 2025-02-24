import { useEffect, useState } from "react";
import { AppRoutePaths } from "shared/models/path.routes";
import styles from "./MenuDetails.module.css";
import { DecodeToken } from "assets/DecodeToken";
import Logout from "../../features/auth/Login-Logout/Logout"; // Importing Logout component

const MenuDetails: React.FC = () => {
  const [, setErrorMessage] = useState<string | null>(null);
  const [auth0UserId, setAuth0UserId] = useState<string | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("accessToken");

    if (token) {
      setIsAuthenticated(true);
      const decodedToken = DecodeToken(token);
      if (decodedToken) {
        setAuth0UserId(decodedToken.sub);
      }
    }
  }, []);

  // let URLDepHost = "http://localhost:8080/api/v1/";

  useEffect(() => {
    const fetchUserData = async () => {
      if (!auth0UserId) return;

      let goodAuth0User = auth0UserId.replace(/\|/g, "%7C");

      try {
        let URLDepTest = "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/";
        const response = await fetch(`${URLDepTest}readers/${goodAuth0User}`);
        if (!response.ok) {
          throw new Error("Failed to fetch user data");
        }
        // const userData: ReaderRequestDTO = await response.json();
      } catch (error) {
        setErrorMessage("We are having issues getting your account details.");
        console.error(error);
      }
    };

    fetchUserData();
  }, [auth0UserId]);

  return (
    <div
      style={{
        position: "absolute",
        top: "60px",
        width: "220px",
        left: "-160px",
        right: "auto",
        backgroundColor: "white",
        border: "3px solid #ddd",
        borderRadius: "8px",
        boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
        padding: "10px",
        zIndex: 1000,
      }}
    >
      <p style={{ textAlign: "center", fontWeight: "bold" }}>Welcome!</p>
      <ul style={{ listStyle: "none", margin: 0, padding: 0 }}>
        <li>
          <a
            className={styles.listItems}
            href={AppRoutePaths.SavedArticles}
            style={{ textDecoration: "none", color: "black" }}
          >
            Saved Articles
          </a>
        </li>
        {/* <li>
          <a href={AppRoutePaths.Authors} className={styles.listItems}>
            See All Authors
          </a>
        </li> */}
        <li>
          <a
            href={AppRoutePaths.ModifyAccountDetails}
            style={{ textDecoration: "none", color: "black" }}
          >
            Modify Account
          </a>
        </li>
        <li>
          {isAuthenticated ? (
            <Logout /> // Show Logout component if authenticated
          ) : (
            <a
              href={AppRoutePaths.CREATE_ACCOUNT}
              style={{ textDecoration: "none", color: "black" }}
            >
              Create Account / Sign In
            </a>
          )}
        </li>
      </ul>
    </div>
  );
};

export default MenuDetails;
