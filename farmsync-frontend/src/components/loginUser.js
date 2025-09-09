
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginUser } from '../services/authService';
import axios from '../services/axiosConfig';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
    const response = await loginUser({ email, password });

    
    // Store token & role immediately after a successful login
    localStorage.setItem("token", response.token);
    localStorage.setItem("role", response.role);

    // const token = response.token;
    // const role = response.role;

    // localStorage.setItem("token", token);
    // localStorage.setItem("role", role);

    // ✅ Decode token to extract userId
    // const decoded = jwtDecode(token); // should contain userId field
    // const userId = decoded.userId;

    // if (!userId) {
    //   throw new Error("User ID not found in token");
    // }

    // localStorage.setItem("userId", userId);

    alert("✅ Login successful!");
    navigate("/crop");
  } catch (err) {
    console.error("❌ Login error:", err);
    setError(err.message || "Login failed. Please try again.");
  }
  };

  return (
    <div style={{ maxWidth: '400px', margin: 'auto', padding: '20px' }}>
      <h2>🌾 Login to FarmSync</h2>

      <input
        type="email"
        placeholder="Email"
        value={email}
        required
        onChange={(e) => setEmail(e.target.value)}
        style={{ width: '100%', marginBottom: '10px', padding: '8px' }}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        required
        onChange={(e) => setPassword(e.target.value)}
        style={{ width: '100%', marginBottom: '10px', padding: '8px' }}
      />

      <button
        onClick={handleLogin}
        style={{
          width: '100%',
          padding: '10px',
          backgroundColor: '#4CAF50',
          color: 'white',
          border: 'none',
          cursor: 'pointer',
        }}
      >
        Login
      </button>

      {error && <p style={{ color: 'red', marginTop: '10px' }}>{error}</p>}
    </div>
  );
}

export default Login;

