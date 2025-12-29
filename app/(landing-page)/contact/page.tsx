

import {
  HeroSection,
  ContactCardsSection,
  MapSection,
} from "@/modules/landing-page/components/contact";
import Footer from "@/modules/landing-page/components/Footer";
import Header from "@/modules/landing-page/components/Header";

export default function ContactPage() {
    return (
        <>
            <Header />
            <HeroSection />
            <ContactCardsSection />
            {/* <MapSection /> */}
            <Footer />
        </>
    );
}