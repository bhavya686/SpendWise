# SpendWise — Development Guide

## 1. Development Environment

The following tools are required to develop SpendWise:

| Tool | Purpose |
|---|---|
| Android Studio | Android application development |
| JDK 21 | Gradle build environment |
| Android SDK | Android platform and build tools |
| Kotlin | Application programming language |
| Gradle Wrapper | Project build automation |
| Visual Studio Code | Source code editing and Git management |
| Git | Version control |
| GitHub | Remote repository hosting |

## 2. Project Configuration

| Configuration | Value |
|---|---|
| Application Name | SpendWise |
| Package Name | com.bhavya.spendwise |
| Minimum SDK | API 26 |
| Compile SDK | API 37.1 |
| Target SDK | API 36 |
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Design System | Material 3 |
| Theme | Light only |
| Currency | INR (₹) |

## 3. Opening the Project

1. Clone or download the project repository.
2. Open the project root directory in Android Studio.
3. Allow Gradle synchronization to complete.
4. Ensure the required Android SDK platform and build tools are installed.
5. Create or select an Android emulator, or connect a physical Android device.
6. Run the application.

## 4. Build Commands

Run these commands from the project root.

### Windows PowerShell

Build the debug application:

```powershell
.\gradlew.bat assembleDebug
```

Run unit tests:

```powershell
.\gradlew.bat test
```

Run Android instrumentation tests:

```powershell
.\gradlew.bat connectedAndroidTest
```

Clean the project:

```powershell
.\gradlew.bat clean
```

Run a clean debug build:

```powershell
.\gradlew.bat clean assembleDebug
```

Instrumentation tests require a connected device or running emulator.

## 5. Development Workflow

Development follows an incremental feature-based workflow.

1. Understand the feature requirements.
2. Identify the affected architectural layers.
3. Implement domain logic where required.
4. Implement data operations where required.
5. Develop the Compose UI.
6. Connect UI state to ViewModels.
7. Handle loading, empty, success, and error states.
8. Write relevant tests.
9. Run the application and verify the feature.
10. Review changes before committing.

Features must be implemented without bypassing the agreed architectural boundaries.

## 6. Kotlin and Compose Guidelines

- Follow Kotlin naming conventions.
- Use meaningful class, function, and variable names.
- Keep composable functions focused on UI responsibilities.
- Keep business logic outside composable functions.
- Use ViewModels to manage screen-level state.
- Prefer immutable UI state.
- Use Kotlin Coroutines for asynchronous operations.
- Use Flow for observable data streams.
- Avoid unnecessary recompositions and duplicated logic.
- Extract reusable UI components when justified.

## 7. Financial Data Guidelines

- Store monetary amounts as integer paise.
- Never use Float or Double for financial calculations.
- Format monetary values for display in INR.
- Use actual user data for financial summaries.
- Do not introduce fake transactions or sample balances.
- Validate monetary input before saving.
- Ensure transaction edits and deletions correctly affect calculated balances and budgets.

## 8. Firebase Development

Firebase integration will use:

- Firebase Authentication for account management.
- Cloud Firestore for persistent financial data.
- Firestore Security Rules for user-level access control.

The Firebase configuration file `google-services.json` must remain local and must not be committed.

Firebase setup instructions are maintained in `FIREBASE_SETUP.md`.

## 9. Git Branching and Commit Conventions

The default branch is:

```text
main
```

Use Conventional Commits.

Examples:

```text
chore: initialize SpendWise Android project
docs: add architecture documentation
feat: implement email authentication
feat: add transaction management
fix: correct budget utilization calculation
test: add transaction repository tests
refactor: simplify dashboard state handling
```

Commit messages must describe the actual change.

## 10. Commit Review Checklist

Before every commit:

- Confirm the application builds successfully.
- Run relevant tests.
- Inspect `git status`.
- Review the complete Git diff.
- Review staged files individually.
- Check for credentials, secrets, and personal configuration.
- Confirm generated files and build outputs are excluded.
- Confirm only intended files are staged.

Never commit credentials, local configuration, signing keys, service account files, or generated build artifacts.

## 11. Project Quality Standards

Every feature should satisfy the following expectations:

- Correct functionality.
- Clear separation of responsibilities.
- Consistent Material 3 UI.
- Appropriate input validation.
- Meaningful error handling.
- User-specific data access.
- Relevant test coverage.
- No unnecessary dependencies or abstractions.

## 12. Development Philosophy

SpendWise will be developed incrementally, with each major feature verified before moving to the next.

Correctness, data security, maintainability, and usability take priority over unnecessary complexity.