# 📚 DialektApp

A modern **Android application** for learning Ukrainian dialects and language variations.

<div align="center">

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg?style=flat&logo=kotlin)](http://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-2024.09.02-brightgreen)](https://developer.android.com/jetpack/compose)
[![Hilt](https://img.shields.io/badge/Hilt-2.57.1-orange.svg?style=flat)](https://dagger.dev/hilt/)
[![License](https://img.shields.io/badge/License-Educational-yellow.svg)](LICENSE)

</div>

## ✨ Features

- 🎓 **Interactive Learning** - Engaging lessons and courses for Ukrainian dialects
- 🔐 **User Authentication** - Secure login and signup with password recovery
- 📊 **Progress Tracking** - Monitor your learning journey with detailed statistics
- 🏆 **Achievements System** - Earn rewards and unlock milestones as you learn
- 📈 **Leaderboard** - Compete with other learners and see your ranking
- 👤 **User Profile** - Personalized profile with avatar and learning statistics
- 🎨 **Modern UI** - Beautiful Material 3 design with Jetpack Compose
- 📱 **Responsive Layout** - Adaptive design for different screen sizes
- 🌙 **Dark Theme Support** - Comfortable learning in any lighting condition
- 🔄 **Cloud Sync** - RESTful API integration for seamless data synchronization

## 📸 Screenshots

<div align="center">

### Authentication Flow

Sign in, sign up, or recover your password with a beautiful and intuitive interface.

---

### Home Dashboard

Access your courses, track your progress, and continue your learning journey.

---

### Lessons & Activities

Interactive lessons with various activity types to enhance your learning experience.

---

### Profile & Achievements

View your profile, achievements, and learning statistics all in one place.

</div>

## 🏗️ Architecture

This project follows **MVVM + Clean Architecture** principles with clear separation of concerns:

```
├── presentation/        # UI Layer (Compose UI, ViewModels, Navigation)
│   ├── screens/
│   │   ├── auth/       # Authentication screens (Login, SignUp, ForgotPassword)
│   │   ├── home/       # Home dashboard
│   │   ├── course/     # Course screens
│   │   ├── lessons/    # Lesson screens
│   │   ├── activity/   # Activity/Exercise screens
│   │   ├── profile/    # User profile
│   │   ├── leaderboard/# Leaderboard screen
│   │   └── achievements/ # Achievements screen
│   ├── navigation/     # Navigation setup and routes
│   ├── components/     # Reusable UI components
│   └── util/          # UI utilities and helpers
├── domain/             # Business Logic (Models, Use Cases, Repository Interfaces)
├── data/               # Data Layer (API, Repositories, DTOs, Mappers)
└── di/                 # Dependency Injection (Hilt modules)
```

## 🛠️ Tech Stack

### Core Technologies

- **[Kotlin](https://kotlinlang.org/)** - Modern programming language for Android
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Modern declarative UI
  framework
- **[Material Design 3](https://m3.material.io/)** - Latest Material Design components

### Libraries & Frameworks

| Library | Purpose | Version |
|---------|---------|---------|
| [Jetpack Compose](https://developer.android.com/jetpack/compose) | Declarative UI framework | 2024.09.02 |
| [Dagger Hilt](https://dagger.dev/hilt/) | Dependency injection | 2.57.1 |
| [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) | In-app navigation | 2.8.5 |
| [Retrofit](https://square.github.io/retrofit/) | REST API client | 2.9.0 |
| [OkHttp](https://square.github.io/okhttp/) | HTTP client | 4.12.0 |
| [Gson](https://github.com/google/gson) | JSON serialization | 2.9.0 |
| [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) | JSON parsing | 1.7.2 |
| [Coil](https://coil-kt.github.io/coil/) | Image loading | 2.7.0 |
| [Security Crypto](https://developer.android.com/topic/security/data) | Encrypted SharedPreferences | 1.1.0-alpha03 |
| [Core Splashscreen](https://developer.android.com/develop/ui/views/launch/splash-screen) | Splash screen API | 1.0.1 |
| [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) | ViewModel & Runtime Compose | 2.8.7 |
| [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) | Asynchronous programming | 1.8.1 |

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Ladybug or later (2024.2.1+)
- **JDK** 11 or higher
- **Kotlin** 2.0.0
- **Android SDK** with API level 24+ (Android 7.0+)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/ch000se/DialektApp.git
   cd DialektApp
   ```

2. **Open in Android Studio**
    - Open Android Studio
    - Select "Open an Existing Project"
    - Navigate to the cloned directory

3. **Configure API Endpoint**
    - Update the base URL in your Retrofit configuration
    - Ensure the backend API is running and accessible

4. **Run the app**
    - Select your device/emulator
    - Click Run ▶️

### Configuration

Create a `local.properties` file in the root directory if it doesn't exist:

```properties
sdk.dir=YOUR_ANDROID_SDK_PATH
```

## 📱 Requirements

| Requirement | Version |
|-------------|---------|
| **Minimum SDK** | API 24 (Android 7.0) |
| **Target SDK** | API 36 (Android 14) |
| **Compile SDK** | API 36 |

## 🏛️ Project Structure

```
DialektApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       └── java/com/example/dialektapp/
│   │           ├── data/              # Data layer
│   │           │   ├── api/          # Retrofit API interfaces
│   │           │   ├── dto/          # Data transfer objects
│   │           │   ├── mapper/       # Data mappers
│   │           │   └── repository/   # Repository implementations
│   │           ├── domain/            # Domain layer
│   │           │   ├── model/        # Domain models
│   │           │   ├── repository/   # Repository interfaces
│   │           │   └── usecase/      # Business logic use cases
│   │           ├── presentation/      # UI layer
│   │           │   ├── screens/      # All app screens
│   │           │   ├── navigation/   # Navigation setup
│   │           │   ├── components/   # Reusable components
│   │           │   └── util/        # UI utilities
│   │           ├── di/               # Dependency injection
│   │           ├── ui/               # Theme and styling
│   │           ├── MainActivity.kt   # Main activity
│   │           └── DialektApplication.kt # Application class
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── gradle/                            # Gradle wrapper
├── build.gradle.kts                   # Root build file
├── settings.gradle.kts
└── README.md
```

## 🎯 Key Features Implementation

### 🔐 Authentication System

- User registration with validation
- Secure login with encrypted credential storage
- Password recovery functionality
- Session management with automatic token handling

### 📚 Learning System

- **Courses** - Structured learning paths for different dialects
- **Lessons** - Individual lessons within courses
- **Activities** - Interactive exercises and quizzes
- **Progress Tracking** - Real-time progress updates

### 🏆 Gamification

- **Achievements** - Unlock badges and milestones
- **Leaderboard** - Global and friend rankings
- **Streaks** - Daily learning streaks
- **Points System** - Earn points for completing activities

### 🎨 UI/UX (Jetpack Compose)

- Declarative UI components
- Material 3 theming
- Smooth animations and transitions
- Responsive layouts
- Edge-to-edge design
- Custom snackbar notifications

### 💉 Dependency Injection (Hilt)

- Modular DI setup
- ViewModel injection
- Repository pattern implementation
- Lifecycle-aware components
- Network module with Retrofit
- Security module with EncryptedSharedPreferences

## 🔌 API Integration

The app communicates with a RESTful backend API for:

- User authentication and authorization
- Course and lesson content
- Progress tracking
- Leaderboard data
- Achievement management

### API Configuration

Configure your API endpoint in the network module:

```kotlin
@Provides
@Singleton
fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("YOUR_API_BASE_URL")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}
```

## 🧪 Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Run all tests
./gradlew testDebugUnitTest connectedAndroidTest
```

## 📦 Build

### Debug Build

```bash
./gradlew assembleDebug
```

### Release Build

```bash
./gradlew assembleRelease
```

The APK will be generated in `app/build/outputs/apk/release/`

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

For major changes, please open an issue first to discuss what you would like to change.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is for educational and personal use.

## 🙏 Acknowledgments

- [JetBrains](https://www.jetbrains.com/) for Kotlin
- [Google](https://www.google.com/) for Android ecosystem
- [Square](https://square.github.io/) for Retrofit and OkHttp
- The amazing Android development community

## 📚 Resources

- [Android Developers](https://developer.android.com/)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Hilt Documentation](https://dagger.dev/hilt/)
- [Retrofit Documentation](https://square.github.io/retrofit/)

---

<div align="center">

Made with ❤️ using Jetpack Compose

</div>
