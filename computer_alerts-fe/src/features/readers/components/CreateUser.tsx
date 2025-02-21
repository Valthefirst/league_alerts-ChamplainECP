import React, { useState } from "react";
import UserRequestDTO from "../models/UserRequestDTO";
import { AuthService } from "features/auth/Service/AuthService";
import styles from "../components/CreateUser.module.css";
import Loging from "../../../features/auth/Login-Logout/Login";
import LeagueImage from "assets/LeagueAlertsImg.jpg";

const CreateUserForm: React.FC = () => {
  const [formData, setFormData] = useState<UserRequestDTO>({
    emailAddress: "",
    firstName: "",
    lastName: "",
    password: "",
    connection: "Username-Password-Authentication",
  });
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const authService = new AuthService();

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSuccessMessage(null);
    setErrorMessage(null);
    try {
      const response = await authService.createUser(formData);
      setSuccessMessage(`User created successfully! ID: ${response.user_id}`);
    } catch (error: any) {
      setErrorMessage(error.message || "Failed to create user");
    }
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.heading}>Sign Up</h1>
      <div className={styles.formContainer}>
        <div className={styles.imgContainer}>
          <img src={LeagueImage} alt="League Alerts" className={styles.img} />
        </div>
        <form onSubmit={handleSubmit} className={styles.form}>
          <div className={styles.inputGroup}>
            <label className={styles.label}></label>
            <input
              placeholder="Enter your email"
              className={styles.input}
              type="emailAddress"
              name="emailAddress"
              value={formData.emailAddress}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className={styles.inputGroup}>
            <label className={styles.label}></label>
            <input
              placeholder="Enter your first name"
              className={styles.input}
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className={styles.inputGroup}>
            <label className={styles.label}></label>
            <input
              placeholder="Enter your last name"
              className={styles.input}
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className={styles.inputGroup}>
            <label className={styles.label}></label>
            <input
              placeholder="Enter your password"
              className={styles.input}
              type="password"
              name="password"
              value={formData.password}
              onChange={handleInputChange}
              required
            />
          </div>
          <button className={styles.button} type="submit">
            Sign up
          </button>
        </form>
      </div>
      {successMessage && (
        <p className={styles.successMessage}>{successMessage}</p>
      )}
      {errorMessage && <p className={styles.errorMessage}>{errorMessage}</p>}

      <Loging />
    </div>
  );
};

export default CreateUserForm;
