import Header from '../Header';
import HeroSection from './HeroSection';
import FeaturesSection from './FeaturesSection';
import DataSecuritySection from './DataSecuritySection';
import ProcessSection from './ProcessSection';
import TestimonialsSection from './TestimonialsSection';
import IntegrationsSection from './IntegrationsSection';
import Footer from '../Footer';

const LandingPage = () => {
  return (
    <div className="min-h-screen bg-white">
        <Header />
        <main>
            <HeroSection />
            <FeaturesSection />
            <DataSecuritySection />
            <ProcessSection />
            <TestimonialsSection />
            <IntegrationsSection />
        </main>
        <Footer />
    </div>
  );
};

export default LandingPage;