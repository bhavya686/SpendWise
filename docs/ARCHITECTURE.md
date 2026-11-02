# SpendWise — Architecture

## 1. Project Overview

SpendWise is a native Android personal finance management application designed to help users record transactions, manage budgets, monitor spending, and understand their financial habits.

The application uses a modular, maintainable architecture with clear separation between the user interface, business logic, and data operations.

## 2. Technology Stack

| Component | Technology |
|---|---|
| Platform | Android |
| Programming Language | Kotlin |
| UI Framework | Jetpack Compose |
| Design System | Material 3 |
| Architecture | MVVM + Clean Architecture |
| Asynchronous Programming | Kotlin Coroutines |
| Reactive Data Handling | Kotlin Flow |
| Navigation | Navigation Compose |
| Authentication | Firebase Authentication |
| Cloud Database | Cloud Firestore |
| Dependency Injection | Hilt (when introduced) |
| Build System | Gradle with Kotlin DSL |
| Version Control | Git and GitHub |

## 3. Architectural Approach

SpendWise follows MVVM (Model-View-ViewModel) combined with Clean Architecture principles.

The application is divided into three primary layers:

### 3.1 Presentation Layer

Responsible for displaying information and handling user interactions.

Responsibilities:
- Render screens using Jetpack Compose.
- Handle user input and UI events.
- Observe application state from ViewModels.
- Display loading, success, empty, and error states.
- Navigate between application screens.

### 3.2 Domain Layer

Contains the application's business rules and core logic.

Responsibilities:
- Define business models.
- Define repository interfaces.
- Implement use cases for individual business operations.
- Keep business logic independent of Android UI and Firebase implementation details.

### 3.3 Data Layer

Responsible for retrieving, storing, and managing application data.

Responsibilities:
- Communicate with Firebase Authentication and Cloud Firestore.
- Implement domain repository interfaces.
- Convert remote data into domain models.
- Handle data operations and errors.
- Maintain user-specific data access.

## 4. Project Package Structure

The planned package structure is:

```text
com.bhavya.spendwise/
│
├── data/
│   ├── remote/
│   ├── repository/
│   └── mapper/
│
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
│
├── presentation/
│   ├── auth/
│   ├── dashboard/
│   ├── transactions/
│   ├── budgets/
│   ├── analytics/
│   ├── settings/
│   ├── navigation/
│   └── components/
│
├── di/
│
├── ui/
│   └── theme/
│
└── MainActivity.kt
```

Packages will be introduced as their corresponding features are implemented. Empty packages will not be created merely to fill out the directory structure.

## 5. Application Features

### 5.1 Authentication

- User registration using email and password.
- User login and logout.
- Authentication state management.
- Account deletion.

### 5.2 Dashboard

- Display the user's financial overview.
- Show total income and expenses.
- Show current balance.
- Display recent transactions.
- Present budget summaries.

### 5.3 Transaction Management

- Add income and expense transactions.
- Edit existing transactions.
- Delete transactions.
- Assign categories.
- Add descriptions.
- Select transaction dates, including past dates.
- Search and filter transaction history.

### 5.4 Budget Management

- Create category-based budgets.
- Set spending limits.
- Track actual spending.
- Calculate remaining budget.
- Display budget utilization.
- Edit and delete budgets.

### 5.5 Analytics

- Analyze income and expenses.
- Display spending by category.
- Visualize financial trends.
- Generate summaries using actual user transaction data.

### 5.6 Account Settings

- Display account information.
- Manage account-related settings.
- Log out.
- Delete the account and associated cloud data.

## 6. Data Management Principles

### 6.1 Currency Representation

All monetary values will be stored as integer paise rather than floating-point numbers.

Example:

- ₹100.00 = 10,000 paise
- ₹25.50 = 2,550 paise

This avoids floating-point precision errors in financial calculations.

### 6.2 Initial Balance

Every new user starts with a balance of ₹0.

The application will not create artificial transactions, sample financial records, or prepopulated budgets.

### 6.3 User Data Isolation

Every user's financial records must be associated with their Firebase Authentication UID.

Users must only be able to read and modify their own data.

## 7. Planned Firestore Data Structure

The planned collection structure is:

```text
users/
  {uid}/
    profile/
    transactions/
      {transactionId}
    budgets/
      {budgetId}
```

The exact document fields and security rules will be finalized during Firebase integration.

All user-specific queries and writes must be scoped to the authenticated user's UID.

## 8. Financial Calculation Principles

- Income increases the balance.
- Expenses decrease the balance.
- Budget utilization is calculated from eligible expenses.
- Remaining budget is calculated from the budget limit minus actual spending.
- Dashboard and analytics values must be derived from actual stored records.
- Calculations must use integer paise wherever possible.

## 9. Security Principles

- Firebase Authentication manages user identity.
- Firestore Security Rules enforce user-level data isolation.
- Firebase configuration files containing project-specific configuration will not be committed to the public repository.
- Secrets, service account credentials, signing keys, and private configuration must never be committed.
- Firestore rules will be tested using the Firebase Emulator Suite.

## 10. Design Principles

- Light theme only.
- Material 3 design system.
- Consistent typography, spacing, and component styling.
- Reusable Compose components.
- Clear navigation and predictable screen behavior.
- Responsive layouts for supported Android screen sizes.
- Meaningful loading, empty, and error states.

## 11. Testing Strategy

The project will include:

- Unit tests for business logic and use cases.
- Repository tests.
- ViewModel tests.
- UI tests for important user flows.
- Firebase security rule tests using the Emulator Suite.

## 12. Architectural Evolution

The architecture will be implemented incrementally.

New packages, dependencies, and abstractions will be introduced when required by actual application features.

The objective is maintainability and separation of responsibilities without unnecessary complexity.