# Arrowhead Navigator

A mobile application that helps Pacific Northwest businesses visualize weather impacts on delivery routes, with a focus on identifying flood risks that may affect supply chains.

## Project Structure

```
arrowhead-navigator/
├── README.md                 # Project documentation
├── .gitignore                # Git ignore file
├── docker-compose.yml        # Docker setup for local development
│
├── backend/                  # Java Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/arrowheadnavigator/
│   │   │   │   ├── ArrowheadNavigatorApplication.java
│   │   │   │   ├── config/          # Configuration classes
│   │   │   │   ├── controller/      # REST controllers
│   │   │   │   ├── model/           # Data models
│   │   │   │   ├── repository/      # Data access
│   │   │   │   ├── service/         # Business logic
│   │   │   │   └── util/            # Utility classes
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── static/
│   │   └── test/                    # Unit tests
│   ├── pom.xml                      # Maven dependencies
│   └── README.md                    # Backend documentation
│
├── mobile/                  # React Native frontend
│   ├── App.tsx              # Main application component
│   ├── app.json             # Expo configuration
│   ├── babel.config.js      # Babel configuration
│   ├── package.json         # NPM dependencies
│   ├── tsconfig.json        # TypeScript configuration
│   ├── src/
│   │   ├── api/             # API client functions
│   │   ├── components/      # Reusable UI components
│   │   ├── hooks/           # Custom React hooks
│   │   ├── navigation/      # React Navigation setup
│   │   ├── screens/         # Application screens
│   │   ├── services/        # Business logic
│   │   ├── types/           # TypeScript type definitions
│   │   └── utils/           # Utility functions
│   └── README.md            # Frontend documentation
│
└── docs/                    # Project documentation
    ├── architecture/        # Architecture diagrams
    ├── api/                 # API documentation
    └── screenshots/         # Application screenshots
```

## Setup Instructions

### Prerequisites
- Java 17+
- Node.js 16+
- npm or yarn
- Docker and Docker Compose (optional, for local development)

### Backend Setup
```bash
cd backend
./mvnw spring-boot:run
```

### Mobile Setup
```bash
cd mobile
npm install
npx expo start
```

## API Documentation
The backend REST API will be available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) when running locally.

## Development Roadmap
- [x] Project setup
- [ ] Basic Java backend with weather API integration
- [ ] Simple React Native map display
- [ ] Route visualization
- [ ] Risk assessment algorithm
- [ ] Data flow between backend and frontend
