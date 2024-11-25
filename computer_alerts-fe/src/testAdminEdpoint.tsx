import axios from "axios";
import authTokenService from "./AuthService";

export const testAdminEdpoint = async () => {
  try {
    const token = await authTokenService.getToken();
    const response = await axios.get("http://localhost:8080/api/v1/readers", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data; // Return the data fetched from the API
  } catch (err) {
    console.error("Error fetching readers data", err);
    throw new Error("Failed to fetch readers data");
  }
};
