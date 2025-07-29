import React, { useState } from 'react';
import {
  Box,
  Button,
  TextField,
  Typography,
  Stack,
  Paper,
} from '@mui/material';
import axios from 'axios';
import { useNavigate } from 'react-router';

const RegisterForm: React.FC = () => {
  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
    password: '',
  });

  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

const handleSubmit = async (e: React.FormEvent) => {
  e.preventDefault();

  try {
    const response = await axios.post('http://localhost:9090/api/users/register', formData);

    const { accessToken } = response.data;

    if(accessToken)
    {
        localStorage.setItem('authToken', accessToken);
        navigate('/');
        setTimeout(() => window.location.reload(), 100);
    }

    console.log('Registration successful. Token stored.');

  } catch (error: any) {
    console.error('Registration failed:', error.response?.data || error.message);
  }
};

  const handleCancel = () => {
    setFormData({ fullName: '', email: '', password: '' });
  };

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      minHeight="100vh"
    >
      <Paper elevation={3} sx={{ p: 4, width: '100%', maxWidth: 600 }}>
        <Typography variant="h5" gutterBottom>
          Sign Up
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate>
          <Stack spacing={2}>
            <TextField
              label="Full Name"
              name="fullName"
              value={formData.fullName}
              onChange={handleChange}
              fullWidth
              required
            />
            <TextField
              label="Email"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              fullWidth
              required
            />
            <TextField
              label="Password"
              name="password"
              type="password"
              value={formData.password}
              onChange={handleChange}
              fullWidth
              required
            />
            <Stack direction="row" spacing={2} justifyContent="flex-end">
              <Button type="submit" variant="contained" color="primary">
                Submit
              </Button>
              <Button variant="outlined" color="secondary" onClick={handleCancel}>
                Cancel
              </Button>
            </Stack>
          </Stack>
        </Box>
      </Paper>
    </Box>
  );
};

export default RegisterForm;
