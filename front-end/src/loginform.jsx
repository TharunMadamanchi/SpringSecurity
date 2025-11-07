import React, { useState } from "react";
import "./AuthorForm.css";

const LoginForm = () => {
  const [loginData, setLoginData] = useState({
    username: "",
    password: "",
  });

  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setLoginData({
      ...loginData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify(loginData),
      });

      const data = await response.text();
      setMessage(data);

      if (response.ok) {
        setLoginData({ username: "", password: "" });
      }
    } catch (error) {
      console.error(error);
      setMessage("Login failed. Please check your credentials.");
    }
  };

  return (
    <div className="auth-container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit} className="auth-form">
        <label>
          Username:
          <input
            type="text"
            name="username"
            value={loginData.username}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Password:
          <input
            type="password"
            name="password"
            value={loginData.password}
            onChange={handleChange}
            required
          />
        </label>

        <button type="submit">Login</button>
      </form>
      {message && <p className="message">{message}</p>}
    </div>
  );
};

export default LoginForm;