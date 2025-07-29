import { Container, Typography, Box, Stack } from '@mui/material';

export default function Home() {
  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        color: 'black',
        textAlign: 'center',
        p: 4,
      }}
    >
      <Container maxWidth="md">
        <Stack spacing={4}>
          <Typography variant="h2" component="h1" fontWeight="bold">
            Welcome
          </Typography>
          <Typography variant="h6">
            A modern web experience built to help you learn faster and more efficiently
          </Typography>
        </Stack>
      </Container>
    </Box>
  );
}
