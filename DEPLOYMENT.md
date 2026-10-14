# Backend deployment

## Render and Aiven

1. Create an Aiven MySQL service and database. Allow connections from Render in
   the Aiven service's networking settings.
2. In Render, create a Blueprint using this backend repository. Render reads
   `render.yaml` and builds the service with the included Dockerfile.
3. Set the three prompted environment variables on the Render service:
   - `SPRING_DATASOURCE_URL`: `jdbc:mysql://<AIVEN_HOST>:<AIVEN_PORT>/<DATABASE>?sslMode=REQUIRED`
   - `SPRING_DATASOURCE_USERNAME`: the Aiven database username
   - `SPRING_DATASOURCE_PASSWORD`: the Aiven database password
4. Deploy the service. Spring Boot binds to the port provided by Render.

Do not commit database credentials. The datasource properties default to a
local MySQL instance for development and use Render's environment variables in
production.

## Frontend

Set `REACT_APP_API_BASE_URL` in the frontend hosting provider to the deployed
Render service URL, with no trailing slash (for example,
`https://dossier-api.onrender.com`). The Vercel frontend origin is allowed by
the user API's CORS configuration.
