import {
  TrendingUp,
  Target,
  PieChart,
  Wallet,
} from "lucide-react";

export default function FeaturesSection() {
  const features = [
    {
      icon: Wallet,
      title: "Income/Expense Tracking",
      description: "Automatically track all your financial transactions in one place.",
    },
    {
      icon: Target,
      title: "Budget Management",
      description: "Create and manage budgets to keep your spending under control.",
    },
    {
      icon: TrendingUp,
      title: "Setting Savings Goals",
      description: "Set and track savings goals with clear milestones.",
    },
    {
      icon: PieChart,
      title: "Visual Reporting",
      description: "Get beautiful, interactive charts and reports about your finances.",
    },
  ];

  return (
    <section className="py-20 px-6 bg-white">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-16">
          <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-4">
            Everything you need for financial success
          </h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">
            Powerful tools to help you take control of your finances
          </p>
        </div>

        <div className="grid md:grid-cols-2 gap-8">
          {features.map((feature, index) => {
            const Icon = feature.icon;
            return (
              <div
                key={index}
                className="group p-8 rounded-2xl bg-gradient-to-br from-green-50 to-white border border-green-100 hover:border-green-300 hover:shadow-lg transition-all duration-300"
              >
                <div className="mb-6 inline-block p-4 bg-gradient-to-br from-primary to-primary/80 rounded-xl group-hover:scale-110 transition-transform duration-300">
                  <Icon className="w-6 h-6 text-white" />
                </div>

                <h3 className="text-xl font-bold text-gray-900 mb-3">
                  {feature.title}
                </h3>

                <p className="text-gray-600 leading-relaxed">
                  {feature.description}
                </p>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
}
