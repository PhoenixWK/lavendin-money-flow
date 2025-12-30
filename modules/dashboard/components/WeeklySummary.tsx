'use client';

export default function WeeklySummary() {
  const days = [
    { day: 'Mo', value: 0 },
    { day: 'Tu', value: 60 },
    { day: 'We', value: 80 },
    { day: 'Th', value: 70 },
    { day: 'Fr', value: 40 },
    { day: 'Sa', value: 90 },
    { day: 'Su', value: 30 },
  ];

  const maxValue = Math.max(...days.map(d => d.value));

  return (
    <div className="bg-teal-700 rounded-lg p-6 text-white">
      <div className="flex items-center justify-between mb-6">
        <h3 className="font-semibold text-lg">Weekly Summary</h3>
        <span className="bg-white/20 px-3 py-1 rounded text-sm">$9,088</span>
      </div>

      <div className="flex items-end justify-between h-40 gap-2">
        {days.map((item, index) => (
          <div key={index} className="flex-1 flex flex-col items-center gap-2">
            <div
              className="w-full bg-white/30 rounded-t"
              style={{
                height: item.value > 0 ? `${(item.value / maxValue) * 120}px` : '4px',
                minHeight: '4px',
              }}
            ></div>
            <span className="text-xs font-medium">{item.day}</span>
          </div>
        ))}
      </div>
    </div>
  );
}
