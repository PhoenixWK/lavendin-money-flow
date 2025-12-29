import Image from 'next/image';
import Link from 'next/link';
import { SmartphoneIcon, MonitorIcon, TabletIcon } from 'lucide-react';


const platforms = [
    { icon: <SmartphoneIcon className="w-6 h-6" />, name: "Mobile" },
    { icon: <TabletIcon className="w-6 h-6" />, name: "Tablet" },
    { icon: <MonitorIcon className="w-6 h-6" />, name: "Desktop" }
];

export const CrossPlatformSection = () => {
  

    return (
        <section className="bg-gray-50 py-16 md:py-24">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-8 md:gap-12 items-center">
                {/* Left Column - Image */}
                    <div className="relative h-96 md:h-full min-h-100 order-2 md:order-1">
                        <Image
                            src="/landing-page/img-feature-key-4.png"
                            alt="Cross-platform accessibility"
                            fill
                            className="object-contain"
                        />
                    </div>

                    {/* Right Column - Content */}
                    <div className="order-1 md:order-2">
                        <h2 className="text-3xl text-black md:text-4xl font-bold mb-4">
                            Cross-platform accessibility
                        </h2>
                        <p className="text-black text-lg mb-6">
                            Track your expenses anytime, anywhere—whether you're using a smartphone, tablet, or computer. The Payop app is available on all devices and syncs seamlessly across all platforms.
                        </p>

                        <div className="space-y-3 mb-8">
                        {platforms.map((platform, index) => (
                            <div key={index} className="flex items-center gap-3">
                            <span className="text-[#47EB98]">{platform.icon}</span>
                            <span className="text-black font-medium">Access via {platform.name}</span>
                            </div>
                        ))}
                        </div>

                        <div className="space-y-3 mb-8">
                            <p className="flex items-start gap-3 text-black">
                                <span className="text-[#47EB98] font-bold">✓</span>
                                <span>Sync connected to your finances anytime, anywhere</span>
                            </p>
                            <p className="flex items-start gap-3 text-black">
                                <span className="text-[#47EB98] font-bold">✓</span>
                                <span>Monitor real-time data across all your devices</span>
                            </p>
                        </div>

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
