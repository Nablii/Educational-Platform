import { useState } from 'react';
import {
  Container,
  TextField,
  Button,
  Typography,
  Stack,
  Box,
  CircularProgress,
} from '@mui/material';
import axios from 'axios';

export default function TopicFormPage() {
  const [topic, setTopic] = useState('');
  const [result, setResult] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [createMessage, setCreateMessage] = useState('');

  const handleRequest = async (type: 'summary' | 'definition') => {
    if (!topic.trim()) return;

    setLoading(true);
    setError('');
    setResult('');
    setCreateMessage('');

    try {
      const response = await axios.post(
        `http://localhost:9090/api/topics/${type}`,
        { topicName: topic }
      );
      setResult(response.data);
    } catch (err) {
      setError('Failed to fetch data. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleCreateTopic = async () => {
    if (!topic.trim()) return;

    setLoading(true);
    setError('');
    setResult('');
    setCreateMessage('');

    try {
      await axios.post('http://localhost:9090/api/topics/create', {
        topicName: topic,
      });
      setCreateMessage(`Topic "${topic}" created successfully.`);
    } catch (err) {
      setError('Failed to create topic. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      sx={{
        minHeight: '100vh',
        backgroundColor: '#f5f5f5',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        p: 4,
      }}
    >
      <Container maxWidth="sm">
        <Stack spacing={4}>
          <Typography variant="h4" textAlign="center" fontWeight="bold">
            Topic Explorer
          </Typography>

          <TextField
            label="Enter a topic"
            variant="outlined"
            fullWidth
            value={topic}
            onChange={(e) => setTopic(e.target.value)}
          />

          <Stack direction="row" spacing={2} justifyContent="center">
            <Button
              variant="contained"
              color="primary"
              onClick={() => handleRequest('summary')}
              disabled={!topic.trim() || loading}
            >
              Summarize
            </Button>
            <Button
              variant="contained"
              color="primary"
              onClick={() => handleRequest('definition')}
              disabled={!topic.trim() || loading}
            >
              Definition
            </Button>
            <Button
              variant="outlined"
              color="secondary"
              onClick={handleCreateTopic}
              disabled={!topic.trim() || loading}
            >
              Create Topic
            </Button>
          </Stack>

          {loading && (
            <Box textAlign="center">
              <CircularProgress />
            </Box>
          )}

          {error && (
            <Typography color="error" textAlign="center">
              {error}
            </Typography>
          )}

          {createMessage && (
            <Typography color="success.main" textAlign="center">
              {createMessage}
            </Typography>
          )}

          {result && (
            <Box
              sx={{
                backgroundColor: '#fff',
                p: 3,
                borderRadius: 2,
                boxShadow: 1,
              }}
            >
              <Typography variant="h6">Result:</Typography>
              <Typography>{result}</Typography>
            </Box>
          )}
        </Stack>
      </Container>
    </Box>
  );
}
