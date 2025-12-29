import Image from 'next/image';
import Link from 'next/link';
import { CheckIcon } from 'lucide-react';

const features = [
    "Instantaneous visibility into spending habits",
    "Ability to adjust budget in real-time",
    "Instant alerts on unexpected expenses",
    "Seamless integration with all major banks"
];

export const InstantExpenseUpdatesSection = () => {
  

    return (
        <section className="bg-white py-16 md:py-24">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-8 md:gap-12 items-center">
                    {/* Left Column - Content */}
                    <div>
                        <h2 className="text-3xl text-black md:text-4xl font-bold mb-4">
                            Instant expense updates
                        </h2>
                        <p className="text-black text-lg mb-6">
                        Every transaction gets logged instantly. Nothing ever falls through the cracks. Whether you're purchasing
                        </p>

                        <ul className="space-y-4 mb-8">
                            {features.map((feature, index) => (
                                <li key={index} className="flex items-start gap-3">
                                    <CheckIcon className="w-5 h-5 text-[#47EB98] shrink-0 mt-1" />
                                    <span className="text-gray-700">{feature}</span>
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
                    <div className="relative h-96 md:h-full min-w-50 md:min-h-150">
                        <Image
                        src="/landing-page/img-feature-key-1.png"
                        alt="Instant expense updates dashboard"
                        fill
                        className="object-contain"
                        />
                    </div>
                </div>
            </div>
        </section>
    );
};
