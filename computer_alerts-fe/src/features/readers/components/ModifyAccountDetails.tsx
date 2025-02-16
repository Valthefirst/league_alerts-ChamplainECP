import { useState, useEffect } from "react";
import styles from "./ModifyAccountDetails.module.css";
import ReaderRequestDTO from "../models/ReaderRequestDTO";
import { DecodeToken } from "assets/DecodeToken";

const ModifyAccountDetails: React.FC = () => {
  const [formData, setFormData] = useState<ReaderRequestDTO>({
    emailAddress: "reader2@gmail.com",
    firstName: "James",
    lastName: "Due",
    address: "2750 rue Jean-Drapeau",
  });
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [auth0UserId, setAuth0UserId] = useState<string | null>(null);

  // let URLDepTest = "http://localhost:8080/api/v1/";

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      const decodedToken = DecodeToken(token);
      if (decodedToken) {
        setAuth0UserId(decodedToken.sub);
      }
    }
  }, []);

  useEffect(() => {
    const fetchUserData = async () => {
      let URLDeploy = "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/";
      if (!auth0UserId) return;

      let goodAuth0User = auth0UserId.replace(/\|/g, "%7C");

      try {
        const response = await fetch(`${URLDeploy}readers/${goodAuth0User}`);
        if (!response.ok) {
          throw new Error("Failed to fetch user data");
        }
        const userData: ReaderRequestDTO = await response.json();
        setFormData(userData);
      } catch (error) {
        setErrorMessage("We are having issues changing you account details.");
        console.error(error);
      }
    };

    fetchUserData();
  }, [auth0UserId]);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // if (!auth0UserId) {
    //   setErrorMessage(
    //     "You do not currently exist in our system, please contact someone or create new account",
    //   );
    //   return;
    // }

    // let goodAuth0User = auth0UserId.replace(/\|/g, "%7C");

    try {
      let URLDeploy = "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/";
      // const response = await fetch(`${URLDeploy}readers/${goodAuth0User}`, {
      const response = await fetch(
        `${URLDeploy}readers/auth0%7C678920c84d650d4d4b64cee4`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            firstName: formData.firstName,
            lastName: formData.lastName,
            address: formData.address,
          }),
        },
      );

      if (!response.ok) {
        throw new Error("Failed to update user data");
      }

      setSuccessMessage("You account has been successfully updated !");
      setErrorMessage(null);
    } catch (error) {
      setErrorMessage(
        "It seems like there want error updating your account information. Try a different time.",
      );
      setSuccessMessage(null);
      console.error(error);
    }
  };

  return (
    <div className="container">
      <div className="row">
        <h1 style={{ textAlign: "center", marginBottom: "30px" }}>
          Modify Your Account
        </h1>
        <form onSubmit={handleSubmit} className={styles.form}>
          <div className={styles.formGroup}>
            <label htmlFor="emailAddress" className={styles.label}>
              Email Address
            </label>
            <input
              type="email"
              id="emailAddress"
              name="emailAddress"
              value={formData.emailAddress}
              readOnly
              className={styles.formControl}
            />
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="firstName" className={styles.label}>
              First Name
            </label>
            <input
              type="text"
              id="firstName"
              name="firstName"
              value={formData.firstName}
              onChange={handleInputChange}
              className={styles.formControl}
            />
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="lastName" className={styles.label}>
              Last Name
            </label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              value={formData.lastName}
              onChange={handleInputChange}
              className={styles.formControl}
            />
          </div>

          <div className={styles.formGroup}>
            <label htmlFor="address" className={styles.label}>
              Address
            </label>
            <input
              type="text"
              id="address"
              name="address"
              value={formData.address}
              onChange={handleInputChange}
              className={styles.formControl}
            />
          </div>

          <button type="submit" className={styles.btnPrimary}>
            Update
          </button>
        </form>

        {successMessage && (
          <div className="alert alert-success mt-3">{successMessage}</div>
        )}
        {errorMessage && (
          <div className="alert alert-danger mt-3">{errorMessage}</div>
        )}
      </div>
    </div>
  );
};

export default ModifyAccountDetails;
