# Contributing to SpendWise

Thank you for your interest in contributing to SpendWise.

SpendWise is a native Android personal finance management application built with Kotlin and Jetpack Compose.

This document establishes the guidelines for contributing to the project.

## 1. Contribution Principles

All contributions should prioritize:

- Code correctness and reliability.
- Security and user privacy.
- Maintainable architecture.
- Consistent user experience.
- Financial calculation accuracy.
- Clear documentation.

Contributions should follow the established MVVM and Clean Architecture principles.

## 2. Development Environment

Before contributing, ensure that you have:

- Android Studio.
- JDK 21.
- Android SDK.
- Kotlin development support.
- Git.
- An Android emulator or physical Android device.

Refer to [DEVELOPMENT.md](DEVELOPMENT.md) for project setup and build instructions.

## 3. Project Structure

Follow the established package organization:

- `data/` — Data sources, repository implementations, and mappers.
- `domain/` — Business models, repository interfaces, and use cases.
- `presentation/` — Compose screens, ViewModels, navigation, and reusable components.
- `di/` — Dependency injection configuration.
- `ui/theme/` — Application theme and styling.

Do not introduce unnecessary architectural layers or dependencies.

## 4. Coding Guidelines

- Follow Kotlin coding conventions.
- Use descriptive names for classes, functions, and variables.
- Keep functions focused on a single responsibility.
- Keep business logic outside composable functions.
- Prefer immutable state.
- Handle asynchronous operations using Kotlin Coroutines.
- Use Flow for observable data streams.
- Reuse existing components where appropriate.
- Avoid duplicated logic.
- Handle errors explicitly.

## 5. Financial Data Requirements

Financial calculations require particular care.

- Store monetary values as integer paise.
- Do not use Float or Double for financial calculations.
- Validate transaction amounts before saving.
- Ensure income and expense calculations are correct.
- Ensure budget utilization and remaining amounts are accurate.
- Never introduce fabricated financial records into production functionality.
- Use clearly identified synthetic data in tests and demonstrations.

## 6. Security and Privacy Requirements

Security is mandatory for every contribution.

Never commit or publish:

- Passwords or access tokens.
- API secrets or private credentials.
- Firebase service account private keys.
- Real user financial records.
- Personal information.
- `google-services.json`.
- Signing keystores or signing credentials.
- Machine-specific configuration files.
- Database exports containing private information.

Firebase configuration and credentials must remain outside the public repository.

All Firestore operations must respect authenticated user ownership.

Client-side validation must never replace server-side authorization.

Security-sensitive changes must be reviewed and tested before merging.

## 7. Testing Requirements

Contributors should add relevant tests for new functionality.

Depending on the change, testing may include:

- Unit tests.
- Repository tests.
- ViewModel tests.
- Jetpack Compose UI tests.
- Firebase Emulator Suite tests.
- Firestore Security Rules tests.

All relevant tests should pass before a contribution is considered ready.

## 8. Git and Commit Guidelines

Use the `main` branch as the default branch.

Follow Conventional Commits.

Examples:

```text
feat: add transaction management
fix: correct expense calculation
docs: update Firebase setup guide
refactor: simplify dashboard ViewModel
test: add budget calculation tests
chore: update Gradle dependencies
```

Commit messages should describe the actual changes.

Keep commits focused and avoid mixing unrelated changes.

## 9. Pull Request Guidelines

Before submitting a pull request:

1. Ensure the application builds successfully.
2. Run relevant tests.
3. Review the complete Git diff.
4. Check for accidentally included generated files.
5. Check for secrets and personal information.
6. Confirm that the contribution follows the project architecture.
7. Provide a clear description of the changes.
8. Include relevant screenshots for UI changes where appropriate.

Pull requests should explain:

- What changed.
- Why the change was necessary.
- How it was tested.
- Any relevant limitations or known issues.

## 10. Issue Reporting

When reporting a bug, include:

- A clear description of the problem.
- Steps to reproduce it.
- Expected behavior.
- Actual behavior.
- Relevant error messages.
- Android version and device information, when relevant.

Never include passwords, credentials, personal financial records, or other confidential information in issues.

## 11. Scope of Contributions

Changes should remain consistent with the project's goals and established roadmap.

Major architectural changes, new dependencies, and modifications to financial data handling should be discussed before implementation.

## 12. License

By contributing to SpendWise, you agree that your contributions will be distributed under the project's MIT License.

See the [LICENSE](../LICENSE) file for details.