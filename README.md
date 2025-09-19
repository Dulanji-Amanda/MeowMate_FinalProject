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

If you'd like, I can commit the images into `docs/screenshots/` for you — just confirm you want me to add them and I'll import the attached images into that folder and update the README if needed.

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

If you need to change DB settings, edit `src/main/resources/application.properties`.

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

## Demo Video

Please provide a YouTube link to the demo video. Follow the naming convention: MeowMate*FinalProject_Demo*<yourname>. Example placeholder:

https://youtu.be/REPLACE_WITH_YOUR_VIDEO_ID

Replace the above URL with your real demo video link. If you want, I can embed the real URL here once you provide it.

## Developer Notes & Next Steps

- Replace secrets (DB password, JWT secret, OAuth client secret, email password) with secure values or environment variables before committing to a public repo.
- Consider creating a Docker Compose file to spin up MySQL and the backend together for easier setup.
- Add minimal end-to-end tests and a few screenshots in the `docs/` folder.

## Credits

MeowMate Final Project
