import Header from "../modules/landing-page/components/Header";
import HeroSection from "../modules/landing-page/components/HeroSection";
import FeaturesSection from "../modules/landing-page/components/FeaturesSection";
import HowItWorksSection from "../modules/landing-page/components/HowItWorksSection";
import PricingSection from "../modules/landing-page/components/PricingSection";
import TestimonialsSection from "../modules/landing-page/components/TestimonialsSection";
import Footer from "../modules/landing-page/components/Footer";

export default function Index() {
  return (
    <div className="min-h-screen bg-white">
      <Header />
      <HeroSection />
      <FeaturesSection />
      <HowItWorksSection />
      <PricingSection />
      <TestimonialsSection />
      <Footer />
    </div>
  );
}
