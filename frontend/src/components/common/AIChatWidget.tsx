import React, { useState } from 'react';
import { Bot, Send, X, Sparkles } from 'lucide-react';
import { api } from '@/services/api';
import { Button } from '@/components/common/Button';

interface Message {
  sender: 'user' | 'ai';
  text: string;
  provider?: string;
  latencyMs?: number;
}

export const AIChatWidget: React.FC = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [prompt, setPrompt] = useState('');
  const [loading, setLoading] = useState(false);
  const [messages, setMessages] = useState<Message[]>([
    { sender: 'ai', text: 'Hello! I am your Enterprise AI Assistant. How can I assist your business workflow today?' },
  ]);

  const handleSend = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!prompt.trim() || loading) return;

    const userMsg = prompt;
    setPrompt('');
    setMessages((prev) => [...prev, { sender: 'user', text: userMsg }]);
    setLoading(true);

    try {
      const res = await api.post('/ai/chat', { prompt: userMsg });
      const data = res.data.data;
      setMessages((prev) => [
        ...prev,
        {
          sender: 'ai',
          text: data.text,
          provider: data.providerName,
          latencyMs: data.latencyMs,
        },
      ]);
    } catch (err: any) {
      setMessages((prev) => [
        ...prev,
        { sender: 'ai', text: 'Sorry, an error occurred while processing your AI request.' },
      ]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed bottom-6 right-6 z-50">
      {!isOpen && (
        <button
          onClick={() => setIsOpen(true)}
          className="bg-primary-600 hover:bg-primary-700 text-white p-4 rounded-full shadow-xl flex items-center justify-center transition-all hover:scale-105"
        >
          <Sparkles className="w-6 h-6 animate-pulse" />
        </button>
      )}

      {isOpen && (
        <div className="w-96 h-[500px] bg-white dark:bg-gray-800 rounded-2xl shadow-2xl border border-gray-200 dark:border-gray-700 flex flex-col overflow-hidden">
          <div className="bg-primary-600 p-4 text-white flex items-center justify-between">
            <div className="flex items-center space-x-2">
              <Bot className="w-5 h-5" />
              <span className="font-semibold text-sm">Enterprise AI Assistant</span>
            </div>
            <button onClick={() => setIsOpen(false)} className="hover:opacity-80">
              <X className="w-5 h-5" />
            </button>
          </div>

          <div className="flex-1 p-4 overflow-y-auto space-y-3">
            {messages.map((msg, idx) => (
              <div
                key={idx}
                className={`flex flex-col ${msg.sender === 'user' ? 'items-end' : 'items-start'}`}
              >
                <div
                  className={`max-w-[80%] p-3 rounded-xl text-sm ${
                    msg.sender === 'user'
                      ? 'bg-primary-600 text-white rounded-br-none'
                      : 'bg-gray-100 dark:bg-gray-700 text-gray-800 dark:text-gray-200 rounded-bl-none'
                  }`}
                >
                  {msg.text}
                </div>
                {msg.provider && (
                  <span className="text-[10px] text-gray-400 mt-1">
                    {msg.provider} • {msg.latencyMs}ms
                  </span>
                )}
              </div>
            ))}
            {loading && (
              <div className="text-xs text-gray-400 italic">AI Assistant is thinking...</div>
            )}
          </div>

          <form onSubmit={handleSend} className="p-3 border-t border-gray-200 dark:border-gray-700 flex items-center space-x-2">
            <input
              type="text"
              placeholder="Ask AI assistant..."
              value={prompt}
              onChange={(e) => setPrompt(e.target.value)}
              className="flex-1 px-3 py-2 text-sm border border-gray-300 dark:border-gray-600 rounded-lg focus:outline-none focus:ring-1 focus:ring-primary-500 dark:bg-gray-700 dark:text-white"
            />
            <Button type="submit" size="sm" isLoading={loading}>
              <Send className="w-4 h-4" />
            </Button>
          </form>
        </div>
      )}
    </div>
  );
};
