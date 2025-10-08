# 🛍️ Product Browser App

A **Kotlin Multiplatform Mobile (KMM)** application built using **Compose Multiplatform** that showcases a simple, cross-platform product catalog.  
The app consumes public product data from the [DummyJSON Products API](https://dummyjson.com/docs/products).

---

## 📋 Overview

This project demonstrates how to structure a scalable, testable, and clean Kotlin Multiplatform app that runs on both **Android** and **iOS**, sharing the same business logic, models, and networking code.

It is designed as part of **Revest's Mobile Engineering** evaluation task — focusing on clean architecture, multiplatform setup, and Compose UI.

---

## 🧩 Business Requirements

The app allows users to:

1. View a list of products showing name, price, and thumbnail.
2. Tap a product to view detailed information (title, description, brand, price, and rating).
3. Search products by keyword (API-based search).
4. *(Optional)* Filter products by category.

---

## ⚙️ Technical Overview

| Layer | Responsibility |
|-------|----------------|
| **Data Layer** | Uses **Ktor Client** for API calls and **kotlinx.serialization** for JSON parsing. |
| **Domain Layer** | Implements use cases like `GetProductsUseCase` and `SearchProductsUseCase`. |
| **Presentation Layer** | Uses **Compose Multiplatform** and `StateFlow`-based ViewModels for UI state management. |

---

## 🧱 Project Structure

ProductBrowserKMM/
├── androidApp/ # Android UI module (Compose)
│ ├── src/main/AndroidManifest.xml
│ ├── MainActivity.kt
│ └── build.gradle.kts
│
├── shared/ # Shared KMM module
│ ├── models/ # Data classes (Product, ProductListResponse)
│ ├── network/ # Ktor API client setup
│ ├── repository/ # ProductRepository (API calls)
│ ├── usecase/ # Business use cases
│ ├── viewmodel/ # State management (ViewModel)
│ └── build.gradle.kts
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…