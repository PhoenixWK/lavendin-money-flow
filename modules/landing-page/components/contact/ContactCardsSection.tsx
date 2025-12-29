import Link from 'next/link';
import { MailIcon, PhoneIcon, FileTextIcon, MapPinIcon } from 'lucide-react';

interface ContactCard {
    icon: React.ReactNode;
    title: string;
    description: string;
    content: string;
    ctaText?: string;
    ctaHref?: string;
}

const contactCards: ContactCard[] = [
    {
        icon: <MailIcon className="w-8 h-8 text-black" />,
        title: "Email",
        description: "For any inquiries or support requests",
        content: "support@payop.com",
        ctaText: "Email to Support",
        ctaHref: "mailto:support@payop.com"
    },
    {
        icon: <PhoneIcon className="w-8 h-8 text-black" />,
        title: "Phone Number",
        description: "Speak directly with our support team",
        content: "+1 (800) 123-4567",
        ctaText: "Call Our Team",
        ctaHref: "tel:+18001234567"
    },
    {
        icon: <FileTextIcon className="w-8 h-8 text-black" />,
        title: "Fax",
        description: "Send us your documents securely",
        content: "+1 (800) 987-6543",
    },
    {
        icon: <MapPinIcon className="w-8 h-8 text-black" />,
        title: "Office Address",
        description: "Visit our headquarters",
        content: "Payop, Inc.\n123 Finance Ave, Suite 100\nNew York, NY 10001, USA",
    }
];

export const ContactCardsSection = () => {
  

    return (
        <section className="bg-white py-10 md:py-16">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                    {contactCards.map((card, index) => (
                        <div key={index} className="bg-white rounded-lg p-8 shadow-sm hover:shadow-md transition-shadow">
                            <div className="flex justify-start mb-4">
                                {card.icon}
                            </div>
                            <h3 className="text-lg text-black font-semibold mb-2">
                                {card.title}
                            </h3>
                            <p className="text-gray-600 text-sm mb-4">
                                {card.description}
                            </p>
                            <p className="text-gray-600 font-semibold mb-4 whitespace-pre-line">
                                {card.content}
                            </p>
                            {card.ctaText && card.ctaHref && (
                                <Link
                                href={card.ctaHref}
                                className="text-[#47EB98] font-semibold hover:text-[#3ad280] transition-colors text-sm"
                                >
                                {card.ctaText}
                                </Link>
                            )}
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
};
