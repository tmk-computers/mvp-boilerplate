import React from 'react';
import { Users, Shield, Activity, Server } from 'lucide-react';

export const Dashboard: React.FC = () => {
  const stats = [
    { label: 'Active Users', value: '1,248', change: '+12%', icon: Users },
    { label: 'System Roles', value: '8', change: 'Stable', icon: Shield },
    { label: 'API Health', value: '99.98%', change: 'Operational', icon: Activity },
    { label: 'Active Sessions', value: '342', change: '+5%', icon: Server },
  ];

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Enterprise Dashboard</h1>
        <p className="text-sm text-gray-500 dark:text-gray-400">System metrics and operational status overview</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {stats.map((stat, idx) => {
          const Icon = stat.icon;
          return (
            <div key={idx} className="bg-white dark:bg-gray-800 p-6 rounded-xl border border-gray-200 dark:border-gray-700 shadow-sm flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-gray-500 dark:text-gray-400">{stat.label}</p>
                <p className="text-2xl font-bold text-gray-900 dark:text-white mt-1">{stat.value}</p>
                <span className="text-xs text-green-600 font-medium">{stat.change}</span>
              </div>
              <div className="p-3 bg-primary-50 dark:bg-gray-700 text-primary-600 dark:text-primary-400 rounded-lg">
                <Icon className="w-6 h-6" />
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};
