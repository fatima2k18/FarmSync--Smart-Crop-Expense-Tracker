import axios from "axios";
import { useState } from "react";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const loginUser = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/auth/login", {
        email: email,
        password: password,
      });

      console.log("Login success:", response.data);
      alert("Login successful!");

      // Save token (optional)
      localStorage.setItem("token", response.data.token);

    } catch (err) {
      console.error("Login failed:", err.response?.data || err.message);
      setError("Invalid credentials or server error.");
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <input
        type="email"
        placeholder="Email"
        onChange={(e) => setEmail(e.target.value)}
      /><br />
      <input
        type="password"
        placeholder="Password"
        onChange={(e) => setPassword(e.target.value)}
      /><br />
      <button onClick={loginUser}>Login</button>

      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}

export default Login;