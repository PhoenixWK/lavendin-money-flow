const TestimonialsSection = () => {
  const testimonials = [
    {
      name: "Sarah",
      role: "Small Business Owner",
      content: "This app has revolutionized how I manage my business finances. The real-time tracking and automated categorization save me hours every week.",
      rating: 5,
      avatar: "S"
    },
    {
      name: "Mike Chen", 
      role: "Software Engineer",
      content: "Finally, a budgeting app that actually works. The insights have helped me save over $5,000 this year alone.",
      rating: 5,
      avatar: "M"
    },
    {
      name: "Jessica Brown",
      role: "Marketing Manager", 
      content: "I love how intuitive the interface is. Setting up budgets and tracking expenses has never been this easy.",
      rating: 5,
      avatar: "J"
    },
    {
      name: "David Wilson",
      role: "Freelancer",
      content: "The multi-account support is incredible. I can track my personal and business finances all in one place.",
      rating: 5,
      avatar: "D"
    },
    {
      name: "Emily Davis",
      role: "Teacher",
      content: "As someone who's not great with numbers, this app makes financial planning so much simpler. Highly recommend!",
      rating: 5,
      avatar: "E"
    },
    {
      name: "Alex Johnson", 
      role: "Consultant",
      content: "The security features give me peace of mind. Plus, the mobile app is fantastic for tracking expenses on the go.",
      rating: 5,
      avatar: "A"
    }
  ];

  const StarRating = ({ rating }: { rating: number }) => (
    <div className="flex space-x-1">
      {[...Array(5)].map((_, i) => (
        <svg
          key={i}
          className={`w-4 h-4 ${i < rating ? 'text-yellow-400' : 'text-gray-300'}`}
          fill="currentColor"
          viewBox="0 0 20 20"
        >
          <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
        </svg>
      ))}
    </div>
  );

  return (
    <section className="py-16 lg:py-24 bg-[#FAF8F6]">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Section header */}
        <div className="text-center mb-16">
          <h2 className="text-3xl lg:text-4xl font-bold text-gray-900 mb-4">
            What customers say
          </h2>
          <p className="text-lg text-[#888888] font-semibold max-w-3xl mx-auto">
            Join thousands of satisfied users who have transformed their financial lives 
            with our comprehensive money management platform.
          </p>
        </div>

        {/* Testimonials grid */}
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          {testimonials.map((testimonial, index) => (
            <div 
              key={index} 
              className="bg-[#FAF8F6] border-2 border-[#EEEEEE] rounded-lg shadow-sm p-6 hover:shadow-md transition-shadow"
            >
              {/* Rating */}
              <div className="mb-4">
                <StarRating rating={testimonial.rating} />
              </div>
              
              {/* Testimonial content */}
              <blockquote className="text-gray-700 mb-6 leading-relaxed">
                "{testimonial.content}"
              </blockquote>
              
              {/* Author info */}
              <div className="flex items-center space-x-3">
                <div className="w-10 h-10 bg-emerald-500 text-white rounded-full flex items-center justify-center font-semibold">
                  {testimonial.avatar}
                </div>
                <div>
                  <div className="font-medium text-gray-900">{testimonial.name}</div>
                  <div className="text-sm text-gray-500">{testimonial.role}</div>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default TestimonialsSection;