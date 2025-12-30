'use client';

import { MoreVertical } from 'lucide-react';

interface Transaction {
  id: string;
  name: string;
  type: string;
  date: string;
  amount: number;
  icon: string;
  color: string;
}

const transactions: Transaction[] = [
  {
    id: '1',
    name: 'McDonal Offical',
    type: 'Online Payment',
    date: '15 Sep 2022 9:00 am',
    amount: -782.01,
    icon: 'M',
    color: 'bg-green-500',
  },
  {
    id: '2',
    name: 'Razer',
    type: 'Cash',
    date: '15 Sep 2022 12:34 pm',
    amount: 576.28,
    icon: 'R',
    color: 'bg-blue-600',
  },
  {
    id: '3',
    name: 'Ralph Edwards',
    type: 'Online Payment',
    date: '11 Sep 2022 6:00 Pm',
    amount: -106.58,
    icon: 'R',
    color: 'bg-red-500',
  },
  {
    id: '4',
    name: 'MyKart LLC',
    type: 'Online Payment',
    date: '10 Sep 2022 4:30 Pm',
    amount: -312.28,
    icon: 'M',
    color: 'bg-orange-500',
  },
];

export default function LatestTransactions() {
  return (
    <div className="bg-white rounded-lg p-6 border border-gray-200">
      <h3 className="text-lg font-semibold text-gray-900 mb-4">Latest Transactions</h3>

      <div className="space-y-4">
        {transactions.map((transaction) => (
          <div
            key={transaction.id}
            className="flex items-center justify-between p-4 hover:bg-gray-50 rounded-lg transition-colors"
          >
            <div className="flex items-center gap-4 flex-1">
              <div className={`${transaction.color} w-12 h-12 rounded-lg flex items-center justify-center text-white font-semibold`}>
                {transaction.icon}
              </div>
              <div className="flex-1">
                <h4 className="font-semibold text-gray-900">{transaction.name}</h4>
                <p className="text-sm text-gray-500">{transaction.type}</p>
              </div>
            </div>

            <div className="flex items-center gap-4">
              <div className="text-right">
                <p className="text-sm text-gray-500">{transaction.date}</p>
              </div>
              <span
                className={`font-semibold ${
                  transaction.amount < 0 ? 'text-gray-900' : 'text-green-600'
                }`}
              >
                {transaction.amount < 0 ? '-' : '+'}${Math.abs(transaction.amount).toFixed(2)}
              </span>
              <button className="p-1 hover:bg-gray-100 rounded text-gray-400">
                <MoreVertical size={20} />
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
