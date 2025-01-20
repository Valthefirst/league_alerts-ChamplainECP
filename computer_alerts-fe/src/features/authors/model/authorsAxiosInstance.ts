import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/authors",
  // baseURL: "http://localhost:8080/api/v1/authors",
  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosInstance;
