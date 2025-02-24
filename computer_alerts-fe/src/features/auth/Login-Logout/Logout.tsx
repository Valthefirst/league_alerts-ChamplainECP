import React from "react";
import styles from "./Logout.module.css";
import { useNavigate } from "react-router-dom";

const LogoutButton: React.FC = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    sessionStorage.removeItem("access_token");
    navigate("/login");
    window.location.reload();
  };

  return (
    <button onClick={handleLogout} className={styles.button}>
      Logout
    </button>
  );
};

export default LogoutButton;
