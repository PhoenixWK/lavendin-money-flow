import { useLanguage } from "@/hooks/useLanguage";
import { UserPlus, Link2, BarChart3 } from "lucide-react";

const steps = {
  en: [
    {
      number: 1,
      icon: UserPlus,
      title: "Create Account",
      description:
        "Sign up in just 2 minutes with your email. No credit card required.",
    },
    {
      number: 2,
      icon: Link2,
      title: "Connect Sources",
      description:
        "Link your bank accounts, income sources, and expense categories.",
    },
    {
      number: 3,
      icon: BarChart3,
      title: "Track & Report",
      description:
        "Start tracking automatically and receive beautiful insights about your finances.",
    },
  ],
  vi: [
    {
      number: 1,
      icon: UserPlus,
      title: "Tạo Tài khoản",
      description:
        "Đăng ký chỉ trong 2 phút bằng email của bạn. Không cần thẻ tín dụng.",
    },
    {
      number: 2,
      icon: Link2,
      title: "Kết nối Nguồn",
      description:
        "Liên kết các tài khoản ngân hàng, nguồn thu nhập và danh mục chi tiêu của bạn.",
    },
    {
      number: 3,
      icon: BarChart3,
      title: "Theo dõi & Báo cáo",
      description:
        "Bắt đầu theo dõi tự động và nhận những thông tin chi tiết đẹp mắt về tài chính của bạn.",
    },
  ],
};

export default function HowItWorksSection() {
  const { language } = useLanguage();
  const currentSteps = steps[language as keyof typeof steps] || steps.en;

  return (
    <section className="py-20 px-6 bg-white">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-16">
          <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-4">
            {language === "en" ? "How It Works" : "Cách Thức Hoạt Động"}
          </h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">
            {language === "en"
              ? "Get started in three simple steps"
              : "Bắt đầu trong ba bước đơn giản"}
          </p>
        </div>

        <div className="grid md:grid-cols-3 gap-8 md:gap-6">
          {currentSteps.map((step, index) => {
            const Icon = step.icon;
            return (
              <div key={index} className="relative">
                <div className="flex flex-col items-center text-center">
                  <div className="relative mb-6 z-10">
                    <div className="absolute inset-0 bg-gradient-to-br from-primary/20 to-primary/10 rounded-full blur-xl"></div>
                    <div className="relative bg-gradient-to-br from-primary/10 to-white border border-primary/20 rounded-full p-6 w-20 h-20 flex items-center justify-center">
                      <Icon className="w-10 h-10 text-primary" />
                    </div>

                    <div className="absolute -top-2 -right-2 bg-primary text-white rounded-full w-8 h-8 flex items-center justify-center font-bold text-sm">
                      {step.number}
                    </div>
                  </div>

                  <h3 className="text-2xl font-bold text-gray-900 mb-3">
                    {step.title}
                  </h3>

                  <p className="text-gray-600 leading-relaxed">
                    {step.description}
                  </p>
                </div>

                {index < currentSteps.length - 1 && (
                  <div className="hidden md:block absolute top-16 -right-12 w-24 h-1 bg-gradient-to-r from-primary to-primary/30"></div>
                )}
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
}
