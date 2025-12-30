'use client';

interface OverviewCardProps {
  title: string;
  amount: string;
  change: string;
  changeType: 'positive' | 'negative';
  bgColor: string;
}

export default function OverviewCard({
  title,
  amount,
  change,
  changeType,
  bgColor,
}: OverviewCardProps) {
  return (
    <div className={`${bgColor} rounded-lg p-6 flex flex-col gap-3 min-h-[140px] justify-between`}>
      <h3 className="text-gray-700 font-medium text-sm">{title}</h3>
      <div className="flex items-baseline gap-3">
        <span className="text-3xl font-bold text-gray-900">{amount}</span>
        <span
          className={`text-sm font-semibold ${
            changeType === 'positive' ? 'text-green-600' : 'text-red-600'
          }`}
        >
          {changeType === 'positive' ? '+' : ''}{change}
        </span>
      </div>
    </div>
  );
}
