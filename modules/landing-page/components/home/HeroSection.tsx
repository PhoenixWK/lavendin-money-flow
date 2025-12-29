
import Image from "next/image";


const HeroSection = () => {
    return (
        <section className="bg-[#FAF8F6] py-16 lg:py-24">
            <div className="mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex flex-col gap-12 items-center">
                {/* Left side - Text content */}
                    <div className="text-center">
                        <h1 className="text-4xl text-center md:text-5xl lg:text-6xl font-bold text-gray-900 leading-tight">
                            Revolutionize{' '}
                            <span className="text-[#47EB98]">the Way</span>
                            <br />
                                You Manage Your Finances
                        </h1>
                        
                        <p className="mt-6 text-lg text-center md:text-xl text-gray-600 leading-relaxed">
                            Take control of your financial future with our comprehensive money management platform. 
                            Track expenses, set budgets, and achieve your financial goals with ease.
                        </p>
                        
                    </div>
                    <Image 
                        src={"/landing-page/img-home-hero.png"}
                        alt="Hero Image"
                        width={0}
                        height={0}
                        sizes="100vw"
                        className="w-full h-full"
                        loading="lazy"
                    />
                </div>
                
                {/* Trusted by section */}
                {/* <div className="mt-16 pt-16 border-t border-gray-200">
                    <p className="text-center text-gray-500 mb-8">Trusted by leading financial institutions</p>
                    <div className="flex justify-center items-center space-x-8 opacity-50">
                        <div className="text-2xl font-bold text-gray-400">PayPal</div>
                        <div className="text-2xl font-bold text-gray-400">Stripe</div>
                        <div className="text-2xl font-bold text-gray-400">Plaid</div>
                        <div className="text-2xl font-bold text-gray-400">Mint</div>
                    </div>
                </div> */}
            </div>
        </section>
    );
};

export default HeroSection;