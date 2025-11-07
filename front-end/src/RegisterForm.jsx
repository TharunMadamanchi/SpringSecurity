import React, { useState } from "react";
import "./RegisterForm.css";

const RegisterForm = () => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    role: "USER",
    accountNonLocked: true,
    failedAttempts: 0,
    lastLoginAt: getLocalTimestamp(),
    lastLogoutAt: getLocalTimestamp(),
  });

  const [message, setMessage] = useState("");

  // Helper to get ISO string without 'Z' for LocalDateTime compatibility
  function getLocalTimestamp() {
    return new Date().toISOString().slice(0, -1); // removes 'Z'
  }

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.username.trim()) {
      setMessage("Username is required.");
      return;
    }

    const timestamp = getLocalTimestamp();
    const payload = {
      ...formData,
      lastLoginAt: timestamp,
      lastLogoutAt: timestamp,
    };

    console.log("Sending payload:", payload); // Debug log

    try {
      const response = await fetch("http://localhost:8080/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify(payload),
      });

      const data = await response.text();
      setMessage(data);

      if (response.ok) {
        setFormData({
          username: "",
          password: "",
          role: "USER",
          accountNonLocked: true,
          failedAttempts: 0,
          lastLoginAt: getLocalTimestamp(),
          lastLogoutAt: getLocalTimestamp(),
        });
      }
    } catch (error) {
      console.error(error);
      setMessage("Something went wrong. Please try again.");
    }
  };

  return (
    <div className="register-container">
      <h2>Register</h2>
      <form onSubmit={handleSubmit} className="register-form">
        <label>
          Username:
          <input
            type="text"
            name="username"
            value={formData.username}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Password:
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Role:
          <select name="role" value={formData.role} onChange={handleChange}>
            <option value="USER">USER</option>
            <option value="ADMIN">ADMIN</option>
          </select>
        </label>

        <button type="submit">Register</button>
      </form>
      {message && <p className="message">{message}</p>}
    </div>
  );
};

export default RegisterForm;