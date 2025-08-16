import React, { useState } from 'react';
import { loginUser, registerUser } from '../api/auth';

const LoginRegister = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    role: 'FARMER', // ✅ default to FARMER
  });
  const [message, setMessage] = useState('');

  const handleChange = (e) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = isLogin
        ? await loginUser({ email: formData.email, password: formData.password })
        : await registerUser(formData);
      setMessage(JSON.stringify(response.data, null, 2));
    } catch (error) {
      setMessage(error.response?.data?.message || 'Something went wrong');
    }
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>{isLogin ? 'Login' : 'Register'} User</h2>
      <form onSubmit={handleSubmit}>
        {!isLogin && (
          <>
            <input
              name="name"
              placeholder="Name"
              value={formData.name}
              onChange={handleChange}
              required
            /><br />
            <select name="role" value={formData.role} onChange={handleChange}>
              <option value="FARMER">FARMER</option>
              <option value="ADMIN">ADMIN</option>
            </select><br />
          </>
        )}
        <input
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
        /><br />
        <input
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
        /><br />
        <button type="submit">{isLogin ? 'Login' : 'Register'}</button>
      </form>
      <button onClick={() => setIsLogin(!isLogin)} style={{ marginTop: '10px' }}>
        Switch to {isLogin ? 'Register' : 'Login'}
      </button>
      <pre style={{ background: '#f4f4f4', padding: 10 }}>{message}</pre>
    </div>
  );
};

export default LoginRegister;