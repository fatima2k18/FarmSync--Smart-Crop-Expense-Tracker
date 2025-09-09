// import { StrictMode } from 'react';
// import { createRoot } from 'react-dom/client';
// import { BrowserRouter, Routes, Route } from 'react-router-dom';

// import './index.css';

// import App from './App.jsx'; // 🏠 Login/Register page
// import CropPage from './pages/CropPage.jsx'; // 🌾 Crop listing page
// import AddCrop from './pages/AddCrop.jsx';  // ✅ Add Crop page
// import PrivateRoute from './components/PrivateRoute.jsx'; // 🔒 Auth wrapper

// createRoot(document.getElementById('root')).render(
//   <StrictMode>
//     <BrowserRouter>
//       <Routes>
//         <Route path="/" element={<App />} />

//         {/* ✅ Protected routes */}
//         <Route
//           path="/crop"
//           element={
//             <PrivateRoute>
//               <CropPage />
//             </PrivateRoute>
//           }
//         />
//         <Route
//           path="/add-crop"
//           element={
//             <PrivateRoute>
//               <AddCrop />
//             </PrivateRoute>
//           }
//         />

//         {/* Fallback */}
//         <Route
//           path="*"
//           element={<div className="p-6 text-center text-xl">404 - Page Not Found</div>}
//         />
//       </Routes>
//     </BrowserRouter>
//   </StrictMode>
// );



import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import './index.css';

import App from './App.jsx'; // 🏠 Login/Register page
import CropPage from './pages/CropPage.jsx'; // 🌾 Crop listing page
import AddCrop from './pages/AddCrop.jsx';  // ✅ Add Crop page

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />} />           {/* 🏠 Login/Register */}
        <Route path="/crop" element={<CropPage />} />  {/* 🌾 Crop Listing */}
        <Route path="/add-crop" element={<AddCrop />} /> {/* ✅ Add Crop */}
        <Route
          path="*"
          element={<div className="p-6 text-center text-xl">404 - Page Not Found</div>}
        />
      </Routes>
    </BrowserRouter>
  </StrictMode>
);
