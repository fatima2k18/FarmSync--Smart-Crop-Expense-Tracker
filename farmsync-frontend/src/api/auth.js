import axios from './axiosConfig';

export const registerUser = async (userData) => {
  return await axios.post('/auth/register', userData);
};

export const loginUser = async (loginData) => {
  return await axios.post('/auth/login', loginData);
};