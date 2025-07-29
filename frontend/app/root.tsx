import {
  isRouteErrorResponse,
  Links,
  Meta,
  NavLink,
  Outlet,
  Scripts,
  ScrollRestoration,
  useNavigate,
} from "react-router";

import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import Stack from '@mui/material/Stack';
import LogoutIcon from '@mui/icons-material/Logout';

import PersonAddAltIcon from '@mui/icons-material/PersonAdd';
import PersonIcon from '@mui/icons-material/Person';

// @ts-ignore
import type { Route } from "./+types/root"; 
import "./app.css";
import { useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";
import { Typography } from "@mui/material";

export const links: Route.LinksFunction = () => [
  { rel: "preconnect", href: "https://fonts.googleapis.com" },
  {
    rel: "preconnect",
    href: "https://fonts.gstatic.com",
    crossOrigin: "anonymous",
  },
  {
    rel: "stylesheet",
    href: "https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap",
  },
];

export function Layout({ children }: { children: React.ReactNode }) {
  const [loggedIn, setLoggedIn] = useState(false);
  const [fullName, setFullName] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('authToken');
    if (!token) return;

    try {
      const decoded = jwtDecode<{ fullName: string; exp: number }>(token);
      const isValid = decoded.exp > Date.now() / 1000;

      setLoggedIn(isValid);
      if (isValid) {
        setFullName(decoded.fullName);
      }
    } catch {
      setLoggedIn(false);
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('authToken');
    setLoggedIn(false);
    setFullName('');
    navigate('/login');
  };

  return (
    <html lang="en">
      <head>
        <meta charSet="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <Meta />
        <Links />
      </head>
      <body>
        <AppBar position="static">
          <Toolbar>
           <>
            <Button color="inherit" component={NavLink} to="/">
                Home
            </Button>

            {loggedIn && (
                <>
                <Button color="inherit" component={NavLink} to="/topic">
                    Topic
                </Button>
                <Button color="inherit" component={NavLink} to="/quiz">
                    Quiz
                </Button>
                <Button color="inherit" component={NavLink} to="/results">
                    Results
                </Button>
                </>
            )}
            </>

            <Box sx={{ flexGrow: 1 }} />

            {loggedIn ? (
              <Stack direction="row" spacing={2} alignItems="center">
                <Typography variant="body1" sx={{ mr: 2 }}>
                  {fullName}
                </Typography>
                <Tooltip title="Logout">
                  <IconButton color="inherit" onClick={handleLogout}>
                    <LogoutIcon fontSize="large" />
                  </IconButton>
                </Tooltip>
              </Stack>
            ) : (
              <Stack direction="row" spacing={2}>
                <Tooltip title="Register">
                  <IconButton color="inherit" component={NavLink} to="/register">
                    <PersonAddAltIcon fontSize="large" />
                  </IconButton>
                </Tooltip>
                <Tooltip title="Login">
                  <IconButton color="inherit" component={NavLink} to="/login">
                    <PersonIcon fontSize="large" />
                  </IconButton>
                </Tooltip>
              </Stack>
            )}
          </Toolbar>
        </AppBar>
        <div>{children}</div>
        <ScrollRestoration />
        <Scripts />
      </body>
    </html>
  );
}

export default function App() {
  return <Outlet />;
}

export function ErrorBoundary({ error }: Route.ErrorBoundaryProps) {
  let message = "Oops!";
  let details = "An unexpected error occurred.";
  let stack: string | undefined;

  if (isRouteErrorResponse(error)) {
    message = error.status === 404 ? "404" : "Error";
    details =
      error.status === 404
        ? "The requested page could not be found."
        : error.statusText || details;
  } else if (import.meta.env.DEV && error && error instanceof Error) {
    details = error.message;
    stack = error.stack;
  }

  return (
    <main className="pt-16 p-4 container mx-auto">
      <h1>{message}</h1>
      <p>{details}</p>
      {stack && (
        <pre className="w-full p-4 overflow-x-auto">
          <code>{stack}</code>
        </pre>
      )}
    </main>
  );
}
