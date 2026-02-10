Skylark Drone Operations AI Coordinator

An AI-assisted coordination system that automates pilot and drone assignments, detects operational conflicts, and synchronizes data in real time using Google Sheets.
Live Demo:
https://dronecoordinator-production.up.railway.app/index.html

Project Overview

Skylark Drones manages multiple drone missions across locations. Manual coordination of pilots, drones, and mission schedules leads to conflicts, inefficiencies, and delays.
This project builds an AI-powered operations coordinator that:
Matches pilots and drones to missions
Detects scheduling and capability conflicts
Supports urgent reassignments
Syncs updates with Google Sheets in real time
Provides both API and web dashboard interfaces

Core Features : 
Pilot Roster Management
View all pilots
Track availability and assignments
Update pilot status after mission assignment

Drone Fleet Management
View drone availability and location
Detect drones under maintenance
Track drone deployments

Mission Assignment Engine

Matches pilots to missions based on:
Skills
Certifications
Location
Availability
Assigns available drones with required capabilities
Updates Google Sheets after assignment

Conflict Detection

The system prevents:
Skill mismatches
Certification mismatches
Double booking of pilots or drones
Assigning drones under maintenance
Location mismatches between pilot, drone, and mission

Urgent Reassignment Logic

For high-priority missions, the system can:
Reassign a pilot from a lower-priority mission
Still enforce safety constraints (skills & certifications)

System Architecture
Backend - 
Java Spring Boot,
RESTful APIs

Layered architecture:
Controller → Service → Repository

Data Source
Google Sheets API: 
Pilot roster,
Drone fleet,
Missions,
2-way synchronization for assignments

Frontend
Static HTML dashboard served from Spring Boot

Allows:
Viewing pilots, drones, missions
Assigning missions
Interacting with AI agent

Deployment
Hosted on Railway
Publicly accessible backend + frontend

🔗 API Endpoints
Endpoint	Method	Description
/pilots	GET	List all pilots
/drones	GET	List all drones
/missions	GET	List all missions
/assign/{missionId}	POST	Assign pilot & drone to mission
/agent	POST	Conversational AI interface
Example Agent Request
POST /agent
{
  "message": "show pilots"
}

Google Sheets Integration

The system reads and writes directly to Google Sheets.

Read Operations:
Pilot roster
Drone fleet
Missions
Write Operations
Pilot status updates
Pilot assignment updates
Drone deployment status
Drone assignment updates

Design Decisions
Choice	Reason
Google Sheets instead of DB	Transparency & easy verification
Rule-based AI	Deterministic and reliable
Spring Boot	Production-ready backend framework
Simple HTML frontend	Quick and lightweight UI

How to Run Locally
Clone the repo
Add your Google service account credentials JSON
Set environment variable for credentials path
Run Spring Boot app:
mvn spring-boot:run
Then open:
http://localhost:8080/index.html

Future Improvements
Time-based mission scheduling conflict detection
PostgreSQL database for scalability
Advanced natural language processing for agent
User authentication and roles

Interactive map-based dashboard

👩‍💻 Author

Sandhya P
AI Drone Operations Coordinator Project
