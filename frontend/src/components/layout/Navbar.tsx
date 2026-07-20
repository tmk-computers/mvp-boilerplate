import React from 'react';
import { useAuth } from '@/context/AuthContext';
import { LogOut, User as UserIcon } from 'lucide-react';
import { Button } from '@/components/common/Button';

export const Navbar: React.FC = () => {
  const { user, logout } = useAuth();

  return (
    <header className="h-16 bg-white border-b border-gray-200 px-6 flex items-center justify-between dark:bg-gray-800 dark:border-gray-700">
      <div className="text-sm font-medium text-gray-500 dark:text-gray-400">
        Enterprise Application Portal
      </div>
      <div className="flex items-center space-x-4">
        <div className="flex items-center space-x-2 text-sm font-medium text-gray-700 dark:text-gray-200">
          <UserIcon className="w-4 h-4" />
          <span>{user?.firstName} {user?.lastName}</span>
        </div>
        <Button variant="outline" size="sm" onClick={logout}>
          <LogOut className="w-4 h-4 mr-1" />
          Logout
        </Button>
      </div>
    </header>
  );
};
