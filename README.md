# MeowMate Final Project

## Project Description

MeowMate is a pet adoption web application focused on connecting users with cats available for adoption and reporting lost/found cats. The project contains a Spring Boot backend (`MeowMate_BackEnd`) and a static frontend (`MeowMate_FrontEnd`) composed of HTML, CSS, and images.

Key features

- User authentication (including Google OAuth2)
- Browse and list cats for adoption
- Adoption request handling and admin dashboard
- Lost & found reporting
- Email notifications (SMTP configured)

## Screenshots

![Sign in page](/MeowMate_FrontEnd/assets/Images/SignINpage.png)

![Lost & Found](/MeowMate_FrontEnd/assets/Images/lost%20cat.png)

![Items / Listings](/MeowMate_FrontEnd/assets/Images/listing.png)

![DashBoard](/MeowMate_FrontEnd/assets/Images/Dashboard.png)

![Admin Management](/MeowMate_FrontEnd/assets/Images/admindashboard.png)

![Adoption Requests](/MeowMate_FrontEnd/assets/Images/adoption.png)

## Setup Instructions

This project includes two parts: a Java Spring Boot backend and a static frontend. The steps below assume you're on Windows using PowerShell (the project's default shell in this workspace). Replace placeholders (like passwords, OAuth credentials, and demo URLs) with your own values where needed.

Prerequisites

- Java 21 JDK installed and JAVA_HOME set
- Maven 3.x (or use the included Maven wrapper)
- MySQL server running (or change datasource to another DB)
- Internet connection for downloading Maven dependencies

Backend (MeowMate_BackEnd)

1. Open PowerShell and navigate to the backend folder:

```powershell
cd 'd:\MeowMate_FinalProject\MeowMate_BackEnd'
```

2. (Optional) If you want to use the Maven wrapper that ships with the project, use `mvnw.cmd` on Windows; otherwise use `mvn`.

3. Ensure MySQL is running and available at `localhost:3306`. The default datasource in `src/main/resources/application.properties` uses:

- URL: jdbc:mysql://localhost:3306/MeowMate?createDatabaseIfNotExist=true
- Username: root
- Password: Ijse@1234

4. Build and run the backend (using the wrapper):

```powershell
# Build
.\mvnw.cmd clean package -DskipTests

# Run
.\mvnw.cmd spring-boot:run
```

The backend will start on port 8080 by default (see `server.port` in `application.properties`). API endpoints will be available at http://localhost:8080.

Notes for backend configuration

- Java version: 21 (see `pom.xml`).
- JPA/Hibernate is configured with `spring.jpa.hibernate.ddl-auto=update`, so entities will be created/updated automatically.
- JWT settings are present (`jwt.secret`, `jwt.expiration`), and Google OAuth2 client credentials are present in `application.properties` — replace these with secure values before deploying.
- SMTP (Gmail) settings are configured in `application.properties`. For production use, consider using app-specific passwords or a dedicated email provider.

Frontend (MeowMate_FrontEnd)

1. The frontend is purely static HTML/CSS located in `MeowMate_FrontEnd/`. There is no build step required. To run it locally, open `index.html` in your browser or use a simple static server.

2. Recommended: Use a lightweight static server for proper relative file handling. With Python installed you can run:

```powershell
# From MeowMate_FrontEnd directory
cd 'd:\MeowMate_FinalProject\MeowMate_FrontEnd'
python -m http.server 5500
```

Then open http://localhost:5500 in your browser. Alternatively, simply open `index.html` by double-clicking it.

3. If the frontend calls backend APIs, ensure the backend is running at `http://localhost:8080`. Update AJAX endpoints in the frontend pages if different.


## Detailed feature notes

Below are short descriptions and implementation notes for several frontend pages and backend flows found in the project. These notes explain where to look in the repository and how the flows work so you can customize or extend them.

### 1. Settings page (`pages/setting.html`)

- Purpose: allow users to update their profile (name, contact info), manage notification preferences, and adjust account settings such as password change and linked OAuth accounts.
- Frontend: the page contains form inputs for profile fields and settings toggles. It uses JavaScript to call backend endpoints for updates.
- Backend: look for user-related controllers (e.g., `UserController` in `MeowMate_BackEnd/src/main/java/.../controller`) which expose endpoints for getting/updating the current user. Profile update endpoints likely accept multipart/form-data for avatar uploads.
- Notes: ensure CSRF and authentication checks are enabled for these endpoints. For password changes, use secure password hashing (BCrypt) and validate the old password before updating.

