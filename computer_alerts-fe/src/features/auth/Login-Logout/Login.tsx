import React, { useState } from "react";
import { AuthService } from "../Service/AuthService";
import axios from "axios";
import Cookies from "js-cookie";

const Login: React.FC = () => {
  const [authService] = useState(new AuthService());
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userInfo, setUserInfo] = useState<{
    name: string;
    roles: string[];
  } | null>(null);

  //
  const fetchUserInfo = async () => {
    try {
      const token = await authService.getToken();
      ////////////////////////////////////////////////////////////
      //
      // SOLVE CORSE ERROR
      //

      const response = await fetch("https://localhost:8080/api/userInfo", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        credentials: "include",
      });

      if (response.ok) {
        const userData = await response.json();
        console.log("User data:", userData);
        console.error("Failed to fetch user info:", response.statusText);
        Cookies.set("name", userData.name);
        Cookies.set("roles", JSON.stringify(userData.roles));
      }
    } catch (error) {
      console.error("Error fetching user info:", error);
    }
  };

  const handleLogin = async () => {
    try {
      console.log("Stage0 Complete");
      await authService.login();
      console.log("Stage1 Complete");
      setIsAuthenticated(true);
      console.log("Stage2 Complete");
      await fetchUserInfo();
      console.log("Stage3 Complete");
    } catch (error) {
      // Check if error is from axios or Auth0
      if (axios.isAxiosError(error)) {
        // Axios error Complication
        console.error("Axios Error:", error.response);
        console.error("Status:", error.response?.status); // 401, 500, etc.
        console.error("Headers:", error.response?.headers);
        console.error("Data:", error.response?.data);
      } else {
        // Auth0 or other errors Complication
        console.error("Login failed:", error);
      }

      // Log the full error for debugging purposes
      console.error("Error details:", error);
    }
  };

  const handleLogout = async () => {
    try {
      await authService.logout();
      setIsAuthenticated(false);
      setUserInfo(null);
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
          <h2>Welcome, {userInfo?.name}</h2>
          <p>Roles: {userInfo?.roles.join(", ")}</p>
          <button onClick={handleLogout}>Log Out</button>
        </div>
      )}
    </div>
  );
};

export default Login;
