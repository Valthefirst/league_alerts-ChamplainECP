import axios from "axios";

const axiosInstance = axios.create({
  // baseURL: "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/interactions/comments",
  baseURL:
    "https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/interactions/comments",
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
});

export default axiosInstance;
