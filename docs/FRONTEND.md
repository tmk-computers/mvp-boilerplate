# Web Frontend Technical Specification (`FRONTEND.md`)

> **DASP Digital MVP Boilerplate Framework**  
> Technology: React 18/19 | Vite | TypeScript | Tailwind CSS | TanStack Query v5 | React Router v6 | Axios | React Hook Form + Zod

---

## 🏛️ 1. Architecture Overview

The web application provides a responsive, role-gated admin portal and enterprise business module shell.

```text
frontend/src/
├── assets/             # Static logos, fonts, images
├── components/         # Reusable UI Component Library
│   ├── common/         # Buttons, Inputs, Selects, Tables, Modals, Dynamic Forms
│   └── layout/         # Sidebar, Navbar, Footer, AppLayout, PermissionGate
├── context/            # Global Auth & Theme Context Providers
├── hooks/              # Custom Hooks (useAuth, usePermissions, useDebounce, useApi)
├── pages/              # Admin Pages (Users, Roles, Organizations, Audit Logs, Settings)
├── services/           # Axios HTTP Client & API Contract Services
├── types/              # Global TypeScript Models & Response Interfaces
├── utils/              # Formatter, Storage & Helper Utilities
├── App.tsx             # Route Definitions & Provider Hierarchy
└── main.tsx            # Application Entry Point
```

---

## 🔒 2. Authorization & Permission Component Gate

Components and menu items are conditionally rendered based on fine-grained backend permissions.

### `<PermissionGate />` Component
```tsx
import React from 'react';
import { usePermissions } from '@/hooks/usePermissions';

interface PermissionGateProps {
  permission: string;
  children: React.ReactNode;
  fallback?: React.ReactNode;
}

export const PermissionGate: React.FC<PermissionGateProps> = ({
  permission,
  children,
  fallback = null,
}) => {
  const { hasPermission } = usePermissions();

  if (!hasPermission(permission)) {
    return <>{fallback}</>;
  }

  return <>{children}</>;
};
```

### Usage Example
```tsx
<PermissionGate permission="Customer.Create">
  <Button onClick={openCreateModal} variant="primary">
    + Add Customer
  </Button>
</PermissionGate>
```

---

## 🔄 3. Axios Interceptor & Silent Token Refresh

The Axios HTTP client automatically handles JWT bearer token injection, 401 Unauthorized responses, silent refresh token rotation, and error standardizations.

```typescript
import axios from 'axios';

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      try {
        const refreshToken = localStorage.getItem('refreshToken');
        const res = await axios.post('/api/v1/auth/refresh', { refreshToken });
        const newAccessToken = res.data.data.accessToken;
        
        localStorage.setItem('accessToken', newAccessToken);
        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        return api(originalRequest);
      } catch (refreshErr) {
        localStorage.clear();
        window.location.href = '/login';
        return Promise.reject(refreshErr);
      }
    }
    return Promise.reject(error);
  }
);
```

---

## 🎨 4. Theme & Design Tokens

The application features dark/light mode toggle with vibrant modern HSL CSS variables and Tailwind CSS:

```css
@tailwind base;
@tailwind components;
@tailwind utilities;

@layer base {
  :root {
    --background: 220 20% 97%;
    --foreground: 224 71.4% 4.1%;
    --primary: 221.2 83.2% 53.3%;
    --primary-foreground: 210 20% 98%;
  }

  .dark {
    --background: 224 71.4% 4.1%;
    --foreground: 210 20% 98%;
    --primary: 217.2 91.2% 59.8%;
    --primary-foreground: 222.2 47.4% 11.2%;
  }
}
```

---

## 🧩 5. Reusable Component Inventory

The frontend architecture includes production-ready components:
1. `Table`: Built-in sorting, filtering, searching, server-side pagination, and row actions.
2. `DynamicForm`: Renders complex forms dynamically from JSON field schemas with Zod validation.
3. `FileUpload`: Drag-and-drop file uploader with progress indicator and upload strategy routing.
4. `Toast`: Global notification toast stack (Success, Error, Warning, Info).
5. `Modal`: Accessible dialog popups with backdrop blur and keyboard escape handlers.
6. `DatePicker`: Custom date & time range picker component.
