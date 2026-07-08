# Payment UI

## What was added

1. Angular app scaffold in `payment-ui/` (standalone components, routing, reactive forms, HttpClient).
2. Payment dashboard UI:
◦
Input: JWT token + transactions JSON
◦
Actions: calculate average, calculate fees, load theory answers
◦
Output panels: per-account averages, per-account fees, theory Q&A, error display
3.
API integration service:
◦
POST /api/payments/analytics/average
◦
POST /api/payments/fees/total
◦
GET /api/payments/theory
4.
Unit tests:
◦
payment-api.service.spec.ts (HttpClient testing)
◦
payment-dashboard.component.spec.ts (component behavior)
5.
Container/runtime integration:
◦
payment-ui/Dockerfile (Node build + Nginx serve)
◦
payment-ui/nginx/default.conf (SPA routing + /api proxy to api-gateway)
◦
docker-compose.yml updated with payment-ui on http://localhost:4200
6.
Platform updates:
◦
CI workflow updated for payment-ui Docker image + Angular unit-test job
◦
README and architecture docs updated
◦
Gateway CORS updated for Angular local origin
I couldn’t execute Angular tests/build in this environment because Node.js is not installed locally and Docker daemon is currently unavailable, but all requested frontend code and test cases are now in place.