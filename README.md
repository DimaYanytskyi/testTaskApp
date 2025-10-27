# Records Viewer App

This project consists of two parts:
1. **Backend (FastAPI + SQLite + Docker Compose)**
2. **Android Client (Kotlin + Jetpack Compose + Clean Architecture + Hilt + Retrofit)**

The backend provides a REST API with records of three possible types:
- **text** — displays text on screen
- **image** — displays an image
- **webview** — opens a web page

The Android app automatically fetches a random record and displays it based on its type.

---

## 🚀 How to Run

### 1️⃣ Start the Backend Server
Make sure Docker and Docker Compose are installed.

From the root of the backend project (where `docker-compose.yml` is located), run:

```bash
docker compose up --build
```

This command will:
- Build the Docker image
- Run the FastAPI server on port **8000**
- Automatically **seed the SQLite database with random records** during build

You can verify the backend is working by opening in your browser:
- [http://localhost:8000/records](http://localhost:8000/records)
- [http://localhost:8000/record/1](http://localhost:8000/record/1)

---

### 2️⃣ Run the Android App

1. Open the **Android project** (folder `testTaskApp` or `RecordsClient`) in **Android Studio**.
2. Make sure the server is running (`docker compose up`).
3. Press **Run ▶️** to launch the app on an **emulator**.

---

## 🧩 How It Works

- When the app starts, it requests `/records` from the server and receives a list of record IDs.
- It then selects a **random ID** from that list and requests `/record/{id}`.
- Depending on the record’s type:
  - `text` → Displays a text screen
  - `image` → Displays an image screen
  - `webview` → Opens a WebView screen

---

## ⚙️ Notes

- If testing on a **real device**, replace `10.0.2.2` with your computer’s local IP (e.g. `192.168.x.x`).

---

## 📦 Tech Stack

### Backend
- Python 3.11
- FastAPI
- SQLAlchemy
- SQLite
- Docker Compose

### Android
- Kotlin
- Jetpack Compose
- Hilt (Dependency Injection)
- Retrofit + Kotlinx Serialization
- Clean Architecture
- Coroutines
- Coil (for image loading)
