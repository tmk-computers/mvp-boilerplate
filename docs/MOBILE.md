# Mobile Application Specification (`MOBILE.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Technology: React Native | TypeScript | React Navigation v6 | SQLite | Offline Sync Queue | Keychain Biometrics

---

## 📱 1. Architecture Overview

The mobile module provides a cross-platform (Android & iOS ready) React Native framework built for field workforce and offline-first business scenarios.

```text
mobile/src/
├── components/          # Reusable Native UI Components
│   ├── Button.tsx
│   ├── Card.tsx
│   ├── Input.tsx
│   └── OfflineBanner.tsx
├── navigation/          # React Navigation Stack & Tab Configuration
│   ├── AppNavigator.tsx
│   └── AuthNavigator.tsx
├── screens/             # Application Screens
│   ├── LoginScreen.tsx
│   ├── DashboardScreen.tsx
│   ├── ProfileScreen.tsx
│   ├── OfflineSyncScreen.tsx
│   └── SettingsScreen.tsx
├── services/            # API Client & Device Feature Hardware Drivers
│   ├── api.ts           # Axios client with Secure Storage token handling
│   ├── biometric.ts     # Fingerprint & FaceID Authentication Service
│   └── camera.ts        # QR / Barcode & Document Scanner Helper
├── storage/             # SQLite & Secure Storage Abstraction
│   ├── sqlite.ts        # SQLite Local Database Manager
│   └── syncQueue.ts     # Offline Mutation Sync Engine
└── types/              # TypeScript Type Definitions
```

---

## 🔄 2. Offline-First Sync Queue Architecture

When network connectivity is unavailable, all database write operations (create, update, delete) are recorded locally in a SQLite `sync_queue` table:

```text
User Action (Offline)
       │
       ▼
 Save to SQLite DB & Append to sync_queue
       │
       ▼
 Network Reconnected (NetInfo Listener Trigger)
       │
       ▼
 Process Sync Queue sequentially via API
       │
       ├── Success ──> Remove item from sync_queue
       └── Conflict ─> Trigger Conflict Resolution Handler (Client Wins / Server Wins)
```

### `syncQueue.ts` Core Engine Contract
```typescript
export interface SyncQueueItem {
  id: string;
  endpoint: string;
  method: 'POST' | 'PUT' | 'DELETE';
  payload: any;
  createdAt: number;
  retryCount: number;
}

export class SyncQueueManager {
  static async enqueue(endpoint: string, method: 'POST' | 'PUT' | 'DELETE', payload: any) {
    // Insert into local SQLite table 'sync_queue'
  }

  static async processQueue() {
    // Read pending items, post to backend API, handle automatic token auth & retries
  }
}
```

---

## 🔒 3. Biometric Authentication & Secure Storage

- Sensitive tokens (Access Token, Refresh Token) are stored securely using platform Keychain / Keystore (`react-native-keychain` / Expo SecureStore).
- Optional fingerprint and Face ID login allows seamless re-authentication without prompting for passwords repeatedly in field conditions.

---

## 📷 4. Hardware Integrations

- **QR & Barcode Scanner**: Built-in support for reading asset tags, product SKUs, and invoice QR codes.
- **Camera & Image Upload**: Automated image compression and offline payload attachment upload.
