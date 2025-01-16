import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "http://dolphin-app-sxvxi.ondigitalocean.app/api/v1/authors",
  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosInstance;
