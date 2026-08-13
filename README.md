# 💬 Chat Application

A native Android chat application built with **Kotlin** and **Firebase**.

The project demonstrates real-time messaging, user authentication, chat rooms, Firebase Firestore integration, and Android architecture components using an MVVM-style approach.

## 🚀 Features

* 🔐 User Registration
* 🔑 User Login
* 💬 Real-time Chat
* 🏠 Chat Rooms
* ➕ Create New Chat Rooms
* 👥 Join and browse available rooms
* 📩 Send and receive messages
* 🔥 Firebase Authentication
* ☁️ Firebase Firestore
* 📊 Firebase Analytics
* ⚡ Reactive UI with LiveData
* 🏗️ MVVM-style architecture
* 📱 XML UI with ViewBinding / DataBinding

---

## 🔐 Authentication

Authentication is handled using **Firebase Authentication**.

The application supports:

* User registration
* User login
* Authentication session handling
* Navigation based on user state

Typical authentication flow:

```text id="chat-auth-flow"
Splash
   ↓
Authentication Check
   ↓
Login / Register
   ↓
Home
```

---

## 💬 Chat Rooms

Users can create and interact with chat rooms.

The application includes dedicated flows for:

* Viewing available rooms
* Creating a new room
* Entering a room
* Sending messages inside a room

```text id="chat-room-flow"
Home
  ↓
Select / Create Room
  ↓
Chat Screen
  ↓
Send / Receive Messages
```

---

## 🔥 Firebase Firestore

**Cloud Firestore** is used as the application's cloud database.

Firestore is responsible for storing and retrieving application data such as:

* Chat rooms
* Messages
* User-related data

Its real-time capabilities allow the chat interface to update as message data changes.

---

## 🏗️ Architecture

The application follows an MVVM-style architecture.

```text id="chat-architecture"
UI
 ↓
ViewModel
 ↓
Firestore / Firebase Auth
 ↓
Firebase Backend
```

This separates the user interface from backend communication and state-management logic.

---

## 📂 Project Structure

```text id="chat-structure"
com.example.chatapplication/
│
├── SessionProvider.kt
│
├── common/
│   └── Shared application utilities
│
├── firestore/
│   └── Firestore-related operations
│
├── model/
│   └── Application data models
│
└── ui/
    ├── splash/
    ├── login/
    ├── register/
    ├── home/
    ├── addRoom/
    ├── chat/
    ├── BindingAdapter.kt
    ├── DialogesExtension.kt
    └── Message.kt
```

---

## 🛠️ Tech Stack

### Language

* **Kotlin**

### Android

* Android SDK
* XML Layouts
* ViewBinding
* DataBinding
* Fragments

### Architecture Components

* ViewModel
* LiveData

### Backend

* **Firebase Authentication**
* **Cloud Firestore**
* **Firebase Analytics**

### Other

* Kotlin Parcelize
* Material Components
* ConstraintLayout

---

## ⚡ Real-Time Messaging

The chat experience uses Firestore-backed data to display messages inside chat rooms.

Typical message flow:

```text id="message-flow"
User types message
       ↓
Message submitted
       ↓
Firestore
       ↓
Chat data updated
       ↓
UI reflects new message
```

This demonstrates working with cloud-backed real-time application data.

---

## 🔄 Session Management

The project includes a dedicated:

```text id="chat-session"
SessionProvider
```

to maintain and expose user session information across the application.

---

## 🎨 UI

The application uses the traditional Android View system with:

* XML layouts
* ViewBinding
* DataBinding
* Reusable Binding Adapters
* Dialog helpers

Screens are organized by feature to keep the UI layer easier to maintain.

---

## 🚀 Getting Started

### Prerequisites

You will need:

* Android Studio
* Android SDK
* JDK
* Firebase project
* Android device or emulator

---

## 🔥 Firebase Setup

To run the project, create a Firebase project and configure the Android application.

Enable:

* Firebase Authentication
* Cloud Firestore
* Firebase Analytics

Download your Firebase Android configuration file:

```text id="firebase-config"
google-services.json
```

and place it inside:

```text id="firebase-path"
app/
```

Do not expose private credentials or sensitive Firebase configuration outside the intended project configuration.

---

## 📥 Clone the Repository

```bash id="chat-clone"
git clone https://github.com/MohamedAbbas289/chat-application.git
cd chat-application
```

Open the project in Android Studio and sync Gradle.

---

## 🔨 Build

Linux / macOS:

```bash id="chat-build-linux"
./gradlew assembleDebug
```

Windows:

```bash id="chat-build-windows"
gradlew.bat assembleDebug
```

Or run the application directly from Android Studio.

---

## 🧪 Testing

Run unit tests with:

```bash id="chat-tests"
./gradlew test
```

---

## 🎯 Project Purpose

This project was built to practice Android and Firebase development concepts including:

* Kotlin Android development
* Firebase Authentication
* Cloud Firestore
* Real-time data
* Chat rooms
* Messaging
* MVVM
* ViewModel
* LiveData
* ViewBinding
* DataBinding
* Firebase-backed session handling

---

## 👨‍💻 Developer

**Mohamed Ibrahim Abbas**

Android & Kotlin Multiplatform Developer

GitHub: `MohamedAbbas289`
