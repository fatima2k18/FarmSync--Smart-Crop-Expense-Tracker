import axios from "axios";
import { useState } from "react";
import "./App.css";

function App() {
  const [formType, setFormType] = useState("login"); // 'login' or 'register'
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const url =
      formType === "login"
        ? "http://localhost:8080/api/auth/login"
        : "http://localhost:8080/api/auth/register";

    try {
      const response = await axios.post(url, { email, password });

      if (formType === "login") {
        localStorage.setItem("token", response.data.token);
        setMessage("✅ Login successful!");
      } else {
        setMessage("🎉 Registration successful!");
      }
    } catch (error) {
      console.error(error);
      setMessage(
        error.response?.data?.message || "❌ Something went wrong. Please try again."
      );
    }
  };

  return (
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

        <input
          type="password"
          placeholder="Enter your password"
          value={password}
          required
          onChange={(e) => setPassword(e.target.value)}
        />

        <button type="submit">
          {formType === "login" ? "Login" : "Register"}
        </button>

        <p className="switch" onClick={() => setFormType(formType === "login" ? "register" : "login")}>
          {formType === "login"
            ? "Don't have an account? Register"
            : "Already have an account? Login"}
        </p>

        {message && <p className="message">{message}</p>}
      </form>
    </div>
  );
}

export default App;
// import axios from "axios";
// import { useState } from "react";
// import "./App.css"; // Make sure this path matches your actual CSS file

// function App() {
//   const [email, setEmail] = useState("");
//   const [password, setPassword] = useState("");
//   const [error, setError] = useState("");

//   const loginUser = async () => {
//     try {
//       const response = await axios.post("http://localhost:8080/api/auth/login", {
//         email: email,
//         password: password,
//       });

//       console.log("Login success:", response.data);
//       alert("Login successful!");
//       localStorage.setItem("token", response.data.token);
//     } catch (err) {
//       console.error("Login failed:", err.response?.data || err.message);
//       setError("Invalid credentials or server error.");
//     }
//   };

//   return (
//     <div className="login-container">
//       <h2>Welcome Back!</h2>
//       <p className="subtitle">Please log in to your account</p>
//       <input
//         type="email"
//         placeholder="Enter your email"
//         value={email}
//         onChange={(e) => setEmail(e.target.value)}
//         className="input-field"
//       />
//       <input
//         type="password"
//         placeholder="Enter your password"
//         value={password}
//         onChange={(e) => setPassword(e.target.value)}
//         className="input-field"
//       />
//       <button onClick={loginUser} className="login-button">
//         Login
//       </button>
//       {error && <p className="error-message">{error}</p>}
//     </div>
//   );
// }

// export default App;



// //import { useState } from 'react'
// // import reactLogo from './assets/react.svg'
// // import viteLogo from '/vite.svg'
// // import './App.css'

// // function App() {
// //   const [count, setCount] = useState(0)

// //   return (
// //     <>
// //       <div>
// //         <a href="https://vite.dev" target="_blank">
// //           <img src={viteLogo} className="logo" alt="Vite logo" />
// //         </a>
// //         <a href="https://react.dev" target="_blank">
// //           <img src={reactLogo} className="logo react" alt="React logo" />
// //         </a>
// //       </div>
// //       <h1>Vite + React</h1>
// //       <div className="card">
// //         <button onClick={() => setCount((count) => count + 1)}>
// //           count is {count}
// //         </button>
// //         <p>
// //           Edit <code>src/App.jsx</code> and save to test HMR
// //         </p>
// //       </div>
// //       <p className="read-the-docs">
// //         Click on the Vite and React logos to learn more
// //       </p>
// //     </>
// //   )
// // }

// // export default App
