# Deploying The Dossier

The frontend and backend are separate GitHub repositories. Render hosts the
Spring Boot API and PostgreSQL database; Vercel hosts the React frontend.

## Render

1. Create or update a Blueprint from the backend repository root. Render reads
   `render.yaml` and provisions the free `dossier-api` web service and
   `dossier-db` PostgreSQL database in Singapore.
2. Confirm the Blueprint creates both services. The database is configured for
   testing; review its expiration and retention in the Render dashboard and
   export any data you need before it expires.
3. The Blueprint connects the API to PostgreSQL and sets the `render` Spring
   profile. It allows the existing frontend origin
   `https://the-dossier-front-end.vercel.app`; update
   `APP_CORS_ALLOWED_ORIGINS` on the API if Vercel assigns a different domain.
4. The API health check is available at `/health`.

## Vercel

1. Import the frontend repository. Its root already contains the React app
   and `vercel.json`.
2. Set `REACT_APP_API_BASE_URL` to the deployed Render API URL, without a
   trailing slash (for example, `https://dossier-api.onrender.com`).
3. Deploy or redeploy the frontend so the API URL is included in its build.

Local MySQL settings remain available for development. The Render profile uses
the managed PostgreSQL database; existing MySQL records are not copied
automatically.
