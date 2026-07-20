import React from 'react';
import { Sparkles, Plus, Edit2, History } from 'lucide-react';
import { Button } from '@/components/common/Button';

export const AdminAIPromptsPage: React.FC = () => {
  const prompts = [
    { name: 'Customer Invoice Summarizer', category: 'Finance', version: 'v2.1', active: true },
    { name: 'HR Candidate Resume Extraction', category: 'HRMS', version: 'v1.4', active: true },
    { name: 'Inventory Re-order Prediction', category: 'Supply Chain', version: 'v3.0', active: true },
  ];

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white flex items-center gap-2">
            <Sparkles className="w-6 h-6 text-primary-600" />
            Prompt & Template Management
          </h1>
          <p className="text-sm text-gray-500 dark:text-gray-400">
            Configure dynamic variables, versioning, and prompt template engines.
          </p>
        </div>
        <Button variant="primary">
          <Plus className="w-4 h-4 mr-2" />
          Create Prompt
        </Button>
      </div>

      <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 overflow-hidden shadow-sm">
        <table className="w-full text-left text-sm">
          <thead className="bg-gray-50 dark:bg-gray-700 text-gray-600 dark:text-gray-300">
            <tr>
              <th className="p-4">Prompt Name</th>
              <th className="p-4">Category</th>
              <th className="p-4">Active Version</th>
              <th className="p-4">Status</th>
              <th className="p-4 text-right">Actions</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-200 dark:divide-gray-700">
            {prompts.map((p, idx) => (
              <tr key={idx} className="hover:bg-gray-50 dark:hover:bg-gray-750">
                <td className="p-4 font-medium text-gray-900 dark:text-white">{p.name}</td>
                <td className="p-4 text-gray-500">{p.category}</td>
                <td className="p-4"><span className="px-2.5 py-1 text-xs font-semibold bg-blue-100 text-blue-800 rounded-full">{p.version}</span></td>
                <td className="p-4"><span className="px-2.5 py-1 text-xs font-semibold bg-green-100 text-green-800 rounded-full">Active</span></td>
                <td className="p-4 text-right space-x-2">
                  <Button variant="outline" size="sm"><Edit2 className="w-3.5 h-3.5" /></Button>
                  <Button variant="outline" size="sm"><History className="w-3.5 h-3.5" /></Button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
