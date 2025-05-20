# KMPDemo

A Kotlin Multiplatform (KMP) project demonstrating shared business logic and UI across Android, iOS, and Compose Multiplatform.

## Project Structure

```
KMPDemo/
├── androidApp/      # Native Android application (entry point for Android)
├── iosApp/          # Native iOS application (entry point for iOS)
├── composeApp/      # Compose Multiplatform UI module (shared UI for Android/iOS)
├── shared/          # Shared Kotlin Multiplatform module (business logic, models, data)
├── build.gradle.kts
├── settings.gradle.kts
└── ...
```

### Modules

#### 1. `androidApp/`
- Contains the Android-specific application code.
- Depends on both `shared` and `composeApp` modules.
- Standard Android project structure under `src/main`.

#### 2. `iosApp/`
- Contains the iOS-specific application code.
- Uses Swift and integrates with the shared KMP logic via generated frameworks.
- Standard Xcode project structure.

#### 3. `composeApp/`
- Compose Multiplatform UI module.
- Contains shared UI code using Jetpack Compose for Android and Compose for iOS.
- Depends on the `shared` module for business logic.
- Supports Android and iOS targets.

#### 4. `shared/`
- Core business logic, models, repositories, and data sources.
- Organized into multiplatform source sets:
  - `commonMain`: Shared code (models, repositories, use cases, etc.)
  - `androidMain`, `iosMain`, `jvmMain`: Platform-specific implementations if needed.
- Example structure in `commonMain`:
  - `domain/`: Business models, repositories, and use cases.
  - `data/`: Data sources (remote/local), DTOs, and repository implementations.

## Build & Run

- **Android:** Open in Android Studio and run the `androidApp` module.
- **iOS:** Open `iosApp/iosApp.xcodeproj` in Xcode and run on a simulator or device.
- **Compose Multiplatform:** The `composeApp` module can be used for shared UI logic.

## Dependencies

- Kotlin Multiplatform
- Jetpack Compose Multiplatform
- Koin (Dependency Injection)
- SQLDelight (for local database)
- Coroutines, Serialization, etc. 