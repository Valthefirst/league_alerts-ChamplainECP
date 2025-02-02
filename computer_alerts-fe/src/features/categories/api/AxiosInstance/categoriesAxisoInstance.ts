import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "https://dolphin-app-sxvxi.ondigitalocean.app/api", // Adjust to match your backend
  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosInstance;
