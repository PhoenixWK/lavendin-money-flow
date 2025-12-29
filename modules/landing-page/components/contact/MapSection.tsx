'use client';

import Image from 'next/image';

export const MapSection = () => {
  return (
    <section className="bg-white py-16 md:py-24">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="relative w-full h-96 md:h-125 bg-[#e8f5e9] rounded-lg overflow-hidden">
          {/* SVG World Map */}
          <svg
            viewBox="0 0 1200 600"
            className="w-full h-full"
            xmlns="http://www.w3.org/2000/svg"
          >
            {/* Light background for water */}
            <rect width="1200" height="600" fill="#e8f5e9" />

            {/* Simplified world map outline */}
            <g fill="#c8e6c9" stroke="#90c690" strokeWidth="0.5">
              {/* North America */}
              <path d="M 150 100 L 200 80 L 220 140 L 180 160 L 150 100 Z" />
              {/* Central America & South America */}
              <path d="M 180 160 L 200 250 L 220 380 L 180 340 L 150 200 Z" />
              {/* Europe */}
              <path d="M 420 80 L 480 100 L 470 150 L 420 140 Z" />
              {/* Africa */}
              <path d="M 480 150 L 540 120 L 560 300 L 500 340 L 480 150 Z" />
              {/* Asia */}
              <path d="M 540 100 L 700 80 L 750 200 L 700 300 L 540 280 Z" />
              {/* Australia */}
              <path d="M 750 380 L 800 350 L 810 420 L 760 430 Z" />
            </g>

            {/* Marker for New York location */}
            <g>
              {/* Marker circle background */}
              <circle cx="250" cy="200" r="30" fill="#47EB98" opacity="0.2" />
              {/* Marker dot */}
              <circle cx="250" cy="200" r="12" fill="#47EB98" stroke="white" strokeWidth="3" />
              {/* Marker pin */}
              <path
                d="M 250 215 L 240 240 C 240 245 245 250 250 250 C 255 250 260 245 260 240 L 250 215 Z"
                fill="#47EB98"
              />
            </g>

            {/* Location label */}
            <g>
              <rect x="200" y="110" width="100" height="30" fill="#47EB98" rx="4" />
              <text
                x="250"
                y="130"
                textAnchor="middle"
                fill="black"
                fontSize="14"
                fontWeight="600"
                fontFamily="system-ui, -apple-system, sans-serif"
              >
                We are here
              </text>
            </g>
          </svg>
        </div>
      </div>
    </section>
  );
};
