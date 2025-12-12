import { Check } from "lucide-react";

export default function PricingSection() {
  const plans = [
    {
      name: "Basic",
      price: "Free",
      description: "Perfect for getting started with personal finance",
      features: [
        "Track 3 accounts",
        "Basic reports",
        "Email support",
      ],
      cta: "Get Started",
      highlighted: false,
    },
    {
      name: "Premium",
      price: "$9.99",
      priceUnit: "/month",
      description: "For serious financial planning and management",
      features: [
        "Unlimited accounts",
        "Advanced analytics",
        "Goal tracking",
        "Priority support",
      ],
      cta: "Start Free Trial",
      highlighted: true,
    },
  ];

  return (
    <section id="pricing" className="py-20 px-6 bg-gradient-to-br from-green-50 to-white">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-16">
          <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-4">
            Simple pricing for everyone
          </h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">
            Choose the perfect plan for your needs
          </p>
        </div>

        <div className="grid md:grid-cols-2 gap-8 max-w-4xl mx-auto">
          {plans.map((plan, index) => (
            <div
              key={index}
              className={`relative p-8 rounded-2xl border-2 transition-all duration-300 hover:shadow-lg ${
                plan.highlighted
                  ? "border-primary bg-white shadow-lg scale-105"
                  : "border-gray-200 bg-white hover:border-green-300"
              }`}
            >
              {plan.highlighted && (
                <div className="absolute -top-4 left-1/2 transform -translate-x-1/2">
                  <span className="bg-primary text-white px-4 py-1 rounded-full text-sm font-medium">
                    Most Popular
                  </span>
                </div>
              )}
              
              <div className="text-center mb-8">
                <h3 className="text-2xl font-bold text-gray-900 mb-2">{plan.name}</h3>
                <div className="mb-4">
                  <span className="text-4xl font-bold text-gray-900">{plan.price}</span>
                  {plan.priceUnit && (
                    <span className="text-gray-600">{plan.priceUnit}</span>
                  )}
                </div>
                <p className="text-gray-600">{plan.description}</p>
              </div>

              <ul className="space-y-4 mb-8">
                {plan.features.map((feature, featureIndex) => (
                  <li key={featureIndex} className="flex items-start">
                    <Check className="w-5 h-5 text-primary mr-3 mt-0.5 flex-shrink-0" />
                    <span className="text-gray-700">{feature}</span>
                  </li>
                ))}
              </ul>

              <button
                className={`w-full py-3 px-6 rounded-xl font-semibold transition-all duration-300 ${
                  plan.highlighted
                    ? "bg-primary text-white hover:bg-primary/90 shadow-lg hover:shadow-xl"
                    : "bg-gray-100 text-gray-900 hover:bg-gray-200"
                }`}
              >
                {plan.cta}
              </button>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
