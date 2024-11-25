import axios from "axios";
import { AuthService } from "./AuthService";

const authService = new AuthService();

// Create an axios instance with base config
const AxiosInstance = axios.create({
  baseURL: "http://localhost:8080/api",
});

// Request interceptor to add token to every request
AxiosInstance.interceptors.request.use(
  async (config) => {
    const token = await authService.getToken();
    config.headers.Authorization = `Bearer ${token}`;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

export default AxiosInstance;
