import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
//import axios from "axios";
import axiosInstance from "../api/axiosConfig";

function CropPage() {
  const [crops, setCrops] = useState([]);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      navigate("/");
      return;
    }

    const fetchCrops = async () => {
      try {
           const response = await axiosInstance.get("/crops/my-crops"); // ✅ Only farmer's crops
        setCrops(response.data);
      } catch (error) {
        console.error("Error fetching crops", error);
      }
    };

     const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this crop?")) return;
    try {
      await axiosInstance.delete(`/crops/${id}`);
      alert("Crop deleted successfully!");
      fetchCrops(); // Refresh list
    } catch (error) {
      console.error("Error deleting crop", error);
      alert("Failed to delete crop");
    }
  };

   const handleEdit = (id) => {
    navigate(`/edit-crop/${id}`); // ✅ Go to edit page
  };

   const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };


    fetchCrops();
  }, [navigate, token]);

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };
    return (
    <div style={{ padding: "20px" }}>
      <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "15px" }}>
        <h1 style={{ color: "black" }}>🌾 Your Crops</h1>
        <div>
          <button
            onClick={() => navigate("/add-crop")}
            style={{ backgroundColor: "green", color: "white", padding: "8px 12px", marginRight: "10px" }}
          >


            ➕ Add Crop
          </button>
          <button
            onClick={handleLogout}
            className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
          >
            🚪 Logout
          </button>
        </div>
      </div>

      {crops.length === 0 ? (
        <p>No crops found.</p>
      ) : (
      <table border="1" cellPadding="8" cellSpacing="0" width="100%">
        <thead>
  <tr style={{ backgroundColor: "#8a5b5bff" }}>
    <th>Name</th>
    <th>Season</th>
    <th>Duration</th>
    <th>Actions</th>
  </tr>
</thead>
<tbody>
  {crops.map((crop) => (
    <tr key={crop.id}>
      <td style={{ color: "#2E8B57"}}>{crop.name}</td>
      <td style={{ color: "#2E8B57"  }}>{crop.season}</td>
      <td>
        <span style={{ color: "green" }}>
          {new Date(crop.startDate).toLocaleDateString()} -{" "}
          {new Date(crop.endDate).toLocaleDateString()}
        </span>
      </td>
      <td>
        <button
          onClick={() => handleEdit(crop.id)}
          style={{
            backgroundColor: "blue",
            color: "#fff",
            padding: "5px 10px",
            marginRight: "5px",
          }}
        >
          ✏ Edit
        </button>
        <button
          onClick={() => handleDelete(crop.id)}
          style={{ backgroundColor: "red", color: "#fff", padding: "5px 10px" }}
        >
          🗑 Delete
        </button>
      </td>
    </tr>
  ))}
</tbody>
        </table>
      )}
    </div>
  );

}

export default CropPage;
