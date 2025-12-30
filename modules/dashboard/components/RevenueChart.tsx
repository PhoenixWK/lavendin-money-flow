'use client';

export default function RevenueChart() {
  const data = [
    { date: '12 Sep', income: 50, business: 100, personal: 96 },
    { date: '13 Sep', income: 100, business: 120, personal: 150 },
    { date: '14 Sep', income: 160, business: 120, personal: 61 },
    { date: '15 Sep', income: 90, business: 120, personal: 97 },
    { date: '16 Sep', income: 130, business: 150, personal: 61 },
    { date: '17 Sep', income: 100, business: 200, personal: 70 },
    { date: '18 Sep', income: 120, business: 50, personal: 70 },
    { date: '19 Sep', income: 130, business: 150, personal: 60 },
    { date: '20 Sep', income: 110, business: 90, personal: 70 },
    { date: '21 Sep', income: 90, business: 150, personal: 50 },
    { date: '22 Sep', income: 180, business: 60, personal: 120 },
    { date: '23 Sep', income: 80, business: 120, personal: 110 },
    { date: '24 Sep', income: 30, business: 40, personal: 30 },
    { date: '25 Sep', income: 60, business: 120, personal: 90 },
  ];

  const maxTotal = Math.max(...data.map(d => d.income + d.business + d.personal));

  return (
    <div className="bg-white rounded-lg p-6 border border-gray-200">
      <div className="flex items-center justify-between mb-6">
        <h3 className="text-lg font-semibold text-gray-900">Revenue</h3>
        <div className="flex gap-4">
          {['1 year', '2 Weeks', '1 Month', '3 months'].map((period) => (
            <button
              key={period}
              className={`text-sm px-3 py-1 rounded ${
                period === '1 year'
                  ? 'text-gray-900 font-medium'
                  : 'text-gray-500 hover:text-gray-700'
              }`}
            >
              {period}
            </button>
          ))}
        </div>
      </div>

      {/* Y-axis labels */}
      <div className="flex gap-4">
        <div className="flex flex-col justify-between text-xs text-gray-500 h-64 -mt-2 -mb-4">
          <span>450</span>
          <span>400</span>
          <span>350</span>
          <span>300</span>
          <span>250</span>
          <span>200</span>
          <span>150</span>
          <span>100</span>
          <span>50</span>
          <span>0</span>
        </div>

        {/* Chart bars */}
        <div className="flex-1 flex items-end justify-between gap-2 h-64 border-l border-gray-200 pl-4">
          {data.map((item, index) => {
            const total = item.income + item.business + item.personal;
            const incomeHeight = (item.income / maxTotal) * 100;
            const businessHeight = (item.business / maxTotal) * 100;
            const personalHeight = (item.personal / maxTotal) * 100;

            return (
              <div key={index} className="flex-1 flex flex-col items-center gap-1">
                <div className="w-full flex flex-col items-stretch h-full relative group">
                  <div className="flex-1 flex flex-col justify-end">
                    {/* Income (green) */}
                    <div
                      className="bg-green-400 w-full"
                      style={{ height: `${incomeHeight}%` }}
                    ></div>
                    {/* Business (green darker) */}
                    <div
                      className="bg-green-600 w-full"
                      style={{ height: `${businessHeight}%` }}
                    ></div>
                    {/* Personal (pink/magenta) */}
                    <div
                      className="bg-pink-500 w-full"
                      style={{ height: `${personalHeight}%` }}
                    ></div>
                  </div>
                </div>
                <span className="text-xs text-gray-500 mt-2 whitespace-nowrap">
                  {item.date.split(' ')[0]}
                </span>
              </div>
            );
          })}
        </div>
      </div>

      {/* Legend */}
      <div className="flex gap-6 mt-6 text-xs">
        <div className="flex items-center gap-2">
          <div className="w-3 h-3 bg-green-400 rounded"></div>
          <span className="text-gray-600">Income</span>
        </div>
        <div className="flex items-center gap-2">
          <div className="w-3 h-3 bg-green-600 rounded"></div>
          <span className="text-gray-600">Business</span>
        </div>
        <div className="flex items-center gap-2">
          <div className="w-3 h-3 bg-pink-500 rounded"></div>
          <span className="text-gray-600">Personal</span>
        </div>
      </div>
    </div>
  );
}
