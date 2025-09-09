import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080/api', // adjust if different
  headers: {
    'Content-Type': 'application/json',
  },
     withCredentials: true, // 👈 ADD THIS
});

// ✅ Add Authorization token to each request
axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});


export default axiosInstance;