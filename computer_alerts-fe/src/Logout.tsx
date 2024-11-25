import React from "react";
import { useNavigate } from "react-router-dom";

const LogoutButton: React.FC = () => {
  const navigate = useNavigate(); // Use `useNavigate` instead of `useHistory`

  // Function to handle logout
  const handleLogout = () => {
    // Clear the access token from localStorage or sessionStorage
    localStorage.removeItem("access_token"); // If stored in localStorage
    sessionStorage.removeItem("access_token"); // If stored in sessionStorage

    // Optionally, redirect the user to the login page after logout
    navigate("/login"); // Redirect to login page (adjust the path as necessary)
  };

  return <button onClick={handleLogout}>Logout</button>;
};

export default LogoutButton;