### 2. AI page (`pages/Aipage.html`)

- Purpose: experimental AI features (project may include an integration point for generating content or assistance through the configured Ollama API from `application.properties`). This could be used for content suggestions, automated responses, or help text.
- Backend: `application.properties` has Ollama API settings (e.g., `ollama.api.url` and `ollama.model`). Check `util/` or a dedicated service class for an API client that forwards requests to Ollama.
- Usage: the frontend likely sends text (e.g., prompts) to an endpoint which then proxies the request to the Ollama model. Responses are displayed in the UI.
- Notes: When testing locally, ensure the Ollama server (or chosen LLM endpoint) is reachable. Be mindful of rate limits and filter user input when exposing an LLM to users.

### 3. Email sending (notifications & verification)

- Purpose: the application sends emails for actions like adoption confirmations, password resets, and notifications.
- Config: SMTP settings are stored in `src/main/resources/application.properties` (Gmail SMTP is pre-configured). The backend uses Spring Boot's mail support (`spring-boot-starter-mail`).
- Key files: search for classes in the backend like `EmailSenderServiceController` or similar (there's `EmailSenderServiceController.class` in the compiled `target` folder). This service composes and sends emails using `JavaMailSender`.
- Local testing: for local development avoid using real Gmail credentials. Instead use a fake SMTP server (MailHog, smtp4dev) and point `spring.mail.host`/`port` to it. Example settings for MailHog running locally:

```properties
spring.mail.host=localhost
spring.mail.port=1025
spring.mail.username=
spring.mail.password=
```

- Production: use SMTP provider with app-specific credentials or a transactional email service (SendGrid, Mailgun) and secure secrets via environment variables.

### 4. Adoption requests flow

- Purpose: users can request to adopt a listed cat. Requests appear in the admin or owner dashboard for action (accept/reject).
- Frontend: adoption request forms and request lists exist in pages like `pages/Listing&requests.html`, `pages/AdoptionRequests.html`, or the dashboard views. The UI shows request cards with details and Accept/Reject buttons.
- Backend: controllers like `AdoptionApplicationController` handle creating and updating adoption applications. The process typically:
  - User submits adoption request (POST) with user and pet IDs.
  - Backend creates an AdoptionApplication entity and sets status to PENDING.
  - Admin/owner uses Accept/Reject API endpoints to change status to APPROVED/REJECTED; the backend updates the entity and optionally triggers an email notification to the requester.
- Notes: ensure state transitions are protected and validated (only authorized users can approve). Add transactional handling when approving (e.g., mark pet as adopted and close other open requests).

### 5. Lost cat sighting reporting

- Purpose: users can report a lost cat sighting to help reunite pets with owners.
- Frontend: `pages/LostCatReport.html` and `pages/lostfound.html` provide UI forms for submitting a sighting (location, description, photo). The Lost & Found listing page displays reported missing pets and allows users to report sightings via a modal or dedicated form.
- Backend: check `LostCatController` in the backend for endpoints that accept sighting reports (`POST /lostcats/sightings` or similar), and `LostCat` entity for data fields (location, description, reporter contact, image URL).
- Notifications: sighting reports can trigger emails to the pet owner if the owner has an active listing and contact email on file. The flow:
  - User submits sighting → backend stores sighting record and attempts to find the matching owner/listing.
  - If matched, send notification email to owner with sighting details.
- Notes: spam prevention (rate-limiting, captcha) is recommended for public sighting forms.

Security and deployment notes

- Keep secrets out of source control: move DB passwords, JWT secrets, OAuth client secrets, and SMTP credentials to environment variables or an external secret store. Use Spring Boot profiles (`application-dev.properties`, `application-prod.properties`) and `@Value` or `System.getenv()` for production secrets.
- HTTPS and OAuth redirect URIs: when deploying, update Google OAuth redirect URIs in the Google Cloud Console to the production domain and enable HTTPS.

## Demo Video


https://youtu.be/GuiPhunVpX0

## Developer Notes & Next Steps

- Replace secrets (DB password, JWT secret, OAuth client secret, email password) with secure values or environment variables before committing to a public repo.
- Consider creating a Docker Compose file to spin up MySQL and the backend together for easier setup.
- Add minimal end-to-end tests and a few screenshots in the `docs/` folder.

## Credits
- MeowMate Final Project