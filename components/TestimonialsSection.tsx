import { useLanguage } from "@/hooks/useLanguage";
import { Star } from "lucide-react";

const testimonials = {
  en: [
    {
      name: "Sarah Chen",
      role: "Marketing Professional",
      image: "SC",
      rating: 5,
      quote:
        "Lavendin completely transformed how I manage my finances. I can finally see where my money goes!",
    },
    {
      name: "Michael Rodriguez",
      role: "Software Developer",
      image: "MR",
      rating: 5,
      quote:
        "The UI is so clean and intuitive. I recommend it to all my friends. Best financial app I've used.",
    },
    {
      name: "Emma Thompson",
      role: "Business Student",
      image: "ET",
      rating: 5,
      quote:
        "As a student, I needed something affordable and powerful. Lavendin exceeded my expectations!",
    },
  ],
  vi: [
    {
      name: "Sarah Chen",
      role: "Chuyên gia Marketing",
      image: "SC",
      rating: 5,
      quote:
        "Lavendin hoàn toàn thay đổi cách tôi quản lý tài chính. Cuối cùng tôi có thể thấy tiền đi đâu!",
    },
    {
      name: "Michael Rodriguez",
      role: "Lập trình viên",
      image: "MR",
      rating: 5,
      quote:
        "Giao diện rất sạch sẽ và trực quan. Tôi giới thiệu nó cho tất cả bạn bè của tôi. Ứng dụng tài chính hay nhất mà tôi đã sử dụng.",
    },
    {
      name: "Emma Thompson",
      role: "Sinh viên kinh doanh",
      image: "ET",
      rating: 5,
      quote:
        "Là một sinh viên, tôi cần thứ gì đó giá rẻ và mạnh mẽ. Lavendin vượt quá kỳ vọng của tôi!",
    },
  ],
};

export default function TestimonialsSection() {
  const { language } = useLanguage();
  const currentTestimonials =
    testimonials[language as keyof typeof testimonials] || testimonials.en;

  return (
    <section className="py-20 px-6 bg-gradient-to-b from-green-50 to-white">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-16">
          <h2 className="text-4xl md:text-5xl font-bold text-gray-900 mb-4">
            {language === "en"
              ? "Loved by Users Worldwide"
              : "Được Yêu Thích Bởi Người Dùng Trên Toàn Thế Giới"}
          </h2>
          <p className="text-xl text-gray-600 max-w-2xl mx-auto">
            {language === "en"
              ? "Join thousands of young professionals managing their finances better"
              : "Tham gia hàng ngàn chuyên gia trẻ quản lý tài chính tốt hơn"}
          </p>
        </div>

        <div className="grid md:grid-cols-3 gap-8">
          {currentTestimonials.map((testimonial, index) => (
            <div
              key={index}
              className="bg-white border border-green-100 rounded-2xl p-8 hover:shadow-lg transition-all duration-300"
            >
              <div className="flex gap-1 mb-6">
                {Array.from({ length: testimonial.rating }).map((_, i) => (
                  <Star
                    key={i}
                    className="w-5 h-5 fill-primary text-primary"
                  />
                ))}
              </div>

              <p className="text-gray-700 leading-relaxed mb-6 italic">
                "{testimonial.quote}"
              </p>

              <div className="flex items-center gap-4 pt-6 border-t border-green-100">
                <div className="w-12 h-12 rounded-full bg-gradient-to-br from-primary to-primary/80 flex items-center justify-center text-white font-bold text-sm">
                  {testimonial.image}
                </div>

                <div>
                  <p className="font-semibold text-gray-900">
                    {testimonial.name}
                  </p>
                  <p className="text-sm text-gray-600">{testimonial.role}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
