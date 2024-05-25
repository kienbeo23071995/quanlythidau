import { BrowserRouter, Routes, Route } from "react-router-dom";
import PageLayout from "./layouts/PageLayout";
import Home from "./Pages/Home";
import SignIn from "./Pages/SignIn";
import Register from "./Pages/Register";
import Profile from "./Pages/Profile";
import ForgotPassword from "./Pages/ForgotPassword";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<PageLayout />}>
          <Route index element={<Home />} />
          <Route path="signin" element={<SignIn />} />
          <Route path="regis" element={<Register />} />
          <Route path="profile" element={<Profile />} />
          <Route path="forgotPassword" element={<ForgotPassword />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
