import React, { useEffect, useState } from "react";
import { AuthService } from "features/auth/Service/AuthService";
import AuthorRequestDTO from "features/authors/model/AuthorRequestDTO";
import styles from "./AdminCreateAuthor.module.css"
import LeagueImage from "../../../../assets/LeagueAlertsImg.jpg"

const AdminCreateAuthor : React.FC = () =>{
    const [formData, setFormData] = useState<AuthorRequestDTO>({
        emailAddress: "",
        firstName: "",
        lastName: "",
        password: "",
        // biography: "",
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
          const response = await authService.createAuthor(formData);
          setSuccessMessage(`User created successfully! ID: ${response.user_id}`);
        } catch (error: any) {
          setErrorMessage(error.message || "Failed to create user");
        }
      };
    
      return (
        <div className={styles.container}>
          <h1 className={styles.heading}>Create Author</h1>
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
              {/* <div className={styles.inputGroup}>
                <label className={styles.label}></label>
                <input
                  placeholder="Enter the authors Biography"
                  className={styles.input}
                  type="biography"
                  name="biography"
                  value={formData.biography}
                  onChange={handleInputChange}
                  required
                />
              </div> */}
              <button className={styles.button} type="submit">
                Create User
              </button>
            </form>
          </div>
          {successMessage && (
            <p className={styles.successMessage}>{successMessage}</p>
          )}
          {errorMessage && <p className={styles.errorMessage}>{errorMessage}</p>}
    
          
        </div>
      );
    };
export default AdminCreateAuthor;