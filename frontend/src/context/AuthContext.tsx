import React, { createContext, useContext, useState, useEffect } from 'react';

interface UserProfile {
  userId: string;
  email: string;
  firstName: string;
  lastName: string;
  permissions: string[];
}

interface AuthContextType {
  user: UserProfile | null;
  isAuthenticated: boolean;
  login: (data: any) => void;
  logout: () => void;
  hasPermission: (permission: string) => boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<UserProfile | null>(() => {
    const savedUser = localStorage.getItem('user');
    return savedUser ? JSON.parse(savedUser) : null;
  });

  const login = (data: any) => {
    localStorage.setItem('accessToken', data.accessToken);
    localStorage.setItem('refreshToken', data.refreshToken);
    const profile: UserProfile = {
      userId: data.userId,
      email: data.email,
      firstName: data.firstName,
      lastName: data.lastName,
      permissions: data.permissions || [],
    };
    localStorage.setItem('user', JSON.stringify(profile));
    setUser(profile);
  };

  const logout = () => {
    localStorage.clear();
    setUser(null);
  };

  const hasPermission = (permission: string) => {
    if (!user) return false;
    return user.permissions.includes(permission);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated: !!user,
        login,
        logout,
        hasPermission,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error('useAuth must be used within an AuthProvider');
  return context;
};
