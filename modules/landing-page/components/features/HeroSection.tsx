import Image from 'next/image';
import Link from 'next/link';

export const HeroSection = () => {
    return (
        <section className="bg-white py-12 md:py-20">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-8 md:gap-12 items-center">
                    {/* Left Column - Text Content */}
                    <div>
                        <h1 className="text-4xl text-black md:text-5xl font-bold mb-4">
                            Effortless real-time
                            <br />
                                expense tracking for
                            <br />
                            <span className="text-[#47EB98]">smarter financial</span> control
                        </h1>
                        <p className="text-gray-600 font-semibold text-lg mb-8">
                            With Payop's seamless tracking, you can monitor your expenses intuitively, uncover spending patterns, and optimize your budget. Take control of your finances today.
                        </p>
                        <div className="flex flex-col sm:flex-row gap-4">
                        <Link 
                            href="#" 
                            className="bg-[#47EB98] text-black font-semibold px-8 py-3 rounded-lg hover:bg-[#3ad280] transition-colors inline-block text-center"
                        >
                            Start Your 7 Days Free Trial
                        </Link>
                        <p className="text-gray-500 text-sm pt-3">
                            Join over 2,000+ users
                        </p>
                        </div>
                </div>

                {/* Right Column - Image */}
                <div className="relative h-96 md:h-full min-w-100 md:min-w-150">
                    <Image
                        src="/landing-page/img-feature-hero.png"
                        alt="Expense tracking dashboard"
                        fill
                        className="object-contain"
                    />
                </div>
                </div>
            </div>
        </section>
    );
};
