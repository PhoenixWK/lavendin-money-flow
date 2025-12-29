
import { ChartColumnBig, Clock4, ContactRound, Wallet } from "lucide-react";
import Image from "next/image";

const steps = [
  {
    logo: "ContactRound",
    title: "Connect your accounts",
    description: "Securely link your bank accounts, credit cards, and investment accounts in just a few clicks."
  },
  {
    logo: "clock", 
    title: "Track your expenses in real time",
    description: "Automatically categorize transactions and monitor your spending patterns with intelligent insights."
  },
  {
    logo: "chart",
    title: "Set financial goals",
    description: "Create personalized budgets and savings goals that align with your financial aspirations."
  },
  {
    number: "wallet",
    title: "Achieve financial freedom",
    description: "Get actionable recommendations and stay on track to reach your financial milestones."
  }
];

const ProcessSection = () => {
  

  return (
    <section className="py-16 lg:py-24 bg-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid lg:grid-cols-2 gap-12 items-center">
          {/* Left side - Dashboard mockup */}
          <Image 
            src={"/landing-page/img-home-how-it-works.png"}
            alt="Dashboard Mockup"
            width={0}
            height={0}
            sizes="100vw"
            className="w-auto h-auto"
          />

          {/* Right side - Process steps */}
          <div className="order-1 lg:order-2">
            <div className="mb-8">
              <h2 className="text-3xl lg:text-4xl font-bold text-gray-900 mb-4">
                Understand the Process
              </h2>
              <p className="text-lg font-semibold text-[#888888]">
                We're here to help! Whether you have a question, need support, or just want to learn more about Payap, feel free to reach out to us.
              </p>
            </div>
            
            {/* Steps */}
            <div className="space-y-8">
              {steps.map((step, index) => (
                <div key={index} className="flex items-start space-x-4">
                  <div className="shrink-0">
                    {
                      step.logo === "ContactRound" ? (
                        <ContactRound className="h-10 w-10 text-[#888888]" />
                      ) : 
                        step.logo === "clock" ? 
                          <Clock4 className="h-10 w-10 text-[#888888]" />
                        : 
                        step.logo === "chart" ? 
                          <ChartColumnBig className="h-10 w-10 text-[#888888]" />
                        : 
                        <Wallet className="h-10 w-10 text-[#888888]" />
                    }
                  </div>
                  <div>
                    <h3 className="text-xl font-semibold text-black mb-2">
                      {step.title}
                    </h3>
                    <p className="text-[#888888] leading-relaxed">
                      {step.description}
                    </p>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default ProcessSection;