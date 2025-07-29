import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
    index("routes/home.tsx"),
    route("register", "routes/register.tsx"),
    route("login", "routes/login.tsx"),
    route("topic", "routes/topic.tsx"),
    route("quiz", "routes/quiz.tsx"),
    route("results", "routes/results.tsx")
] satisfies RouteConfig;

