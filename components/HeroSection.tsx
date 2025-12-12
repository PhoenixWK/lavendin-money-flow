import { ChevronRight } from "lucide-react";
import { useLanguage } from "@/hooks/useLanguage";

export default function HeroSection() {
  const { language } = useLanguage();

  const content = {
    en: {
      headline: "Smart Personal Finance Management with Lavendin",
      subtext:
        "Track income and expenses, create budgets, set savings goals, and receive visual reports. All in one beautiful, simple interface.",
      ctaPrimary: "Start Free",
      ctaSecondary: "View Demo",
    },
    vi: {
      headline: "Quản lý Tài chính Cá nhân Thông minh với Lavendin",
      subtext:
        "Theo dõi thu nhập và chi tiêu, tạo ngân sách, đặt mục tiêu tiết kiệm và nhận báo cáo trực quan. Tất cả trong một giao diện đơn giản và đẹp mắt.",
      ctaPrimary: "Bắt Đầu Miễn Phí",
      ctaSecondary: "Xem Demo",
    },
  };

  const text = content[language as keyof typeof content] || content.en;

  return (
    <section className="min-h-screen flex items-center justify-center bg-gradient-to-br from-green-50 via-green-25 to-green-10 px-6 py-20">
      <div className="max-w-6xl w-full grid md:grid-cols-2 gap-12 items-center">
        <div className="space-y-8">
          <h1 className="text-5xl md:text-6xl font-bold text-gray-900 leading-tight">
            {text.headline}
          </h1>

          <p className="text-lg text-gray-600 leading-relaxed max-w-xl">
            {text.subtext}
          </p>

          <div className="flex flex-col sm:flex-row gap-4 pt-6">
            <button className="bg-primary hover:bg-primary/90 text-white px-8 py-4 rounded-xl font-semibold transition-all duration-300 shadow-lg hover:shadow-xl flex items-center justify-center gap-2 group">
              {text.ctaPrimary}
              <ChevronRight className="w-5 h-5 group-hover:translate-x-1 transition-transform" />
            </button>

            <button className="border-2 border-primary text-primary hover:bg-primary/5 px-8 py-4 rounded-xl font-semibold transition-all duration-300">
              {text.ctaSecondary}
            </button>
          </div>

          <div className="flex gap-6 pt-4 text-sm text-gray-600">
            <div className="flex items-center gap-2">
              <div className="w-2 h-2 bg-primary rounded-full"></div>
              {language === "en" ? "No credit card needed" : "Không cần thẻ tín dụng"}
            </div>
            <div className="flex items-center gap-2">
              <div className="w-2 h-2 bg-primary rounded-full"></div>
              {language === "en" ? "Takes 2 minutes" : "Chỉ mất 2 phút"}
            </div>
          </div>
        </div>

        <div className="hidden md:block">
          <div className="relative">
            <div className="absolute inset-0 bg-gradient-to-tr from-green-300 to-green-100 rounded-3xl blur-2xl opacity-40"></div>
            <svg
              className="relative w-full h-auto"
              viewBox="0 0 400 400"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              {/* Main App Background */}
              <rect
                x="30"
                y="50"
                width="340"
                height="300"
                rx="20"
                fill="#FFFFFF"
                stroke="#BBF7D0"
                strokeWidth="2"
                style={{filter: "drop-shadow(0 4px 20px rgba(0,0,0,0.1))"}}
              />

              {/* Header Section */}
              <rect x="50" y="70" width="300" height="50" rx="10" fill="#F0FDF4" />
              <text x="65" y="90" fill="#059669" fontSize="14" fontWeight="600">Current Balance</text>
              <text x="65" y="110" fill="#059669" fontSize="24" fontWeight="700">$8,129.00</text>
              
              {/* Notification Badge */}
              <circle cx="330" cy="85" r="8" fill="#EF4444" />
              <text x="328" y="89" fill="white" fontSize="10" fontWeight="600">3</text>

              {/* Credit Card */}
              <rect x="65" y="140" width="120" height="75" rx="8" fill="url(#cardGradient)" />
              <circle cx="160" cy="155" r="6" fill="rgba(255,255,255,0.3)" />
              <circle cx="170" cy="155" r="6" fill="rgba(255,255,255,0.5)" />
              <rect x="75" y="185" width="30" height="3" rx="1" fill="rgba(255,255,255,0.8)" />
              <text x="75" y="205" fill="white" fontSize="12" fontWeight="500">**** 5697</text>

              {/* Transaction Section */}
              <rect x="200" y="140" width="135" height="140" rx="8" fill="#F8FAFC" />
              <text x="215" y="160" fill="#1E293B" fontSize="12" fontWeight="600">Recent Transactions</text>
              
              {/* Transaction Items */}
              <circle cx="220" cy="180" r="8" fill="#10B981" />
              <text x="235" y="185" fill="#374151" fontSize="10">Coffee Shop</text>
              <text x="295" y="185" fill="#EF4444" fontSize="10">-$4.99</text>

              <circle cx="220" cy="200" r="8" fill="#3B82F6" />
              <text x="235" y="205" fill="#374151" fontSize="10">Salary</text>
              <text x="290" y="205" fill="#10B981" fontSize="10">+$3,200</text>

              <circle cx="220" cy="220" r="8" fill="#F59E0B" />
              <text x="235" y="225" fill="#374151" fontSize="10">Groceries</text>
              <text x="295" y="225" fill="#EF4444" fontSize="10">-$67.32</text>

              <circle cx="220" cy="240" r="8" fill="#8B5CF6" />
              <text x="235" y="245" fill="#374151" fontSize="10">Netflix</text>
              <text x="295" y="245" fill="#EF4444" fontSize="10">-$15.99</text>

              {/* Chart Section */}
              <rect x="65" y="230" width="120" height="70" rx="8" fill="#F0FDF4" />
              <text x="75" y="250" fill="#059669" fontSize="11" fontWeight="600">Monthly Spending</text>
              
              {/* Chart Bars */}
              <rect x="80" y="270" width="6" height="20" rx="3" fill="#10B981" />
              <rect x="92" y="275" width="6" height="15" rx="3" fill="#10B981" />
              <rect x="104" y="265" width="6" height="25" rx="3" fill="#10B981" />
              <rect x="116" y="280" width="6" height="10" rx="3" fill="#10B981" />
              <rect x="128" y="270" width="6" height="20" rx="3" fill="#10B981" />
              <rect x="140" y="260" width="6" height="30" rx="3" fill="#10B981" />
              <rect x="152" y="275" width="6" height="15" rx="3" fill="#10B981" />
              <rect x="164" y="268" width="6" height="22" rx="3" fill="#10B981" />

              {/* Action Buttons */}
              <rect x="210" y="295" width="55" height="24" rx="12" fill="#10B981" />
              <text x="225" y="309" fill="white" fontSize="10" fontWeight="500">Transfer</text>
              
              <rect x="275" y="295" width="55" height="24" rx="12" fill="transparent" stroke="#10B981" strokeWidth="1.5" />
              <text x="295" y="309" fill="#10B981" fontSize="10" fontWeight="500">Top Up</text>

              {/* Decorative Elements */}
              <circle cx="85" cy="320" r="2" fill="#10B981" opacity="0.3" />
              <circle cx="95" cy="325" r="1.5" fill="#10B981" opacity="0.5" />
              <circle cx="320" cy="320" r="2" fill="#10B981" opacity="0.3" />

              {/* Card Gradient Definition */}
              <defs>
                <linearGradient id="cardGradient" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" stopColor="#10B981" />
                  <stop offset="100%" stopColor="#059669" />
                </linearGradient>
              </defs>
            </svg>
          </div>
        </div>
      </div>
    </section>
  );
}
