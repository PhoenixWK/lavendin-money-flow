'use client';

import Image from 'next/image';
import Link from 'next/link';

import { X } from 'lucide-react';
import { MdOutlineDashboard, MdOutlineSavings, MdOutlineSettings } from 'react-icons/md';
import { TbInvoice } from 'react-icons/tb';
import { LuWalletMinimal } from 'react-icons/lu';
import { AiFillCalculator } from 'react-icons/ai';
import { usePathname } from 'next/navigation';

interface SidebarProps {
    isOpen: boolean;
    onClose: () => void;
}



export default function Sidebar({ isOpen, onClose }: SidebarProps) {

    const path = usePathname();

    const menuItems = [
        { icon: <MdOutlineDashboard className={`${path === '/dashboard' ? 'text-green-500' : 'text-black'} w-10 h-10`} />, label: 'Dashboard', href: '/dashboard' },
        { icon: <TbInvoice className='w-10 h-10 text-black' />, label: 'Transactions', href: '/transactions' },
        { icon: <LuWalletMinimal className='w-10 h-10 text-black' />, label: 'Wallets', href: '/wallets' },
        { icon: <AiFillCalculator className='w-10 h-10 text-black' />, label: 'Budgets', href: '/budgets' },
        { icon: <MdOutlineSavings className='w-10 h-10 text-black' />, label: 'Savings', href: '/savings' },
    ];

    return (
        <>
        {/* Mobile Overlay */}
            {isOpen && (
                <div
                className="fixed inset-0 bg-black/50 z-40 lg:hidden"
                onClick={onClose}
                />
            )}

            {/* Sidebar */}
            <div
                className={`fixed left-0 top-0 h-screen w-20 bg-white border-r border-gray-200 flex flex-col items-center py-4 gap-8 z-50 transition-all duration-300 lg:sticky lg:w-20 ${
                isOpen ? 'translate-x-0' : '-translate-x-full lg:translate-x-0'
                }`}
            >
                {/* Close button for mobile */}
                <button
                    onClick={onClose}
                    className="absolute top-4 right-4 lg:hidden text-gray-600"
                >
                    <X size={24} />
                </button>

                {/* Logo/Brand */}
                <Image 
                    src="/logo.png"
                    alt="Logo"
                    width={40}
                    height={40}
                    className='w-auto h-auto'
                />

                {/* Menu Items */}
                <nav className="flex flex-col gap-6">
                    {menuItems.map((item, index) => (
                        <Link
                            key={index}
                            href={item.href}
                            className="w-12 h-12 flex items-center justify-center rounded-lg hover:bg-gray-100 transition-colors text-2xl"
                            title={item.label}
                        >
                        {item.icon}
                        </Link>
                    ))}
                </nav>

                 Bottom icons
                <div className="mt-auto flex flex-col gap-6">
                    <button className="w-12 h-12 flex items-center justify-center rounded-lg hover:bg-gray-100 transition-colors text-2xl">
                        <MdOutlineSettings className="w-10 h-10 text-black" />
                    </button>
                    
                </div>
            </div>
        </>
    );
}
