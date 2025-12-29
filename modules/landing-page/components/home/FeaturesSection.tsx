
import Image from "next/image";


const features = [
  {
    title: "Track your finances in real-time, anytime, anywhere",
    includes: [
      "Immediate visibility into spending habits",
      "Ability to adjust budget in real time"
    ],
    img: "/landing-page/instant-expense-updates.png"
  },
  {
    title: "Automated expense categorization",
    includes: [
      "No manual sorting or categorizing",
      "Financial data is always organized"
    ],
    img: "/landing-page/automated-expense-categorization.png"
  },
  {
    title: "Visual financial insights",
    includes: [
      "Clear, easy-to-read charts and graphs",
      "Spot trends and anomalies quickly"
    ],
    img: "/landing-page/visual-expense-insight.png"
  },
  {
    title: "Cross-platform accessibility",
    includes: [
      "Access your financial data",
      "Seamless experience across web, mobile, and tablet"
    ],
    img: "/landing-page/cross-platform-accessibility.png"
  },
];


const FeaturesSection = () => {
    return (
      <div className="px-4 sm:px-6 lg:px-14 py-16 lg:py-24 space-y-12">
        <div className="flex flex-col gap-12 items-center">
          {/* Left side - Text content */}
          <div className="text-center">
              <h1 className="text-2xl text-center md:text-3xl lg:text-4xl font-bold text-gray-900 leading-tight">
                Track your finances in real-time, anytime, anywhere
              </h1>
              
              <p className="mt-6 text-lg text-center md:text-xl text-gray-600 leading-relaxed">
                Gain full control over your spending with live updates on every transaction, available across all your devices, no matter where you are.
              </p>
              
          </div>
        </div>
        <div className=" grid grid-cols-2 gap-8 place-items-center">
          {features.map((feature) => (
            <section className=" h-auto bg-[#FAF8F6] p-4 md:p-8 rounded-lg flex flex-col gap-6" key={feature.title}>
              <h2 className="text-2xl text-black font-semibold">{feature.title}</h2>
              <ul className="flex flex-col gap-2 md:gap-0 md:flex-row justify-between">
                <li className="flex items-center gap-2">
                  <div className="w-2 md:w-4 h-2 md:h-4 border-2 border-[#47EB98] rounded-full"></div>
                  <span className="text-black">{feature.includes[0]}</span>
                </li>
                <li className="flex items-center gap-2">
                  <div className="w-2 md:w-4 h-2 md:h-4 border-2 border-[#47EB98] rounded-full"></div>
                  <span className="text-black">{feature.includes[1]}</span>
                </li>
              </ul>
              <Image 
                src={feature.img}
                alt={feature.title}
                width={0}
                height={0}
                sizes="100vw"
                className="w-auto h-auto"
              />
            </section>
          ))}
        </div>
      </div>
    )
  
};

export default FeaturesSection;