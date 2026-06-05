# Live Notifications Demo

Small Android app that demos **Android 16 Live Updates** — delivery tracking with `Notification.ProgressStyle`. Segments, ETA, and action buttons update in real time from a foreground service.

![Live Updates Demo](live_updates_demo.gif)

## What it does

- Simulates a food delivery: Confirmed → Preparing → En Route → Delivered
- Shows a progress-style notification with colored segments and phase-specific actions
- Compose UI to start/stop the demo; handles notification permission on Android 13+
- Falls back gracefully on devices below Android 16

## Requirements

- Android Studio Panda / Quail (2025.3+)
- JDK 17
- Gradle 9.4+ / AGP 9.2+
- **Android 16 (API 36) device or emulator** for full Live Updates UI
- Min SDK 28, target SDK 36

## Run it

```bash
git clone https://github.com/iVamsi/Live-Notifications-Demo.git
cd Live-Notifications-Demo
```

Open in Android Studio, sync Gradle, run on an Android 16 emulator or device. Tap **Start Demo Delivery**, allow notifications, pull down the shade to watch updates.

From the command line:

```bash
./gradlew assembleDebug
./gradlew test
```

## Project layout

| Piece | Role |
| --- | --- |
| `MainActivity` | Compose UI, permissions, start/stop delivery |
| `DeliveryTrackingService` | Foreground service, coroutine-driven status updates |
| `LiveUpdatesNotificationBuilder` | Builds `Notification.ProgressStyle` notifications |
| `DeliveryStatus` | Enum for stages, progress, and copy |

## Customize timing

In `DeliveryTrackingService`:

```kotlin
private const val SEGMENT_UPDATE_DELAY_MS = 3000L
private const val STATUS_TRANSITION_DELAY_MS = 6000L
```

## License

MIT — see [LICENSE](LICENSE).

## Links

- [Android 16 Live Updates](https://developer.android.com/about/versions/16)
- [Notification.ProgressStyle](https://developer.android.com/reference/android/app/Notification.ProgressStyle)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
