'use client';

import { Check, X } from 'lucide-react';

interface Feature {
  name: string;
  basic: boolean | string;
  pro: boolean | string;
  business: boolean | string;
  enterprise: boolean | string;
}

const features: Feature[] = [
  {
    name: 'Budget tracking',
    basic: true,
    pro: true,
    business: true,
    enterprise: true,
  },
  {
    name: 'Expense categorization',
    basic: true,
    pro: true,
    business: true,
    enterprise: true,
  },
  {
    name: 'Invoicing and billing',
    basic: false,
    pro: true,
    business: true,
    enterprise: true,
  },
  {
    name: 'Basic financial reports',
    basic: true,
    pro: true,
    business: true,
    enterprise: true,
  },
  {
    name: 'Advanced financial reports',
    basic: false,
    pro: true,
    business: true,
    enterprise: true,
  },
  {
    name: 'Multi-user access',
    basic: false,
    pro: 'Up to 5 users',
    business: 'Up to 20 users',
    enterprise: 'Unlimited users',
  },
  {
    name: 'Payroll management',
    basic: false,
    pro: false,
    business: true,
    enterprise: true,
  },
  {
    name: 'Inventory management',
    basic: false,
    pro: false,
    business: true,
    enterprise: true,
  },
  {
    name: 'Custom integrations',
    basic: false,
    pro: false,
    business: false,
    enterprise: true,
  },
  {
    name: 'Dedicated account manager',
    basic: false,
    pro: false,
    business: true,
    enterprise: true,
  },
  {
    name: 'Email support',
    basic: true,
    pro: true,
    business: true,
    enterprise: true,
  },
  {
    name: 'Priority email support',
    basic: false,
    pro: true,
    business: true,
    enterprise: true,
  },
  {
    name: 'Phone support',
    basic: false,
    pro: false,
    business: true,
    enterprise: true,
  },
  {
    name: '24/7 premium support',
    basic: false,
    pro: false,
    business: false,
    enterprise: true,
  },
  {
    name: 'Tailored onboarding and training',
    basic: false,
    pro: false,
    business: false,
    enterprise: true,
  },
];

const PricingComparison = () => {
  const renderFeatureValue = (value: boolean | string) => {
    if (typeof value === 'boolean') {
      return value ? (
        <Check className="w-5 h-5 text-green-500 mx-auto" />
      ) : (
        <X className="w-5 h-5 text-red-500 mx-auto" />
      );
    }
    return <span className="text-sm text-gray-700 text-center block">{value}</span>;
  };

  return (
    <section className="py-16 bg-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-12">
          <h2 className="text-3xl font-bold text-gray-900 mb-4">Compare all features</h2>
        </div>

        {/* Desktop Table */}
        <div className="hidden lg:block overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-200">
                <th className="text-left py-4 px-6"></th>
                <th className="text-center py-4 px-6">
                  <div className="space-y-2">
                    <div className="text-lg text-black font-semibold">Basic</div>
                    <div className="text-2xl text-black font-bold">$9.99</div>
                    <button className="bg-[#F4FBF2] border-2 border-[#9CDD8A] hover:bg-[#9CDD8A] text-black font-semibold px-4 py-2 rounded-lg text-sm transition-colors">
                      Get Started
                    </button>
                  </div>
                </th>
                <th className="text-center py-4 px-6">
                  <div className="space-y-2">
                    <div className="text-lg text-black font-semibold">Pro ⭐</div>
                    <div className="text-2xl text-black font-bold">$29.99</div>
                    <button className="bg-[#F4FBF2] border-2 border-[#9CDD8A] hover:bg-[#9CDD8A] text-black font-semibold px-4 py-2 rounded-lg text-sm transition-colors">
                      Get Started
                    </button>
                  </div>
                </th>
                <th className="text-center py-4 px-6">
                  <div className="space-y-2">
                    <div className="text-lg text-black font-semibold">Business</div>
                    <div className="text-2xl text-black font-bold">$79.99</div>
                    <button className="bg-[#F4FBF2] border-2 border-[#9CDD8A] hover:bg-[#9CDD8A] text-black font-semibold px-4 py-2 rounded-lg text-sm transition-colors">
                      Get Started
                    </button>
                  </div>
                </th>
                <th className="text-center py-4 px-6">
                  <div className="space-y-2">
                    <div className="text-lg text-black font-semibold">Enterprise</div>
                    <div className="text-2xl text-black font-bold">$199.99</div>
                    <button className="bg-[#F4FBF2] border-2 border-[#9CDD8A] hover:bg-[#9CDD8A] text-black font-semibold px-4 py-2 rounded-lg text-sm transition-colors">
                      Contact Sales
                    </button>
                  </div>
                </th>
              </tr>
            </thead>
            <tbody>
              {features.map((feature, index) => (
                <tr key={index} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="py-4 px-6 text-gray-900 font-medium">{feature.name}</td>
                  <td className="py-4 px-6 text-center">{renderFeatureValue(feature.basic)}</td>
                  <td className="py-4 px-6 text-center">{renderFeatureValue(feature.pro)}</td>
                  <td className="py-4 px-6 text-center">{renderFeatureValue(feature.business)}</td>
                  <td className="py-4 px-6 text-center">{renderFeatureValue(feature.enterprise)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Mobile Cards */}
        <div className="lg:hidden space-y-8">
          {[
            { name: 'Basic', price: '$9.99', values: features.map(f => f.basic) },
            { name: 'Pro', price: '$29.99', values: features.map(f => f.pro), isPopular: true },
            { name: 'Business', price: '$79.99', values: features.map(f => f.business) },
            { name: 'Enterprise', price: '$199.99', values: features.map(f => f.enterprise) },
          ].map((plan, planIndex) => (
            <div key={planIndex} className={`rounded-lg border-2 p-6 ${plan.isPopular ? 'border-green-500 bg-green-50' : 'border-gray-200'}`}>
              <div className="text-center mb-6">
                <h3 className="text-xl text-black font-bold">{plan.name}</h3>
                <div className="text-2xl text-black font-bold">{plan.price}</div>
              </div>
              <div className="space-y-3">
                {features.map((feature, featureIndex) => (
                  <div key={featureIndex} className="flex items-center justify-between">
                    <span className="text-gray-700">{feature.name}</span>
                    {renderFeatureValue(plan.values[featureIndex])}
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

export default PricingComparison;