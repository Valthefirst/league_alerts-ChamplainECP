import { useState, useEffect } from "react";
import "./ModifyAccountDetails.module.css";
import ReaderRequestDTO from "../models/ReaderRequestDTO";
import { DecodeToken } from "assets/DecodeToken";

const ModifyAccountDetails: React.FC = () => {
  const [formData, setFormData] = useState<ReaderRequestDTO>({
    emailAddress: "",
    firstName: "",
    lastName: "",
    auth0UserId: "",
  });
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [auth0UserId, setAuth0UserId] = useState<string | null>(null);

  // Extract auth0UserId from the access token
  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      const decodedToken = DecodeToken(token);
      if (decodedToken) {
        setAuth0UserId(decodedToken.sub); 
      }
    }
  }, []);

  // Fetch user data based on auth0UserId
  useEffect(() => {
    const fetchUserData = async () => {
      if (!auth0UserId) return; // Skip if auth0UserId is not available

      let goodAuth0User = auth0UserId.replace(/\|/g, "%7C");


      try {
        const response = await fetch(`http://localhost:8080/api/v1/readers/${goodAuth0User}`); // Replace with your API endpoint
        if (!response.ok) {
          throw new Error("Failed to fetch user data");
        }
        const userData: ReaderRequestDTO = await response.json();
        setFormData(userData); // Populate form with fetched data
      } catch (error) {
        setErrorMessage("Error fetching user data");
        console.error(error);
      }
    };

    fetchUserData();
  }, [auth0UserId]); // Fetch data when auth0UserId changes

  // Handle form field changes
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  // Handle form submission
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!auth0UserId) {
      setErrorMessage("User ID not found");
      return;
    }

    try {
      const response = await fetch(`/api/v1/readers/${auth0UserId}`, {
        method: "PUT", // or "PATCH"
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          firstName: formData.firstName,
          lastName: formData.lastName,
        }),
      });

      if (!response.ok) {
        throw new Error("Failed to update user data");
      }

      setSuccessMessage("User data updated successfully!");
      setErrorMessage(null);
    } catch (error) {
      setErrorMessage("Error updating user data");
      setSuccessMessage(null);
      console.error(error);
    }
  };

  return (
    <div className="container">
      <div className="row">
        <form onSubmit={handleSubmit}>
          {/* Email Address (Read-only) */}
          <div className="form-group">
            <label htmlFor="emailAddress">Email Address</label>
            <input
              type="email"
              id="emailAddress"
              name="emailAddress"
              value={formData.emailAddress}
              readOnly
              className="form-control"
            />
          </div>

          {/* First Name (Editable) */}
          <div className="form-group">
            <label htmlFor="firstName">First Name</label>
            <input
              type="text"
              id="firstName"
              name="firstName"
              value={formData.firstName}
              onChange={handleInputChange}
              className="form-control"
            />
          </div>

          {/* Last Name (Editable) */}
          <div className="form-group">
            <label htmlFor="lastName">Last Name</label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              value={formData.lastName}
              onChange={handleInputChange}
              className="form-control"
            />
          </div>

          {/* Update Button */}
          <button type="submit" className="btn btn-primary">
            Update
          </button>
        </form>

        {/* Success and Error Messages */}
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