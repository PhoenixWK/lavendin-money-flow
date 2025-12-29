'use client';

import { Check, Star } from 'lucide-react';

interface PricingTier {
  name: string;
  price: string;
  period: string;
  description: string;
  features: string[];
  isPopular?: boolean;
  buttonText: string;
  buttonAction: string;
}

const pricingTiers: PricingTier[] = [
  {
    name: 'Basic',
    price: '$9.99',
    period: '/month',
    description: 'For individuals and freelancers',
    features: [
      'Budget tracking',
      'Expense categorization',
      'Basic financial reports'
    ],
    buttonText: 'Get Started',
    buttonAction: 'primary'
  },
  {
    name: 'Pro',
    price: '$29.99',
    period: '/month',
    description: 'Good for small business',
    features: [
      'All features in Basic Plan',
      'Expense categorization',
      'Advanced financial reports',
      'Multi-user access (up to 5 users)',
      'Priority email support'
    ],
    isPopular: true,
    buttonText: 'Contact Sales',
    buttonAction: 'secondary'
  },
  {
    name: 'Business',
    price: '$79.99',
    period: '/month',
    description: 'Perfect for growing enterprises',
    features: [
      'All features in Pro Plan',
      'Unlimited users',
      'Inventory management',
      'Multi-user access (up to 20 users)',
      'Dedicated account manager',
      'Phone support'
    ],
    buttonText: 'Get Started',
    buttonAction: 'primary'
  },
  {
    name: 'Enterprise',
    price: '$199.99',
    period: '/month',
    description: 'Solutions for large enterprises',
    features: [
      'All features in Business Plan',
      'Custom integrations',
      'Unlimited users',
      'Advanced audit and reporting',
      '24/7 premium support',
      'Tailored onboarding and training'
    ],
    buttonText: 'Contact Sales',
    buttonAction: 'secondary'
  }
];

const PricingCards = () => {
  return (
    <section className="py-16 bg-gray-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-12">
          <h2 className="text-6xl font-bold text-gray-900 mb-4">Pricing</h2>
          <p className="text-lg font-semibold text-[#888888] max-w-2xl mx-auto">
            We're here to help! Whether you have a question, need support, or just want to learn more about Payap, feel free to reach out to us.
          </p>
          
          {/* Toggle Switch */}
          {/* <div className="flex items-center justify-center mt-8">
            <span className="text-gray-700 mr-3">Annual</span>
            <div className="relative">
              <input type="checkbox" className="sr-only" />
              <div className="block bg-gray-300 w-14 h-8 rounded-full cursor-pointer">
                <div className="absolute left-1 top-1 bg-white w-6 h-6 rounded-full transition"></div>
              </div>
            </div>
            <span className="text-gray-700 ml-3">Monthly</span>
          </div> */}
        </div>

        <div className="pt-10 grid md:grid-cols-2 lg:grid-cols-4 gap-8">
          {pricingTiers.map((tier, index) => (
            <div
              key={index}
              className={`relative rounded-2xl p-8 ${
                tier.isPopular
                  ? 'bg-black text-white transform scale-105'
                  : 'bg-white text-gray-900'
              } shadow-lg`}
            >
              {tier.isPopular && (
                <div className="absolute -top-4 left-1/2 transform -translate-x-1/2">
                  <div className="bg-orange-500 text-white px-4 py-1 rounded-full text-sm font-medium flex items-center gap-1">
                    <Star className="w-4 h-4" fill="currentColor" />
                    Pro
                  </div>
                </div>
              )}

              <div className="text-center mb-8">
                <h3 className="text-xl font-semibold mb-2">{tier.name}</h3>
                <p className={`text-sm mb-4 ${tier.isPopular ? 'text-gray-300' : 'text-gray-600'}`}>
                  {tier.description}
                </p>
                <div className="flex items-baseline justify-center">
                  <span className="text-4xl font-bold">{tier.price}</span>
                  <span className={`text-lg ${tier.isPopular ? 'text-gray-300' : 'text-gray-600'}`}>
                    {tier.period}
                  </span>
                </div>
              </div>

              <button
                className={`w-full py-3 px-6 rounded-lg font-medium mb-8 transition-colors ${
                  tier.isPopular
                    ? 'bg-green-500 hover:bg-green-600 text-white'
                    : tier.buttonAction === 'secondary'
                    ? 'border-2 border-gray-300 hover:border-gray-400 text-gray-700 hover:text-gray-900'
                    : 'bg-green-100 hover:bg-green-200 text-green-800'
                }`}
              >
                {tier.buttonText}
              </button>

              <div className="space-y-4">
                {tier.features.map((feature, featureIndex) => (
                  <div key={featureIndex} className="flex items-start gap-3">
                    <Check 
                      className={`w-5 h-5 shrink-0 mt-0.5 ${
                        tier.isPopular ? 'text-green-400' : 'text-green-500'
                      }`} 
                    />
                    <span className={`text-sm ${tier.isPopular ? 'text-gray-300' : 'text-gray-600'}`}>
                      {feature}
                    </span>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default PricingCards;