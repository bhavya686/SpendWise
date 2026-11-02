# SpendWise — Firebase Setup Guide

## 1. Overview

SpendWise uses Firebase services for user authentication and cloud-based financial data storage.

The Firebase services planned for integration are:

| Firebase Service | Purpose |
|---|---|
| Firebase Authentication | Email and password authentication |
| Cloud Firestore | Store user profiles, transactions, and budgets |
| Firebase Security Rules | Enforce user-level data access |
| Firebase Emulator Suite | Test Firebase functionality and security rules locally |

Firebase integration will be introduced after the initial Android project foundation is established.

## 2. Firebase Project Creation

1. Visit the [Firebase Console](https://console.firebase.google.com/).
2. Sign in with a Google account.
3. Select **Add project**.
4. Enter the project name: `SpendWise`.
5. Follow the project creation instructions.
6. Google Analytics can remain disabled unless it is required later.
7. Complete project creation.

The Firebase project should be dedicated to SpendWise.

## 3. Register the Android Application

1. Open the SpendWise Firebase project.
2. Select **Add app**.
3. Choose Android.
4. Enter the Android package name:

   `com.bhavya.spendwise`

5. Register the application.
6. Download the `google-services.json` configuration file.
7. Place the file inside the Android application's module directory:

```text
SpendWise/
└── app/
    └── google-services.json
```

### Important Security Note

The `google-services.json` file contains Firebase project configuration and identifiers. It must remain local and must not be committed to the public GitHub repository.

The project `.gitignore` excludes this file.

Never commit Firebase service account credentials, private keys, or other secrets.

## 4. Firebase Authentication Setup

1. Open **Build → Authentication** in the Firebase Console.
2. Select **Get started**.
3. Open the Sign-in method section.
4. Enable **Email/Password** authentication.
5. Save the configuration.

The application will use Firebase Authentication for:

- User registration.
- User login.
- Authentication state management.
- Logout.
- Account deletion.

Every authenticated user will have a unique Firebase Authentication UID.

This UID will be used to associate financial records with the correct account.

## 5. Cloud Firestore Setup

1. Open **Build → Firestore Database**.
2. Select **Create database**.
3. Choose a database location appropriate for the application.
4. Complete the database creation process.
5. Configure and deploy the application's security rules before relying on Firestore for user data.

The application will use Cloud Firestore as its persistent cloud database.

### Planned Data Structure

```text
users/
  {uid}/
    transactions/
      {transactionId}
    budgets/
      {budgetId}
```

The user document at `users/{uid}` will hold profile information.

Transactions and budgets will be stored in separate subcollections under the authenticated user's document.

Exact document fields and indexes will be defined during implementation.

## 6. Firestore Security Rules

Firestore Security Rules must prevent users from accessing another user's financial information.

The following is the initial security-rule design:

```javascript
rules_version = '2';

service cloud.firestore {
  match /databases/{database}/documents {

    function isOwner(userId) {
      return request.auth != null
          && request.auth.uid == userId;
    }

    match /users/{userId} {
      allow read, write: if isOwner(userId);

      match /transactions/{transactionId} {
        allow read, write: if isOwner(userId);
      }

      match /budgets/{budgetId} {
        allow read, write: if isOwner(userId);
      }
    }
  }
}
```

### Security Considerations

- Unauthenticated users must not access private financial records.
- Authenticated users must only access their own user document.
- Users must not read, create, update, or delete another user's transactions or budgets.
- Security rules must be tested before production deployment.
- Client-side validation must not be treated as a replacement for server-side security rules.

The rules above provide a basic ownership boundary. Additional field validation, data-type validation, and permitted-operation restrictions will be added as the Firestore schema is finalized.

## 7. Firebase Emulator Suite

The Firebase Emulator Suite will be used to test Firebase functionality without depending on production data.

Planned testing includes:

- Authentication workflows.
- Firestore read and write operations.
- User data isolation.
- Unauthorized access attempts.
- Transaction and budget data operations.
- Security-rule enforcement.

The Emulator Suite should be used before deploying changes to production Firebase services.

## 8. Account Deletion

Account deletion must address both:

1. Firebase Authentication account removal.
2. Deletion of the user's associated Firestore data.

Removing a Firebase Authentication account alone does not automatically delete its Firestore documents.

A trusted backend process, such as a Firebase Cloud Function, should perform recursive deletion of the user's data before or as part of account removal.

The deletion workflow must handle failures and prevent partially deleted accounts from being treated as successfully deleted.

## 9. Configuration and Credential Management

The following files and credentials must not be committed:

```text
google-services.json
service-account.json
*.jks
*.keystore
secrets.properties
```

Never publish:

- Firebase service account private keys.
- API credentials intended to remain secret.
- Signing credentials.
- Personal access tokens.
- Private configuration files.

Firebase client configuration is not an authorization boundary. Firestore Security Rules and appropriate backend controls must protect user data.

## 10. Firebase Integration Checklist

- [ ] Create the Firebase project.
- [ ] Register the Android application.
- [ ] Configure Firebase Authentication.
- [ ] Enable Email/Password authentication.
- [ ] Create the Cloud Firestore database.
- [ ] Add local Firebase configuration.
- [ ] Configure the Firebase Gradle plugin and dependencies.
- [ ] Implement authentication integration.
- [ ] Implement Firestore repository operations.
- [ ] Write and deploy Firestore Security Rules.
- [ ] Configure the Firebase Emulator Suite.
- [ ] Test user-level data isolation.
- [ ] Implement secure account deletion.

## 11. Official Documentation

- [Firebase Documentation](https://firebase.google.com/docs)
- [Firebase Authentication for Android](https://firebase.google.com/docs/auth/android/start)
- [Cloud Firestore for Android](https://firebase.google.com/docs/firestore/quickstart)
- [Firestore Security Rules](https://firebase.google.com/docs/firestore/security/get-started)
- [Firebase Emulator Suite](https://firebase.google.com/docs/emulator-suite)
- [Delete Firestore Data](https://firebase.google.com/docs/firestore/manage-data/delete-data)