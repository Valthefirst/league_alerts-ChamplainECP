import axios from 'axios';

const axiosInstance = axios.create({
    // baseURL: 'https://dolphin-app-sxvxi.ondigitalocean.app/api/v1/reports/latest',
    baseURL: 'http://localhost:8080/api/v1/reports/latest',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
});

export default axiosInstance;