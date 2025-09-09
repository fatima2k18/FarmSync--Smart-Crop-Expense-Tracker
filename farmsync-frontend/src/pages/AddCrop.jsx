import React, { useState } from "react";
import axios from "../api/axiosConfig"; 
import { useNavigate } from "react-router-dom";

const AddCropPage = () => {
  const [formData, setFormData] = useState({
    name: "",
    season: "",
    startDate: "",
    endDate: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      console.log("🚀 Submitting crop:", formData);

      const response = await axios.post("/crops", formData);

      console.log("✅ Response from backend:", response.data);
      alert("✅ Crop added successfully!");
      navigate("/crop");
    } catch (error) {
      console.error(
        "❌ Error adding crop:",
        error?.response?.data || error.message
      );
      alert("❌ Something went wrong. Please try again.");
    }
  };

  return (
    <div className="form-container">
      <h2 className="form-heading">Add New Crop</h2>

      <form onSubmit={handleSubmit} className="form">
        <input
          type="text"
          name="name"
          placeholder="Crop Name"
          value={formData.name}
          onChange={handleChange}
          className="input-field"
          required
        />
        <input
          type="text"
          name="season"
          placeholder="Season (e.g., Kharif)"
          value={formData.season}
          onChange={handleChange}
          className="input-field"
          required
        />
        <input
          type="date"
          name="startDate"
          value={formData.startDate}
          onChange={handleChange}
          className="input-field"
          required
        />
        <input
          type="date"
          name="endDate"
          value={formData.endDate}
          onChange={handleChange}
          className="input-field"
          required
        />
        <button type="submit" className="submit-button">
          Add Crop
        </button>
      </form>
    </div>
  );
};

export default AddCropPage;