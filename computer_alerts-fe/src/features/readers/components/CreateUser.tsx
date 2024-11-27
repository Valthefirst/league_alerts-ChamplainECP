import React, { useState } from "react";
import UserRequestDTO from "../models/UserRequestDTO";
import { AuthService } from "../../../AuthService";

const CreateUserForm: React.FC = () => {
  const [formData, setFormData] = useState<UserRequestDTO>({
    email: "",
    password: "",
    name: "",
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
    <div>
      <h1>Create a New User</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleInputChange}
            required
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleInputChange}
            required
          />
        </div>
        <div>
          <label>Name:</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleInputChange}
            required
          />
        </div>
        <button type="submit">Create User</button>
      </form>
      {successMessage && <p style={{ color: "green" }}>{successMessage}</p>}
      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
    </div>
  );
};

export default CreateUserForm;
