'use client';

import Link from 'next/link';
import Image from 'next/image';
import { useState } from 'react';


const Header = () => {
    const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

    const toggleMobileMenu = () => {
        setIsMobileMenuOpen(!isMobileMenuOpen);
    };

    return (
        <header className="bg-white shadow-sm sticky top-0 z-50">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex justify-between items-center h-16">
                    {/* Logo */}
                    <Link
                        href={"/"}
                        className='flex items-center gap-2'
                    >
                        <Image 
                            src={"/logo.png"}
                            alt="Lavendin Logo"
                            width={40}
                            height={40}
                        />
                        <div className="text-2xl font-bold text-[#47EB98]">Lavendin</div>
                    </Link>

                    {/* Desktop Navigation */}
                    <nav className="hidden md:flex space-x-8">
                        <Link href="/features" className="text-black font-semibold hover:text-[#47EB98] transition-colors">
                            Features
                        </Link>
                        <Link href="/pricing" className="text-black font-semibold hover:text-[#47EB98] transition-colors">
                            Pricing
                        </Link>
                        <Link href="/about" className="text-black font-semibold hover:text-[#47EB98] transition-colors">
                            About
                        </Link>
                        <Link href="/contact" className="text-black font-semibold hover:text-[#47EB98] transition-colors">
                            Contact
                        </Link>
                    </nav>

                    {/* Desktop Auth Links */}
                    <div className="hidden md:flex items-center space-x-4">
                        <Link
                            href={"/login"}
                            className='bg-[#FAF8F6] hover:bg-[#E0E0E0] text-center text-black font-semibold px-4 py-2 rounded-lg'
                        >
                            Login
                        </Link>
                    </div>

                    {/* Mobile menu button */}
                    <button
                        onClick={toggleMobileMenu}
                        className="md:hidden p-2 rounded-md text-gray-400 hover:text-gray-500 hover:bg-gray-100"
                        aria-label="Toggle mobile menu"
                    >
                        <svg
                            className="h-6 w-6"
                            xmlns="http://www.w3.org/2000/svg"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                        >
                            {isMobileMenuOpen ? (
                                <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                strokeWidth={2}
                                d="M6 18L18 6M6 6l12 12"
                                />
                            ) : (
                                <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                strokeWidth={2}
                                d="M4 6h16M4 12h16M4 18h16"
                                />
                            )}
                        </svg>
                    </button>
                </div>
            </div>

            {/* Mobile sidebar */}
            <div className={`md:hidden fixed inset-0 z-50 ${isMobileMenuOpen ? 'block' : 'hidden'}`}>
                {/* Backdrop */}
                <div
                    className="fixed inset-0 bg-black bg-opacity-50"
                    onClick={toggleMobileMenu}
                ></div>
                
                {/* Sidebar */}
                <div className="fixed right-0 top-0 bottom-0 w-64 bg-white shadow-lg overflow-y-auto">
                    <div className="p-6">
                        {/* Close button */}
                        <div className="flex justify-between items-center mb-8">
                            <div className="text-2xl font-bold text-[#47EB98]">Lavendin</div>
                            <button
                                onClick={toggleMobileMenu}
                                className="p-2 rounded-md text-black hover:text-[#47EB98] hover:bg-gray-100"
                            >
                                <svg className="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            </button>
                        </div>

                        {/* Mobile Navigation */}
                        <nav className="flex flex-col space-y-4">
                            <a 
                                href="#features" 
                                className="text-black font-semibold hover:text-[#47EB98] transition-colors py-2"
                                onClick={toggleMobileMenu}
                            >
                                Features
                            </a>
                            <a 
                                href="#pricing" 
                                className="text-black font-semibold hover:text-[#47EB98] transition-colors py-2"
                                onClick={toggleMobileMenu}
                            >
                                Pricing
                            </a>
                            <a 
                                href="#about" 
                                className="text-black font-semibold hover:text-[#47EB98] transition-colors py-2"
                                onClick={toggleMobileMenu}
                            >
                                About
                            </a>
                            <a 
                                href="#contact" 
                                className="text-black font-semibold hover:text-[#47EB98] transition-colors py-2"
                                onClick={toggleMobileMenu}
                            >
                                Contact
                            </a>
                        </nav>

                        {/* Mobile Auth Links */}
                        <div className="mt-8 pt-8 border-t border-gray-200">
                            <div className="flex flex-col space-y-4">
                                <Link
                                    href={"/login"}
                                    className='text-black font-semibold '
                                >
                                    Login
                                </Link>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
        </header>
    );
};

export default Header;