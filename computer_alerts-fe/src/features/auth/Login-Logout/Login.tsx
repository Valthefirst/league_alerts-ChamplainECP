import React, { useState, useEffect } from "react";
import { AuthService } from "../Service/AuthService";
import styles from "./Login.module.css";
import axios from "axios";

// Decode JWT and return the payload
export const DecodeToken2 = (token: string) => {
  const base64Url = token.split(".")[1]; // Get the payload part
  const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
  const decodedPayload = JSON.parse(atob(base64));
  return decodedPayload;
};

const Login: React.FC = () => {
  const [authService] = useState(new AuthService());
  const [, setAuth0UserId] = useState<string | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(() => {
    // Check for accessToken in localStorage to set initial authentication state
    return !!localStorage.getItem("accessToken");
  });
  const [, setUserInfo] = useState<{
    name: string;
    roles: string[];
  } | null>(null);

  useEffect(() => {
    if (isAuthenticated) {
      // Fetch user info if already authenticated (access token exists)
      fetchUserInfo();
    }
  }, [isAuthenticated]);

  const fetchUserInfo = async () => {
    const token = localStorage.getItem("accessToken");
    if (!token) {
      console.error("No access token found.");
      return;
    }

    try {
      const decodedToken = DecodeToken2(token);
      if (decodedToken) {
        const userId = decodedToken.sub;

        if (userId == null) {
          console.log("NOOOOOOO  USER IDDD");
        } else {
          console.log("YESSSSS");
        }
        setAuth0UserId(userId);

        // Check if the user signed in via Google
        if (userId && userId.indexOf("google-") === 0) {
          const goodAuthId = userId.replace(/\|/g, "%7C");

          // Call your backend endpoint for Google Sign-In users
          const response = await fetch(
            `https://dolphin-app-sxvxi.ondigitalocean.app/api/create/${goodAuthId}/assign-role/Google`,
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
              },
              body: JSON.stringify({
                auth0UserId: userId,
              }),
            },
          );

          if (!response.ok) {
            throw new Error("Failed to create user in the database");
          }
        }
        // Check if the user signed in via Facebook
        else if (userId && userId.indexOf("facebook-") === 0) {
          const goodAuthId = userId.replace(/\|/g, "%7C");

          // Call your backend endpoint for Facebook Sign-In users
          const response = await fetch(
            `https://dolphin-app-sxvxi.ondigitalocean.app/api/create/${goodAuthId}/assign-role/Facebook`,
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
              },
              body: JSON.stringify({
                auth0UserId: userId,
              }),
            },
          );

          if (!response.ok) {
            throw new Error("Failed to create user in the database");
          }
        }
      }
    } catch (error) {
      console.error("Error fetching user info:", error);
    }
  };

  const handleLogin = async () => {
    try {
      await authService.login();
      fetchUserInfo();

      setIsAuthenticated(true);

      window.location.reload();
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("Axios Error:", error.response);
      } else {
        console.error("Login failed:", error);
      }
    }
  };

  // Logout handler
  const handleLogout = async () => {
    try {
      await authService.logout();
      localStorage.removeItem("accessToken"); // Remove access token
      setIsAuthenticated(false);
      setUserInfo(null); // Reset user info on logout
    } catch (error) {
      console.error("Logout failed", error);
    }
  };

  return (
    <div>
      {!isAuthenticated ? (
        <button onClick={handleLogin} className={styles.button}>
          Log In
        </button>
      ) : (
        <div>
          <button onClick={handleLogout} className={styles.button}>
            Log Out
          </button>
        </div>
      )}
    </div>
  );
};

export default Login;
