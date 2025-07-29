import React, { useEffect, useState } from 'react';
import { jwtDecode } from 'jwt-decode';
import {
  Container,
  Typography,
  CircularProgress,
  List,
  ListItem,
  ListItemText,
  Paper,
  Box,
} from '@mui/material';

type DecodedToken = {
  id: number;
  fullName: string;
  exp: number;
};

type Result = {
  id: number;
  userId: number;
  quizResult: string;
};

const ResultsPage: React.FC = () => {
  const [userId, setUserId] = useState<number | null>(null);
  const [fullName, setFullName] = useState<string | null>(null);
  const [results, setResults] = useState<Result[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('authToken');
    if (!token) return;

    try {
      const decoded = jwtDecode<DecodedToken>(token);
      setUserId(decoded.id);
      setFullName(decoded.fullName);
    } catch (error) {
      console.error('Invalid token:', error);
    }
  }, []);

  useEffect(() => {
    if (userId === null) return;

    const fetchResults = async () => {
      try {
        const response = await fetch(`http://localhost:9090/api/results/${userId}`);
        if (!response.ok) throw new Error('Failed to fetch results');
        const data: Result[] = await response.json();
        setResults(data);
      } catch (error) {
        console.error('Error fetching results:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchResults();
  }, [userId]);

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Typography variant="h4" gutterBottom>
        Your Quiz Results
      </Typography>

      {fullName && (
        <Typography variant="subtitle1" gutterBottom>
          Welcome, {fullName} (User ID: {userId})
        </Typography>
      )}

      {loading ? (
        <Box display="flex" justifyContent="center" mt={4}>
          <CircularProgress />
        </Box>
      ) : results.length === 0 ? (
        <Typography variant="body1">No results found.</Typography>
      ) : (
        <Paper sx={{ mt: 2, padding: 2 }}>
          <List>
            {results.map((result) => (
              <ListItem key={result.id} divider>
                <ListItemText
                  primary={result.quizResult}
                  secondary={`User ID: ${result.userId}`}
                />
              </ListItem>
            ))}
          </List>
        </Paper>
      )}
    </Container>
  );
};

export default ResultsPage;
