'use client';

import PricingCards from './PricingCards';
import TrustedBy from './TrustedBy';
import PricingComparison from './PricingComparison';
import PricingFAQ from './PricingFAQ';

const PricingPage = () => {
  return (
    <main className="min-h-screen">
      <PricingCards />
      <PricingComparison />
      <PricingFAQ />
    </main>
  );
};

export default PricingPage;