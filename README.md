# 📰 NewsApp

**NewsApp** is a modern Android application that displays up-to-date news articles from various sources. Built with Kotlin and Jetpack Compose, the app offers a smooth and visually appealing user experience.

## 📱 Features

- 📰 Display top news headlines with title, description, image, and publication date
- 🔄 Manual refresh using swipe-to-refresh
- ♾️ Infinite scrolling with automatic pagination
- 📱 Clean and modern UI following Material Design principles
- 🌐 Open full article in browser from article detail screen

## 🛠️ Tech Stack

- **Kotlin** – Main programming language
- **Jetpack Compose** – Declarative UI toolkit
- **Ktor** – For API calls
- **Coil** – Efficient image loading
- **MVVM Architecture** – For separation of concerns
- **Hilt** – Dependency Injection
- **Navigation Compose** – For screen navigation

## 📦 Project Structure

```
NewsApp/
├── data/              ← API calls and network layer
├── domain/            ← Models and business logic
├── presentation/      ← UI screens and ViewModels
├── di/                ← Dependency Injection setup
└── utils/             ← Utility functions and helpers
```

## 📷 Screenshots

![Home_screen](image/Home.png)<br><br>
![Article_screen](image/Article.png)<br><br>

## 🚀 Getting Started

1. Make sure you have Android Studio installed (latest version recommended).
2. Clone or download the project and open it in Android Studio.

```bash
git clone https://github.com/roywasker/NewsApp.git
``` 

3. **API Key already exists** If you want to change it, go to the Constants.kt file in the utils folder and change the API KEY.
4. click **Run** ▶️.
