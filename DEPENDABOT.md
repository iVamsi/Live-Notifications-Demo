# Dependabot Configuration for Live Notifications Demo

This document explains the Dependabot setup and automated dependency management for the Live Notifications Demo project.

## 📋 Overview

Dependabot is configured to automatically monitor and update dependencies in this project, helping maintain security and keeping the codebase current with the latest stable versions.

## 🔧 Configuration

### Main Configuration (`.github/dependabot.yml`)

**Update Schedule:**

- **Gradle dependencies**: Weekly on Mondays at 9:00 AM EST
- **GitHub Actions**: Weekly on Mondays at 9:00 AM EST
- **Docker**: Monthly on the 1st at 9:00 AM EST

**Grouping Strategy:**
Dependencies are grouped logically for easier review:

- **android-core**: AndroidX and Android framework dependencies
- **compose**: Jetpack Compose related dependencies
- **kotlin**: Kotlin language and coroutines dependencies
- **testing**: Testing frameworks and utilities

**Review Process:**

- All PRs are automatically assigned to `@iVamsi`
- PRs are labeled with `dependencies`, package type, and `automated`
- Maximum 10 open PRs for Gradle dependencies at a time

## 🤖 Automated Workflows

### 1. Dependabot Auto-Merge (`.github/workflows/dependabot-auto-merge.yml`)

**Behavior by Update Type:**

- **Patch Updates** (1.0.0 → 1.0.1):

  - ✅ Automatically approved and merged after successful tests
  - Minimal risk, typically bug fixes and security patches

- **Minor Updates** (1.0.0 → 1.1.0):

  - ✅ Automatically merged after all CI checks pass
  - May include new features; build and tests must stay green

- **Major Updates** (1.0.0 → 2.0.0):
  - ✅ Automatically merged after all CI checks pass
  - A comment is added noting the major bump

**Build Process:**

1. Sets up JDK 17 and caches Gradle dependencies
2. Runs `./gradlew test assembleDebug lintDebug`
3. Waits for all other PR checks (security scan, Android CI) to finish
4. Auto-merges patch and minor updates when everything is green

### 2. Security Check (`.github/workflows/security-check.yml`)

**Regular Security Scanning:**

- Runs weekly on Sundays at 2 AM UTC
- Triggered on pushes to main/develop branches
- Triggered on pull requests to main

**OWASP Dependency Check:**

- Scans for known vulnerabilities in dependencies
- Uploads results to GitHub Security tab (SARIF format)
- Generates detailed reports in multiple formats
- Comments on PRs with security findings summary

**Dependency Updates Check:**

- Uses `com.github.ben-manes.versions` plugin
- Identifies outdated dependencies
- Generates update recommendations

## 🛡️ Security Configuration

### OWASP Suppressions (`config/owasp-suppressions.xml`)

Common false positives are suppressed to reduce noise:

- Android SDK components (managed by Google)
- Gradle wrapper (build-time only)
- Test dependencies (not shipped with app)
- Development tools

### NVD API Configuration

For faster and more accurate vulnerability data:

1. Get a free API key from [NIST NVD](https://nvd.nist.gov/developers/request-an-api-key)
2. Add as repository secret: `Settings → Secrets → Actions → NVD_API_KEY`
3. Without API key, scans will be slower but still functional

## 📊 Monitoring and Reports

### Generated Reports

**Security Reports:**

- `dependency-check-report.html` - Human-readable vulnerability report
- `dependency-check-report.sarif` - GitHub Security tab integration
- Available as workflow artifacts for 30 days

**Update Reports:**

- `build/dependencyUpdates/report.html` - Available dependency updates
- Shows current vs latest versions
- Categorizes by stability (stable, milestone, integration)

### GitHub Integration

**Security Tab:**

- View vulnerability findings directly in GitHub
- Track remediation status
- Historical security posture

**Pull Request Comments:**

- Automatic security scan summaries
- Update type identification
- Merge recommendations

## 🚀 Usage Instructions

### For Maintainers

**Reviewing Dependabot PRs:**

1. Check the auto-generated PR description
2. Review changelog/release notes for the dependency
3. Verify CI/CD pipeline passes
4. For minor/major updates, test functionality manually
5. Merge when confident in the update

**Handling Security Alerts:**

1. Review the vulnerability details in Security tab
2. Check if the vulnerability affects your usage
3. Update the dependency or add suppression if false positive
4. Document the decision in commit messages

**Managing Suppressions:**

1. Edit `config/owasp-suppressions.xml`
2. Add specific CVE suppressions with clear justification
3. Include expiration dates for review
4. Commit and push changes

### For Contributors

**Dependency Updates:**

- Dependabot will automatically propose updates
- No manual dependency updates needed in most cases
- Focus on feature development, not dependency management

**Security Awareness:**

- Check Security tab before adding new dependencies
- Consider security implications of new libraries
- Report any security concerns immediately

## 🔍 Troubleshooting

### Common Issues

**Build Failures:**

- Check compatibility between dependency versions
- Look for breaking changes in update changelogs
- Revert problematic updates if necessary

**False Positive Vulnerabilities:**

- Add suppressions to `owasp-suppressions.xml`
- Document the reason clearly
- Set expiration date for future review

**Too Many Open PRs:**

- Adjust `open-pull-requests-limit` in dependabot.yml
- Close outdated PRs manually if needed
- Consider more frequent review cycles

### Manual Commands

**Local Security Scan:**

```bash
./gradlew dependencyCheckAnalyze
```

**Check for Updates:**

```bash
./gradlew dependencyUpdates
```

**Clean Reports:**

```bash
./gradlew clean
rm -rf build/reports/
```

## 📈 Best Practices

1. **Regular Reviews**: Check Dependabot PRs weekly
2. **Test Thoroughly**: Always test minor/major updates
3. **Security First**: Prioritize security updates
4. **Document Decisions**: Clear commit messages for suppressions
5. **Stay Informed**: Follow security advisories for your dependencies
6. **Automate Wisely**: Auto-merge patches, review everything else

## 🔗 Additional Resources

- [Dependabot Documentation](https://docs.github.com/en/code-security/dependabot)
- [OWASP Dependency Check](https://owasp.org/www-project-dependency-check/)
- [GitHub Security Features](https://docs.github.com/en/code-security)
- [Semantic Versioning](https://semver.org/)

---

For questions or issues with dependency management, please open a GitHub issue or contact the maintainers.
