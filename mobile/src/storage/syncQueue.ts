import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';

export interface QueueItem {
  id: string;
  url: string;
  method: 'POST' | 'PUT' | 'DELETE';
  payload: any;
  timestamp: number;
}

const QUEUE_KEY = 'offline_sync_queue';

export class SyncQueueManager {
  static async enqueue(url: string, method: 'POST' | 'PUT' | 'DELETE', payload: any) {
    const queue = await this.getQueue();
    const newItem: QueueItem = {
      id: Math.random().toString(36).substring(2, 9),
      url,
      method,
      payload,
      timestamp: Date.now(),
    };
    queue.push(newItem);
    await AsyncStorage.setItem(QUEUE_KEY, JSON.stringify(queue));
  }

  static async getQueue(): Promise<QueueItem[]> {
    const data = await AsyncStorage.getItem(QUEUE_KEY);
    return data ? JSON.parse(data) : [];
  }

  static async processQueue(baseUrl: string, token: string): Promise<number> {
    const queue = await this.getQueue();
    if (queue.length === 0) return 0;

    let processedCount = 0;
    const remainingQueue: QueueItem[] = [];

    for (const item of queue) {
      try {
        await axios({
          url: `${baseUrl}${item.url}`,
          method: item.method,
          data: item.payload,
          headers: { Authorization: `Bearer ${token}` },
        });
        processedCount++;
      } catch (err) {
        remainingQueue.push(item);
      }
    }

    await AsyncStorage.setItem(QUEUE_KEY, JSON.stringify(remainingQueue));
    return processedCount;
  }
}
