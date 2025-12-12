import { useLanguage } from "@/hooks/useLanguage";
import { Facebook, Twitter, Instagram, Linkedin, Github } from "lucide-react";

const footerContent = {
  en: {
    company: "Company",
    links: [
      { label: "About", href: "#" },
      { label: "Features", href: "#" },
      { label: "Pricing", href: "#" },
      { label: "Contact", href: "#" },
    ],
    legal: "Legal",
    legalLinks: [
      { label: "Privacy Policy", href: "#" },
      { label: "Terms of Service", href: "#" },
    ],
    copyright: "© 2024 Lavendin. All rights reserved.",
    followUs: "Follow Us",
  },
  vi: {
    company: "Công ty",
    links: [
      { label: "Về chúng tôi", href: "#" },
      { label: "Tính năng", href: "#" },
      { label: "Giá cả", href: "#" },
      { label: "Liên hệ", href: "#" },
    ],
    legal: "Pháp lệ",
    legalLinks: [
      { label: "Chính sách Bảo mật", href: "#" },
      { label: "Điều khoản Dịch vụ", href: "#" },
    ],
    copyright: "© 2024 Lavendin. Tất cả quyền được bảo lưu.",
    followUs: "Theo dõi chúng tôi",
  },
};

export default function Footer() {
  const { language } = useLanguage();
  const content =
    footerContent[language as keyof typeof footerContent] || footerContent.en;

  return (
    <footer className="bg-gradient-to-b from-white to-green-50 border-t border-green-100">
      <div className="max-w-6xl mx-auto px-6 py-16">
        <div className="grid md:grid-cols-4 gap-12 mb-12">
          <div>
            <h3 className="text-2xl font-bold text-primary mb-1">Lavendin</h3>
            <p className="text-sm text-gray-600">
              {language === "en"
                ? "Smart personal finance for everyone"
                : "Tài chính cá nhân thông minh cho mọi người"}
            </p>
          </div>

          <div>
            <h4 className="font-semibold text-gray-900 mb-4">
              {content.company}
            </h4>
            <ul className="space-y-2">
              {content.links.map((link, idx) => (
                <li key={idx}>
                  <a
                    href={link.href}
                    className="text-gray-600 hover:text-primary transition-colors text-sm"
                  >
                    {link.label}
                  </a>
                </li>
              ))}
            </ul>
          </div>

          <div>
            <h4 className="font-semibold text-gray-900 mb-4">{content.legal}</h4>
            <ul className="space-y-2">
              {content.legalLinks.map((link, idx) => (
                <li key={idx}>
                  <a
                    href={link.href}
                    className="text-gray-600 hover:text-primary transition-colors text-sm"
                  >
                    {link.label}
                  </a>
                </li>
              ))}
            </ul>
          </div>

          <div>
            <h4 className="font-semibold text-gray-900 mb-4">
              {content.followUs}
            </h4>
            <div className="flex gap-4">
              <a
                href="#"
                className="w-10 h-10 rounded-full bg-green-100 text-primary hover:bg-primary hover:text-white transition-all duration-300 flex items-center justify-center"
              >
                <Facebook className="w-5 h-5" />
              </a>
              <a
                href="#"
                className="w-10 h-10 rounded-full bg-green-100 text-primary hover:bg-primary hover:text-white transition-all duration-300 flex items-center justify-center"
              >
                <Twitter className="w-5 h-5" />
              </a>
              <a
                href="#"
                className="w-10 h-10 rounded-full bg-green-100 text-primary hover:bg-primary hover:text-white transition-all duration-300 flex items-center justify-center"
              >
                <Instagram className="w-5 h-5" />
              </a>
              <a
                href="#"
                className="w-10 h-10 rounded-full bg-green-100 text-primary hover:bg-primary hover:text-white transition-all duration-300 flex items-center justify-center"
              >
                <Linkedin className="w-5 h-5" />
              </a>
            </div>
          </div>
        </div>

        <div className="border-t border-green-100 pt-8 flex flex-col md:flex-row justify-between items-center gap-6">
          <p className="text-gray-600 text-sm">{content.copyright}</p>
        </div>
      </div>
    </footer>
  );
}
