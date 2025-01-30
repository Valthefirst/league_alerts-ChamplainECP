import { useState, useEffect } from "react";
import "./ModifyAccountDetails.module.css";
import ReaderRequestDTO from "../models/ReaderRequestDTO";
import { DecodeToken } from "assets/DecodeToken";

const ModifyAccountDetails: React.FC = () => {
  const [formData, setFormData] = useState<ReaderRequestDTO>({
    emailAddress: "",
    firstName: "",
    lastName: "",
    address: "",
    
  });
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [auth0UserId, setAuth0UserId] = useState<string | null>(null);

  let URLDeploy = "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/";

  let URLDepTest = "http://localhost:8080/api/v1/";


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
        const response = await fetch(`${URLDepTest}readers/${goodAuth0User}`); // Replace with your API endpoint
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

    if (!auth0UserId) {
      setErrorMessage("Reader Auth0UserID not found");
      return;
    }

    let goodAuth0User = auth0UserId.replace(/\|/g, "%7C");

    try {
      const response = await fetch(`${URLDepTest}readers/${goodAuth0User}`, {
        method: "PUT", 
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          firstName: formData.firstName,
          lastName: formData.lastName,
          address: formData.address
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

          <div className="form-group">
            <label htmlFor="address">Address</label>
            <input 
            type="text"
            id="address"
            name="address"
            value={formData.address}
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