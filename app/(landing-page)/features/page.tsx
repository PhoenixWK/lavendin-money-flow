

import {
  HeroSection,
  WhyRealTimeSection,
  InstantExpenseUpdatesSection,
  AutomatedCategorizationSection,
  VisualInsightsSection,
  CrossPlatformSection,
  CTASection,
} from "@/modules/landing-page/components/features";
import Footer from "@/modules/landing-page/components/Footer";
import Header from "@/modules/landing-page/components/Header";

export default function FeaturesPage() {
    return (
        <>
            <Header />
            <HeroSection />
            <WhyRealTimeSection />
            <InstantExpenseUpdatesSection />
            <AutomatedCategorizationSection />
            <VisualInsightsSection />
            <CrossPlatformSection />
            <CTASection />
            <Footer />
        </>
    );
}