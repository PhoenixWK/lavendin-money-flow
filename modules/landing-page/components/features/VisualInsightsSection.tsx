import Image from 'next/image';
import Link from 'next/link';
import { BarChartIcon, LineChartIcon, PieChartIcon, TrendingUpIcon } from 'lucide-react';

const insights = [
    { icon: <BarChartIcon className="w-5 h-5" />, text: "Clear easy-to-read charts and graphs" },
    { icon: <LineChartIcon className="w-5 h-5" />, text: "Can discover insights into spending patterns" },
    { icon: <PieChartIcon className="w-5 h-5" />, text: "Simplify financial analysis with equal data representation" },
    { icon: <TrendingUpIcon className="w-5 h-5" />, text: "Spot trends and anomalies quickly" }
];

export const VisualInsightsSection = () => {
  

    return (
        <section className="bg-white py-16 md:py-24">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-8 md:gap-12 items-center">
                    {/* Left Column - Content */}
                    <div>
                        <h2 className="text-3xl text-black md:text-4xl font-bold mb-4">
                            Visual financial insights
                        </h2>
                        <p className="text-black text-lg mb-6">
                            Get access to detailed charts, graphs, and dashboards that visualize your spending, habits. Take you to the next level of financial intelligence.
                        </p>

                        <ul className="space-y-4 mb-8">
                            {insights.map((insight, index) => (
                                <li key={index} className="flex items-start gap-3">
                                <span className="text-[#47EB98] shrink-0 mt-1">{insight.icon}</span>
                                <span className="text-black">{insight.text}</span>
                                </li>
                            ))}
                        </ul>

                        <Link 
                            href="#" 
                            className="inline-block bg-[#47EB98] text-black font-semibold px-8 py-3 rounded-lg hover:bg-[#3ad280] transition-colors"
                        >
                            Get Started
                        </Link>
                    </div>

                    {/* Right Column - Image */}
                    <div className="relative h-96 md:h-full min-h-100">
                        <Image
                        src="/landing-page/img-feature-key-3.png"
                        alt="Visual financial insights"
                        fill
                        className="object-contain"
                        />
                    </div>
                </div>
            </div>
        </section>
    );
};
