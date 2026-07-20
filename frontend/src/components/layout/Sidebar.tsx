import React from 'react';
import { NavLink } from 'react-router-dom';
import { LayoutDashboard, Users, ShieldCheck, Building, History, Settings } from 'lucide-react';

export const Sidebar: React.FC = () => {
  const menuItems = [
    { label: 'Dashboard', path: '/dashboard', icon: LayoutDashboard },
    { label: 'Users', path: '/users', icon: Users },
    { label: 'Roles & Permissions', path: '/roles', icon: ShieldCheck },
    { label: 'Organizations', path: '/organizations', icon: Building },
    { label: 'Audit Logs', path: '/audit-logs', icon: History },
    { label: 'Settings', path: '/settings', icon: Settings },
  ];

  return (
    <aside className="w-64 bg-white border-r border-gray-200 min-h-screen dark:bg-gray-800 dark:border-gray-700 flex flex-col">
      <div className="p-6 border-b border-gray-200 dark:border-gray-700">
        <h1 className="text-xl font-bold text-primary-600 dark:text-primary-400">DASP Digital</h1>
        <p className="text-xs text-gray-500 dark:text-gray-400">MVP Framework v1.0</p>
      </div>
      <nav className="flex-1 p-4 space-y-1">
        {menuItems.map((item) => {
          const Icon = item.icon;
          return (
            <NavLink
              key={item.path}
              to={item.path}
              className={({ isActive }) =>
                `flex items-center px-4 py-2.5 text-sm font-medium rounded-lg transition-colors ${
                  isActive
                    ? 'bg-primary-50 text-primary-600 dark:bg-gray-700 dark:text-primary-400'
                    : 'text-gray-600 hover:bg-gray-50 dark:text-gray-300 dark:hover:bg-gray-700'
                }`
              }
            >
              <Icon className="w-5 h-5 mr-3" />
              {item.label}
            </NavLink>
          );
        })}
      </nav>
    </aside>
  );
};
