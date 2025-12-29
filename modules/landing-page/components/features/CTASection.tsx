import Link from 'next/link';

export const CTASection = () => {
    return (
        <section className="py-16 lg:py-24 bg-white">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                {/* Bottom CTA */}
                <div className="mt-16 bg-[#86ffb2] rounded-2xl p-8 lg:p-12 text-center text-black">
                    <h3 className="text-2xl lg:text-3xl font-bold mb-4">
                        Get Started in under 30 seconds.<br />
                        Enjoy a 7-day free trial.
                    </h3>
                <p className="text-black text-lg mb-8 max-w-2xl mx-auto">
                    No credit card required. Experience the full power of our platform 
                    with a completely free trial period.
                </p>
                <button className="bg-[#37e28c] font-semibold text-black px-8 py-3 rounded-lg text-lg hover:bg-[#27a35a] transition-colors">
                    Start Free Trial
                </button>
                </div>
            </div>
        </section>
    );
};
