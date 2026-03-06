# 📚 ClassSense

## Smart Attendance, Safety & Engagement System for Schools

ClassSense is a sensor-driven mobile application designed to modernize classroom management through automated attendance, engagement analytics, and real-time safety monitoring.

The system reduces administrative workload for teachers while providing actionable insights into student participation and academic performance using smartphone sensors.

---

## 🎯 Problem Statement

Educational institutions face several operational challenges:

- Manual attendance processes that are time-consuming and error-prone
- Limited visibility into student engagement during lessons
- Late identification of struggling students
- Lack of real-time classroom analytics
- Administrative overload for teachers

These issues reduce effective teaching time and prevent early academic intervention.

---

## 💡 Proposed Solution

ClassSense introduces an intelligent classroom assistant powered by smartphone sensors.

The system automates attendance tracking, measures classroom engagement, and provides real-time analytics for teachers — without invasive monitoring technologies.

### Core Capabilities

- ✔ Automated attendance using Bluetooth proximity or GPS geofencing
- ✔ Engagement detection using accelerometer and gyroscope data
- ✔ Student learning assistant with reminders and quizzes
- ✔ Teacher analytics dashboard
- ✔ Real-time absence and performance alerts

The solution is privacy-conscious — no camera or microphone access required.

---

## 🧠 System Architecture Overview

Mobile Application (Flutter)
⬇
Sensor Layer (Bluetooth, GPS, Accelerometer, Gyroscope)
⬇
State Management (Provider / Riverpod)
⬇
Cloud Backend (Firebase)
⬇
Analytics & Dashboard Interface

The architecture ensures scalability, modularity, and maintainability.

---

## 🧩 Key Features

### 1️⃣ Smart Attendance System

- Bluetooth Low Energy (BLE) beacon detection
- GPS-based classroom geofencing
- Automatic check-in and check-out
- Offline and online synchronization
- Real-time attendance updates

---

### 2️⃣ Engagement Radar System

Uses:

- Accelerometer
- Gyroscope

Detects:

- Excessive phone movement
- Prolonged idle time
- Repetitive interaction patterns

Generates a non-invasive engagement score.

---

### 3️⃣ Smart Study Assistant

- Homework reminders
- Schedule notifications
- Mini quizzes
- Progress tracking dashboard

---

### 4️⃣ Teacher Dashboard

Provides:

- Live attendance list
- Engagement analytics
- Identification of at-risk students
- Visual performance trends
- Class-level engagement graphs

---

### 5️⃣ Real-Time Alerts

Optional notifications for:

- Student absence
- Performance decline
- Early classroom exit detection

---

## 👥 Team Structure (2-Person Model)

### 👨‍💻 Lead Developer

- Sensor integration (Bluetooth, GPS, accelerometer)
- Engagement detection logic
- UI/UX implementation
- Backend integration (Firebase)
- Database modeling
- Documentation

### 🧪 QA & Test Engineer

- Unit testing
- Integration testing
- Sensor mocking
- GPS boundary validation
- Engagement algorithm validation
- Edge case simulations
- Failure scenario handling

---

## 🛠️ Technology Stack

### 📱 Frontend

- Flutter (Dart)

### 📡 Sensor Packages

- geolocator
- flutter_blue
- sensors_plus

### 🔄 State Management

- Provider or Riverpod

### ☁ Backend

- Firebase Authentication
- Cloud Firestore
- Firebase Cloud Messaging
- Firebase Analytics

### 🧪 Testing Tools

- flutter_test
- integration_test
- mockito
- test

---

## 🧪 Testing Strategy

The application follows a structured testing approach:

- Unit tests for engagement algorithm
- Integration tests for attendance workflow
- Sensor mocking for GPS and motion data
- Geofence boundary validation
- Load testing for multiple simultaneous check-ins
- Network failure handling tests

Each major scenario is implemented as an independent test class.
