import { CheckCircleIcon, TrendingUpIcon, PieChartIcon, ShieldIcon, BarChartIcon } from 'lucide-react';

interface FeatureCard {
  icon: React.ReactNode;
  title: string;
  description: string;
}

const features: FeatureCard[] = [
    {
        icon: <CheckCircleIcon className="w-8 h-8 text-[#47EB98]" />,
        title: "Immediate financial awareness",
        description: "Know exactly where your money goes with instant tracking and real-time notifications."
    },
    {
        icon: <BarChartIcon className="w-8 h-8 text-[#47EB98]" />,
        title: "Better budget management",
        description: "Stay within budget limits and adjust spending in real-time with smart alerts."
    },
    {
        icon: <TrendingUpIcon className="w-8 h-8 text-[#47EB98]" />,
        title: "Smarter decision making",
        description: "Make informed financial decisions with comprehensive spending insights and analytics."
    },
    {
        icon: <ShieldIcon className="w-8 h-8 text-[#47EB98]" />,
        title: "Reduced risk of overspending",
        description: "Monitor your spending patterns and prevent unnecessary expenses efficiently."
    },
    {
        icon: <PieChartIcon className="w-8 h-8 text-[#47EB98]" />,
        title: "Simplified expense organization",
        description: "Automatically categorize and organize your spending for better clarity."
    }
];

export const WhyRealTimeSection = () => {
    

    return (
        <section className="bg-gray-50 py-16 md:py-24">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="text-center mb-12">
                    <h2 className="text-3xl text-black md:text-4xl font-bold mb-4">
                        Why real-time expense tracking matters
                    </h2>
                    <p className="text-black text-lg max-w-2xl mx-auto">
                        Managing expenses effectively is key to maintaining financial health. Whether you're running a small business or on a personal journey, Payop's real-time tracking helps you stay in control.
                    </p>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-6">
                    {features.map((feature, index) => (
                        <div 
                        key={index}
                        className="bg-white rounded-lg p-6 shadow-sm hover:shadow-md transition-shadow"
                        >
                        <div className="flex justify-center mb-4">
                            {feature.icon}
                        </div>
                        <h3 className="text-lg text-black font-semibold text-center mb-3">
                            {feature.title}
                        </h3>
                        <p className="text-[#888888] text-sm text-center">
                            {feature.description}
                        </p>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
};
