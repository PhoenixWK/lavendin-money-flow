import { Check } from "lucide-react";
import { useLanguage } from "@/hooks/useLanguage";

const pricingPlans = {
  en: {
    title: "Simple, Transparent Pricing",
    subtitle: "Choose the perfect plan for your needs",
    plans: [
      {
        name: "Free",
        price: "0",
        period: "forever",
        description: "Perfect for getting started",
        features: [
          "Track up to 50 transactions",
          "1 budget",
          "Basic reports",
          "Email support",
        ],
        cta: "Get Started",
        highlighted: false,
      },
      {
        name: "Pro",
        price: "9.99",
        period: "per month",
        description: "For serious savers",
        features: [
          "Unlimited transactions",
          "Unlimited budgets",
          "Advanced analytics",
          "Savings goals",
          "Priority support",
          "Export reports",
        ],
        cta: "Register Now",
        highlighted: true,
      },
      {
        name: "Premium",
        price: "19.99",
        period: "per month",
        description: "For ultimate control",
        features: [
          "Everything in Pro",
          "Multi-currency support",
          "Investment tracking",
          "Custom categories",
          "API access",
          "Dedicated support",
        ],
        cta: "Register Now",
        highlighted: false,
      },
    ],
  },
  vi: {
    title: "Giá Cả Đơn Giản, Minh Bạch",
    subtitle: "Chọn gói hoàn hảo cho nhu cầu của bạn",
    plans: [
      {
        name: "Miễn Phí",
        price: "0",
        period: "mãi mãi",
        description: "Hoàn hảo để bắt đầu",
        features: [
          "Theo dõi tới 50 giao dịch",
          "1 ngân sách",
          "Báo cáo cơ bản",
          "Hỗ trợ qua email",
        ],
        cta: "Bắt Đầu",
        highlighted: false,
      },
      {
        name: "Pro",
        price: "9.99",
        period: "mỗi tháng",
        description: "Dành cho những người tiết kiệm nghiêm túc",
        features: [
          "Giao dịch không giới hạn",
          "Ngân sách không giới hạn",
          "Phân tích nâng cao",
          "Mục tiêu tiết kiệm",
          "Hỗ trợ ưu tiên",
          "Xuất báo cáo",
        ],
        cta: "Đăng ký Ngay",
        highlighted: true,
      },
      {
        name: "Premium",
        price: "19.99",
        period: "mỗi tháng",
        description: "Để kiểm soát tối đa",
        features: [
          "Tất cả tính năng Pro",
          "Hỗ trợ đa tiền tệ",
          "Theo dõi đầu tư",
          "Danh mục tùy chỉnh",
          "Truy cập API",
          "Hỗ trợ chuyên dụng",
        ],
        cta: "Đăng ký Ngay",
        highlighted: false,
      },
    ],
  },
};

export default function PricingSection() {
  const { language } = useLanguage();
  const content =
    pricingPlans[language as keyof typeof pricingPlans] || pricingPlans.en;

  return (
    <section className="py-20 px-6 bg-gradient-to-b from-white to-green-50">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-16">
          <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-4">
            {content.title}
          </h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">
            {content.subtitle}
          </p>
        </div>

        <div className="grid md:grid-cols-3 gap-8">
          {content.plans.map((plan, index) => (
            <div
              key={index}
              className={`relative rounded-2xl transition-all duration-300 ${
                plan.highlighted
                  ? "bg-gradient-to-br from-primary to-primary/90 text-white shadow-2xl transform scale-100 md:scale-105"
                  : "bg-white border border-green-100 text-gray-900 hover:border-green-300 hover:shadow-lg"
              }`}
            >
              {plan.highlighted && (
                <div className="absolute -top-4 left-1/2 transform -translate-x-1/2 bg-white text-primary px-4 py-1 rounded-full text-sm font-semibold">
                  {language === "en" ? "MOST POPULAR" : "PHỔ BIẾN NHẤT"}
                </div>
              )}

              <div className="p-8 flex flex-col h-full">
                <div className="mb-8">
                  <h3 className="text-2xl font-bold mb-2">{plan.name}</h3>
                  <p
                    className={`text-sm ${
                      plan.highlighted
                        ? "text-white/80"
                        : "text-gray-600"
                    }`}
                  >
                    {plan.description}
                  </p>
                </div>

                <div className="mb-8">
                  <div className="flex items-baseline gap-2">
                    <span className="text-4xl font-bold">${plan.price}</span>
                    <span
                      className={`text-sm ${
                        plan.highlighted
                          ? "text-white/80"
                          : "text-gray-600"
                      }`}
                    >
                      {plan.period}
                    </span>
                  </div>
                </div>

                <button
                  className={`w-full py-3 px-6 rounded-xl font-semibold transition-all duration-300 mb-8 ${
                    plan.highlighted
                      ? "bg-white text-primary hover:bg-gray-100"
                      : "bg-primary text-white hover:bg-primary/90"
                  }`}
                >
                  {plan.cta}
                </button>

                <div className="space-y-4 flex-1">
                  {plan.features.map((feature, idx) => (
                    <div
                      key={idx}
                      className="flex items-start gap-3"
                    >
                      <Check
                        className={`w-5 h-5 mt-0.5 flex-shrink-0 ${
                          plan.highlighted
                            ? "text-white"
                            : "text-primary"
                        }`}
                      />
                      <span className="text-sm">{feature}</span>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
