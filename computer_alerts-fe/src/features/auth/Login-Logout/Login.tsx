import React, { useState, useEffect } from "react";
import { AuthService } from "../Service/AuthService";
import axios from "axios";

// Decode JWT and return the payload
export const DecodeToken2 = (token: string) => {
  const base64Url = token.split(".")[1]; // Get the payload part
  const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/"); // Replace URL-safe chars
  const decodedPayload = JSON.parse(atob(base64)); // Decode base64 and parse as JSON
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

  // Fetch user info if authenticated
  useEffect(() => {
    if (isAuthenticated) {
      fetchUserInfo();
    }
  }, [isAuthenticated]);

  // Function to fetch user info from the backend after login
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
        console.log("Decoded Token:", decodedToken);

        // Check if the user signed in via Google
        if (userId && userId.indexOf("google-") === 0) {
          const goodAuthId = userId.replace(/\|/g, "%7C");
          console.log("Making fetch request for Google user:", goodAuthId);

          // Call your backend endpoint for Google Sign-In users
          const response = await fetch(
            `http://localhost:8080/api/create/${goodAuthId}/assign-role/Google`,
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

          const data = await response.json();
          console.log("User created/fetched:", data);
        }
        // Check if the user signed in via Facebook
        else if (userId && userId.indexOf("facebook-") === 0) {
          const goodAuthId = userId.replace(/\|/g, "%7C");
          console.log("Making fetch request for Facebook user:", goodAuthId);

          // Call your backend endpoint for Facebook Sign-In users
          const response = await fetch(
            `http://localhost:8080/api/create/${goodAuthId}/assign-role/Facebook`,
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

          const data = await response.json();
          console.log("User created/fetched:", data);
        }
      }
    } catch (error) {
      console.error("Error fetching user info:", error);
    }
  };

  // Login handler
  const handleLogin = async () => {
    try {
      console.log("Starting login process...");
      await authService.login();
      console.log("Login successful!");
      fetchUserInfo();

      // After successful login, set authentication state
      setIsAuthenticated(true);
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
        <button onClick={handleLogin}>Log In</button>
      ) : (
        <div>
          <button onClick={handleLogout}>Log Out</button>
        </div>
      )}
    </div>
  );
};

export default Login;
