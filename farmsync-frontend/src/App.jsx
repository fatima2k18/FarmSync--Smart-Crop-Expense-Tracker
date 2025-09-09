import axios from "axios";
import { useState } from "react";
import "./App.css";
import { useNavigate, Routes, Route } from "react-router-dom";

import AddCropPage from "./pages/AddCrop";
import CropPage from "./pages/CropPage";

function App() {
  const navigate = useNavigate();
  const [formType, setFormType] = useState("login");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("FARMER");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const url =
      formType === "login"
        ? "http://localhost:8080/api/auth/login"
        : "http://localhost:8080/api/auth/register";

    const requestBody =
      formType === "login"
        ? { email, password }
        : { name, email, password, role };

    try {
      const response = await axios.post(url, requestBody, {
        withCredentials: true,
      });

      if (formType === "login") {
        const { token, role } = response.data;

        localStorage.setItem("token", token);
        localStorage.setItem("role", role);
        setMessage("✅ Login successful!");

        axios
          .get(`http://localhost:8080/user/email/${email}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          })
          .then((res) => {
             console.log("👤 User fetched:", res.data); // ✅ log to confirm user data
            const userId = res.data.id;
            localStorage.setItem("userId", userId);
            console.log("📦 Stored userId in localStorage:", userId); // ✅ confirm it's set
            navigate("/crop");
          })
          .catch((err) => {
            console.error("Error fetching userId:", err);
            setMessage("⚠️ Login successful but failed to fetch user ID.");
            navigate("/crop");
          });
      } else {
        setMessage("🎉 Registration successful!");
      }
    } catch (error) {
      console.error(error);
      setMessage(
        error.response?.data?.message ||
          "❌ Something went wrong. Please try again."
      );
    }
  };

  return (
    <>
      <Routes>
        <Route
          path="/"
          element={
            <div className="app-container">
              <h1 className="title">🌾 FarmSync</h1>
              <p className="subtitle">Welcome to the Farmers' One-Stop Solution</p>

              <form className="form-card" onSubmit={handleSubmit}>
                <h2>{formType === "login" ? "Login" : "Register"}</h2>

                <input
                  type="email"
                  placeholder="Enter your email"
                  value={email}
                  required
                  onChange={(e) => setEmail(e.target.value)}
                />

                {formType === "register" && (
                  <input
                    type="text"
                    placeholder="Enter your name"
                    value={name}
                    required
                    onChange={(e) => setName(e.target.value)}
                  />
                )}

                <input
                  type="password"
                  placeholder="Enter your password"
                  value={password}
                  required
                  onChange={(e) => setPassword(e.target.value)}
                />

                {formType === "register" && (
                  <select value={role} onChange={(e) => setRole(e.target.value)}>
                    <option value="FARMER">Farmer</option>
                    <option value="ADMIN">Admin</option>
                    <option value="USER">User</option>
                  </select>
                )}

                <button type="submit">
                  {formType === "login" ? "Login" : "Register"}
                </button>

                <p
                  className="switch"
                  onClick={() =>
                    setFormType(formType === "login" ? "register" : "login")
                  }
                >
                  {formType === "login"
                    ? "Don't have an account? Register"
                    : "Already have an account? Login"}
                </p>

                {message && <p className="message">{message}</p>}
              </form>
            </div>
          }
        />
        <Route path="/crop" element={<CropPage />} />
        <Route path="/add-crop" element={<AddCropPage />} />
      </Routes>
    </>
  );
}

export default App;