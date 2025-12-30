'use client';

interface ScheduledPayment {
  id: string;
  name: string;
  date: string;
  amount: number;
  icon: string;
  color: string;
}

const payments: ScheduledPayment[] = [
  {
    id: '1',
    name: 'Netflix',
    date: '21 Sep 2022',
    amount: -9.99,
    icon: 'N',
    color: 'bg-red-500',
  },
  {
    id: '2',
    name: 'Microsoft Inc.',
    date: '21 Sep 2022',
    amount: -19.99,
    icon: 'M',
    color: 'bg-teal-500',
  },
  {
    id: '3',
    name: 'Figma',
    date: '21 Sep 2022',
    amount: -5.99,
    icon: 'F',
    color: 'bg-blue-500',
  },
  {
    id: '4',
    name: 'FedExpress',
    date: '21 Sep 2022',
    amount: -829.99,
    icon: 'F',
    color: 'bg-blue-700',
  },
  {
    id: '5',
    name: 'FedExpress',
    date: '21 Sep 2022',
    amount: -829.99,
    icon: 'F',
    color: 'bg-red-500',
  },
  {
    id: '6',
    name: 'Figma',
    date: '21 Sep 2022',
    amount: -5.99,
    icon: 'F',
    color: 'bg-purple-500',
  },
];

export default function ScheduledPayments() {
  return (
    <div className="bg-white rounded-lg p-6 border border-gray-200">
      <h3 className="text-lg font-semibold text-gray-900 mb-4">Scheduled Payments</h3>

      <div className="space-y-3 max-h-96 overflow-y-auto">
        {payments.map((payment) => (
          <div
            key={payment.id}
            className="flex items-center justify-between p-3 hover:bg-gray-50 rounded-lg transition-colors"
          >
            <div className="flex items-center gap-3">
              <div className={`${payment.color} w-10 h-10 rounded-lg flex items-center justify-center text-white font-semibold text-sm`}>
                {payment.icon}
              </div>
              <div>
                <h4 className="font-semibold text-gray-900 text-sm">{payment.name}</h4>
                <p className="text-xs text-gray-500">{payment.date}</p>
              </div>
            </div>
            <span className="font-semibold text-gray-900 text-sm">
              ${Math.abs(payment.amount).toFixed(2)}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
}
