import React, { useState } from "react";
import axios from "axios";

const CropForm = () => {
  const [formData, setFormData] = useState({
    name: "",
    type: "",
    quantity: "",
  });

  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!token || !userId) {
      alert("⚠️ Missing user session. Please log in again.");
      return;
    }

    try {
      const payload = {
        ...formData,
        userId: Number(userId),
      };

      await axios.post("http://localhost:8080/api/crops", payload, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      alert("Crop added successfully!");
      setFormData({ name: "", type: "", quantity: "" });
    } catch (error) {
      console.error("Error adding crop:", error.response?.data || error.message);
      alert("❌ Error adding crop. Please try again.");
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ maxWidth: "400px", margin: "auto", padding: "20px", border: "1px solid #ccc", borderRadius: "8px" }}>
      <h2 style={{ marginBottom: "15px" }}>Add New Crop</h2>

      <input
        type="text"
        name="name"
        placeholder="Crop Name"
        value={formData.name}
        onChange={handleChange}
        style={{ width: "100%", padding: "8px", marginBottom: "10px" }}
        required
      />
      <input
        type="text"
        name="type"
        placeholder="Crop Type"
        value={formData.type}
        onChange={handleChange}
        style={{ width: "100%", padding: "8px", marginBottom: "10px" }}
        required
      />
      <input
        type="number"
        name="quantity"
        placeholder="Quantity"
        value={formData.quantity}
        onChange={handleChange}
        style={{ width: "100%", padding: "8px", marginBottom: "10px" }}
        required
      />

      <button
        type="submit"
        style={{ backgroundColor: "#4CAF50", color: "white", padding: "10px 15px", border: "none", borderRadius: "4px", cursor: "pointer" }}
      >
        Add Crop
      </button>
    </form>
  );
};

export default CropForm;