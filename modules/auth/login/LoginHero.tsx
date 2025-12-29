'use client';

import { useState, useEffect } from 'react';
import { ChevronLeft, ChevronRight } from 'lucide-react';

interface CarouselSlide {
  id: number;
  title: string;
  description: string;
  icon: string;
}

const slides: CarouselSlide[] = [
  {
    id: 1,
    title: 'Manage your finances',
    description:
      'Take control of your personal and business finances with seamless expense tracking, automated bill reminders, insightful reports, and top-tier security features, all designed to simplify your financial management journey.',
    icon: '💰',
  },
  {
    id: 2,
    title: 'Track expenses easily',
    description:
      'Effortlessly monitor your spending with real-time updates, automatic categorization, and detailed analytics to help you understand where your money goes.',
    icon: '📊',
  },
  {
    id: 3,
    title: 'Achieve financial goals',
    description:
      'Set and track your financial goals with personalized insights and recommendations to help you stay on budget and build wealth.',
    icon: '🎯',
  },
];

export default function LoginHero() {
  const [currentSlide, setCurrentSlide] = useState(0);
  const [autoPlay, setAutoPlay] = useState(true);

  useEffect(() => {
    if (!autoPlay) return;

    const timer = setInterval(() => {
      setCurrentSlide((prev) => (prev + 1) % slides.length);
    }, 5000);

    return () => clearInterval(timer);
  }, [autoPlay]);

  const goToSlide = (index: number) => {
    setCurrentSlide(index);
    setAutoPlay(false);
  };

  const nextSlide = () => {
    setCurrentSlide((prev) => (prev + 1) % slides.length);
    setAutoPlay(false);
  };

  const prevSlide = () => {
    setCurrentSlide((prev) => (prev - 1 + slides.length) % slides.length);
    setAutoPlay(false);
  };

  return (
    <div className="relative h-full flex flex-col justify-between p-8 bg-gradient-to-b from-teal-600 to-teal-700 text-white rounded-2xl overflow-hidden">
      {/* Background pattern */}
      <div className="absolute inset-0 opacity-10">
        <div className="absolute inset-0 bg-grid-pattern" />
      </div>

      <div className="relative z-10">
        {/* Carousel Content */}
        <div className="mb-8">
          <h2 className="text-4xl font-bold mb-4 leading-tight">
            {slides[currentSlide].title}
          </h2>
          <p className="text-teal-100 text-base leading-relaxed">
            {slides[currentSlide].description}
          </p>
        </div>

        {/* Dots Navigation */}
        <div className="flex gap-2 mb-8">
          {slides.map((_, index) => (
            <button
              key={index}
              onClick={() => goToSlide(index)}
              className={`h-2 rounded-full transition-all ${
                index === currentSlide ? 'bg-white w-8' : 'bg-white/40 w-2'
              }`}
              aria-label={`Go to slide ${index + 1}`}
            />
          ))}
        </div>
      </div>

      {/* Dashboard Preview */}
      <div className="relative z-10 space-y-6">
        {/* Main Dashboard Card */}
        <div className="bg-white/95 rounded-xl p-6 shadow-2xl">
          <div className="flex items-center justify-between mb-4">
            <div className="flex gap-2">
              <div className="w-3 h-3 rounded-full bg-red-400"></div>
              <div className="w-3 h-3 rounded-full bg-yellow-400"></div>
              <div className="w-3 h-3 rounded-full bg-green-400"></div>
            </div>
            <span className="text-xs text-gray-400">Sources</span>
          </div>

          <div className="space-y-3 text-gray-700">
            <div className="flex items-center gap-2">
              <div className="w-4 h-4 rounded border border-gray-300"></div>
              <span className="text-xs font-medium">Overviews</span>
            </div>

            <div className="grid grid-cols-3 gap-3 text-center">
              <div className="bg-pink-100 p-2 rounded text-xs">
                <div className="font-semibold">$22,908</div>
                <div className="text-gray-500">Total Income</div>
              </div>
              <div className="bg-green-100 p-2 rounded text-xs">
                <div className="font-semibold">$29,092</div>
                <div className="text-gray-500">Total Expense</div>
              </div>
              <div className="bg-orange-100 p-2 rounded text-xs">
                <div className="font-semibold">$6,184</div>
                <div className="text-gray-500">Total Outgo</div>
              </div>
            </div>

            {/* Chart preview */}
            <div className="h-16 bg-gradient-to-r from-purple-200 to-pink-200 rounded flex items-end gap-1 px-2 py-2">
              {[60, 40, 75, 50, 80, 45, 65, 55].map((height, i) => (
                <div
                  key={i}
                  className="flex-1 bg-gradient-to-t from-purple-500 to-pink-500 rounded-t"
                  style={{ height: `${height}%` }}
                ></div>
              ))}
            </div>
          </div>
        </div>

        {/* Credit Card Preview */}
        <div className="bg-gradient-to-br from-orange-400 to-pink-400 rounded-xl p-4 text-white shadow-xl">
          <div className="flex justify-between items-start mb-12">
            <div className="text-sm font-semibold">Mastercard</div>
            <div className="w-8 h-5 rounded bg-white/30"></div>
          </div>
          <div className="space-y-2">
            <div className="text-2xl font-bold tracking-wider">•••• •••• •••• 3342</div>
            <div className="flex justify-between text-xs">
              <span>Dianne Russell</span>
              <span>08/25</span>
            </div>
          </div>
        </div>
      </div>

      {/* Navigation Arrows */}
      <button
        onClick={prevSlide}
        className="absolute left-4 top-1/3 -translate-y-1/2 bg-white/20 hover:bg-white/30 rounded-full p-2 transition-colors z-20"
        aria-label="Previous slide"
      >
        <ChevronLeft className="w-5 h-5 text-white" />
      </button>
      <button
        onClick={nextSlide}
        className="absolute right-4 top-1/3 -translate-y-1/2 bg-white/20 hover:bg-white/30 rounded-full p-2 transition-colors z-20"
        aria-label="Next slide"
      >
        <ChevronRight className="w-5 h-5 text-white" />
      </button>
    </div>
  );
}
