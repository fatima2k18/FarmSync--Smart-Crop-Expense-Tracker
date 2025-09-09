import React, { useState } from 'react';
import { loginUser, registerUser } from '../api/auth';
import { useNavigate } from 'react-router-dom'; // ✅ Import navigate

const LoginRegister = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    role: 'FARMER',
  });
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  const navigate = useNavigate(); // ✅ Initialize router

  const handleChange = (e) => {
    setFormData(prev => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage('');

    try {
      if (isLogin) {
        const response = await loginUser({
          email: formData.email,
          password: formData.password,
        });

        // ✅ Save token and user ID
        localStorage.setItem("token", response.data.token);
        localStorage.setItem("userId", response.data.userId); // Optional

        setMessage('✅ Login successful!');
        navigate('/crops'); // ✅ Redirect to Crop page
      } else {
        const response = await registerUser(formData);
        setMessage('✅ Registration successful! Please login.');
        setIsLogin(true);
      }
    } catch (error) {
      console.log('Login/Register error:', error.response);
      setMessage(`❌ ${error.response?.data?.message || 'Something went wrong'}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '400px', margin: 'auto' }}>
      <h2>{isLogin ? 'Login' : 'Register'}</h2>

      <form onSubmit={handleSubmit}>
        {!isLogin && (
          <>
            <input
              name="name"
              placeholder="Name"
              value={formData.name}
              onChange={handleChange}
              required
              style={{ width: '100%', marginBottom: '10px', padding: '8px' }}
            />
            <select
              name="role"
              value={formData.role}
              onChange={handleChange}
              style={{ width: '100%', marginBottom: '10px', padding: '8px' }}
            >
              <option value="FARMER">FARMER</option>
              <option value="ADMIN">ADMIN</option>
            </select>
          </>
        )}
        <input
          name="email"
          type="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
          style={{ width: '100%', marginBottom: '10px', padding: '8px' }}
        />
        <input
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
          style={{ width: '100%', marginBottom: '10px', padding: '8px' }}
        />
        <button
          type="submit"
          disabled={loading}
          style={{
            width: '100%',
            padding: '10px',
            backgroundColor: '#007bff',
            color: 'white',
            border: 'none',
            cursor: 'pointer',
          }}
        >
          {loading ? 'Please wait...' : isLogin ? 'Login' : 'Register'}
        </button>
      </form>

      <button
        onClick={() => {
          setIsLogin(!isLogin);
          setMessage('');
        }}
        style={{
          marginTop: '10px',
          width: '100%',
          padding: '8px',
          backgroundColor: '#6c757d',
          color: 'white',
          border: 'none',
          cursor: 'pointer',
        }}
      >
        Switch to {isLogin ? 'Register' : 'Login'}
      </button>

      {message && (
        <div
          style={{
            marginTop: '15px',
            padding: '10px',
            backgroundColor: message.startsWith('✅') ? '#d4edda' : '#f8d7da',
            color: message.startsWith('✅') ? '#155724' : '#721c24',
            borderRadius: '5px',
          }}
        >
          {message}
        </div>
      )}
    </div>
  );
};
