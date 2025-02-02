import axios from "axios";

const axiosInstance = axios.create({
  // baseURL: "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1",
  baseURL: "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1",
  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosInstance;
