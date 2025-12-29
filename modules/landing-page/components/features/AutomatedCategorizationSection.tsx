import Image from 'next/image';
import Link from 'next/link';

export const AutomatedCategorizationSection = () => {
    return (
        <section className="bg-gray-50 py-16 md:py-24">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-8 md:gap-12 items-center">
                    {/* Left Column - Image */}
                    <div className="relative h-96 md:h-full min-h-100 order-2 md:order-1">
                        <Image
                        src="/landing-page/img-feature-key-2.png"
                        alt="Automated categorization"
                        fill
                        className="object-contain"
                        />
                    </div>

                    {/* Right Column - Content */}
                    <div className="order-1 md:order-2">
                        <h2 className="text-3xl text-black md:text-4xl font-bold mb-4">
                            Automated expense categorization
                        </h2>
                        <p className="text-black text-lg mb-6">
                            Using intelligent algorithms and machine learning, Payop categorizes your transactions and expenses ensuring that your financial data is organized to match your needs.
                        </p>
                        <ul className="space-y-3 mb-8">
                            <li className="flex items-start gap-3">
                                <span className="text-[#47EB98] font-bold">✓</span>
                                <span className="text-black">Automatic categorization of transactions</span>
                            </li>
                            <li className="flex items-start gap-3">
                                <span className="text-[#47EB98] font-bold">✓</span>
                                <span className="text-black">Financial data is always organized</span>
                            </li>
                        </ul>

                        <Link 
                        href="#" 
                        className="inline-block bg-[#47EB98] text-black font-semibold px-8 py-3 rounded-lg hover:bg-[#3ad280] transition-colors"
                        >
                        Get Started
                        </Link>
                    </div>
                </div>
            </div>
        </section>
    );
};
