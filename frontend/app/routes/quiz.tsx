import { useEffect, useState } from 'react';
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
import { jwtDecode } from 'jwt-decode';

export default function QuizFormPage() {
  const [quizName, setQuizName] = useState('');
  const [rawQuiz, setRawQuiz] = useState('');
  const [questions, setQuestions] = useState<string[]>([]);
  const [answers, setAnswers] = useState<string[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [createMessage, setCreateMessage] = useState('');
  const [finalResult, setFinalResult] = useState('');
  const [userId, setUserId] = useState<number | null>(null);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('authToken');
      if (!token) return;

      try {
        const decoded = jwtDecode<{ fullName: string; id: number; exp: number }>(token);
        setUserId(decoded.id);
      } catch (error) {
        console.error('Failed to decode token:', error);
        setUserId(null);
      }
    }
  }, []);

  const handleRequest = async (type: 'create' | 'generate') => {
    if (!quizName.trim()) return;

    setLoading(true);
    setError('');
    setRawQuiz('');
    setQuestions([]);
    setAnswers([]);
    setCreateMessage('');
    setFinalResult('');

    try {
      const response = await axios.post(
        `http://localhost:9090/api/quiz/${type}`,
        { quizName }
      );

      if (type === 'generate') {
        const quizText: string = response.data;
        setRawQuiz(quizText);

        const extracted = quizText.match(/\d+\.\s.+?(?=(?:\d+\.|$))/gs) || [];
        setQuestions(extracted);
        setAnswers(new Array(extracted.length).fill(''));
      } else {
        setCreateMessage(`Quiz "${quizName}" created successfully.`);
      }
    } catch (err) {
      setError(`Failed to ${type} quiz. Please try again.`);
    } finally {
      setLoading(false);
    }
  };

  const handleAnswerChange = (index: number, value: string) => {
    const updated = [...answers];
    updated[index] = value;
    setAnswers(updated);
  };

  const handleSubmitAnswers = async () => {
    const trimmedAnswers = answers.map((a) => a.trim());

    const hasEmpty = trimmedAnswers.some((a) => a === '');
    if (hasEmpty) {
      setError('Please answer all questions before submitting.');
      return;
    }

    const formatted =
      "Given a list of question-answer pairs formatted as 'Question : Answer', evaluate each answer for factual correctness based on general knowledge. Return only the total count of correct answers as a single integer, with no additional text, explanation, or formatting, here are the questions: " +
      questions.map((q, i) => `${q.trim()}: ${trimmedAnswers[i]}`).join(' | ');

    setError('');
    setFinalResult('');

    try {
      const response = await axios.post('http://localhost:9999/query', {
        input_string: formatted,
      });

      const returnedNumber = response.data.result;
      const resultText = `${returnedNumber} are correct`;
      setFinalResult(resultText);

      if (userId !== null) {
        await axios.post('http://localhost:9090/api/results/create', {
          quizResult: resultText,
          userId: userId,
        });
      } else {
        console.warn('User ID not found in token. Result not stored.');
      }
    } catch (err) {
      setError('Failed to send final result to server.');
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
      <Container maxWidth="md">
        <Stack spacing={4}>
          <Typography variant="h4" textAlign="center" fontWeight="bold">
            Quiz
          </Typography>

          <TextField
            label="Enter a quiz name"
            variant="outlined"
            fullWidth
            value={quizName}
            onChange={(e) => setQuizName(e.target.value)}
          />

          <Stack direction="row" spacing={2} justifyContent="center">
            <Button
              variant="contained"
              color="primary"
              onClick={() => handleRequest('generate')}
              disabled={!quizName.trim() || loading}
            >
              Generate Quiz
            </Button>
            <Button
              variant="outlined"
              color="secondary"
              onClick={() => handleRequest('create')}
              disabled={!quizName.trim() || loading}
            >
              Create Quiz
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

          {questions.length > 0 && (
            <Box
              sx={{
                backgroundColor: '#fff',
                p: 3,
                borderRadius: 2,
                boxShadow: 1,
              }}
            >
              <Typography variant="h6" gutterBottom>
                Quiz:
              </Typography>

              <Stack spacing={4}>
                {questions.map((q, index) => (
                  <Stack key={index} spacing={1}>
                    <Typography fontWeight="bold">{q}</Typography>
                    <TextField
                      label={`Your answer to Q${index + 1}`}
                      variant="outlined"
                      fullWidth
                      multiline
                      value={answers[index]}
                      onChange={(e) =>
                        handleAnswerChange(index, e.target.value)
                      }
                    />
                  </Stack>
                ))}
              </Stack>

              <Box textAlign="center" mt={4}>
                <Button
                  variant="contained"
                  color="success"
                  onClick={handleSubmitAnswers}
                  disabled={answers.some((a) => a.trim() === '')}
                >
                  Submit Answers
                </Button>
              </Box>
            </Box>
          )}

          {finalResult && (
            <Box
              sx={{
                backgroundColor: '#e0f7fa',
                p: 3,
                borderRadius: 2,
                boxShadow: 1,
              }}
            >
              <Typography variant="h6">Final Result:</Typography>
              <Typography>{finalResult}</Typography>
            </Box>
          )}
        </Stack>
      </Container>
    </Box>
  );
}
