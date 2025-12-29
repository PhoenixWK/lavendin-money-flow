'use client';

const TrustedBy = () => {
  const companies = [
    { name: 'Monzo', logo: 'monzo' },
    { name: 'Monday.com', logo: 'monday' },
    { name: 'Databricks', logo: 'databricks' },
    { name: 'Pipedrive', logo: 'pipedrive' },
    { name: 'Treehouse', logo: 'treehouse' },
    { name: 'Contentful', logo: 'contentful' },
  ];

  return (
    <section className="py-16 bg-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-12">
          <h3 className="text-xl font-semibold text-gray-900">
            Trusted by 7,000+ apps and the world's largest app publishers
          </h3>
        </div>
        
        <div className="flex flex-wrap items-center justify-center gap-8 md:gap-12 lg:gap-16 opacity-60">
          {companies.map((company, index) => (
            <div key={index} className="flex items-center">
              <div className="text-gray-400 font-medium text-lg">
                {company.name}
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default TrustedBy;