
import Image from 'next/image';

const DataSecuritySection = () => {

  return (
    <section className="py-16 lg:py-24 bg-black text-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Section header */}
        <div className="text-center mb-16">
          <h2 className="text-3xl lg:text-4xl font-bold mb-4">
            Your Data Secured
          </h2>
          <p className="text-lg text-gray-300 max-w-3xl mx-auto">
            We take your privacy and security seriously. Our comprehensive security measures 
            ensure your financial data is always protected and accessible only to you.
          </p>
        </div>

        <Image 
          src={"/landing-page/img-home-secury.png"}
          alt="Data Security Illustration"
          width={0}
          height={0}
          sizes="100vw"
          className="w-auto h-auto"
        />
      </div>
    </section>
  );
};

export default DataSecuritySection;