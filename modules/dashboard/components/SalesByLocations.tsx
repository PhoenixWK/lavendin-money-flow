'use client';

export default function SalesByLocations() {
  const locations = [
    { country: 'United States', flag: '🇺🇸', percentage: 74 },
    { country: 'France', flag: '🇫🇷', percentage: 11 },
    { country: 'Brazil', flag: '🇧🇷', percentage: 5 },
    { country: 'Others', flag: '🌍', percentage: 10 },
  ];

  return (
    <div className="bg-white rounded-lg p-6 border border-gray-200">
      <h3 className="text-lg font-semibold text-gray-900 mb-6">Sales by Locations</h3>

      <div className="space-y-4">
        {locations.map((item, index) => (
          <div key={index} className="flex items-center justify-between">
            <div className="flex items-center gap-3">
              <span className="text-2xl">{item.flag}</span>
              <span className="text-gray-700 font-medium">{item.country}</span>
            </div>
            <div className="flex items-center gap-4 flex-1 ml-4">
              <div className="flex-1 bg-gray-100 rounded-full h-2">
                <div
                  className="bg-gradient-to-r from-blue-500 to-purple-500 h-full rounded-full"
                  style={{ width: `${item.percentage}%` }}
                ></div>
              </div>
              <span className="font-semibold text-gray-900 w-12 text-right">
                {item.percentage}%
              </span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
