'use client';

import { Menu, Bell, Search } from 'lucide-react';

interface HeaderProps {
  onMenuClick: () => void;
}

export default function Header({ onMenuClick }: HeaderProps) {
    return (
        <div className="sticky top-0 bg-white border-b border-gray-200 z-40">
            <div className="flex items-center justify-between px-4 lg:px-8 py-4">
                {/* Left side - Menu button and title */}
                <div className="flex items-center gap-4">
                    <button
                        onClick={onMenuClick}
                        className="lg:hidden p-2 hover:bg-gray-100 rounded-lg transition-colors"
                    >
                        <Menu size={24} />
                    </button>
                    <h1 className="text-2xl font-bold text-gray-900">Overviews</h1>
                </div>

                {/* Right side - Search, notification, profile */}
                <div className="flex items-center gap-4">
                    <div className="hidden md:flex items-center bg-gray-100 rounded-lg px-4 py-2 gap-2">
                        <Search size={20} className="text-gray-500" />
                        <input
                        type="text"
                        placeholder="Search..."
                        className="bg-transparent outline-none text-sm w-48"
                        />
                    </div>

                    <button className="p-2 hover:bg-gray-100 rounded-lg transition-colors relative">
                        <Bell size={24} className="text-gray-600" />
                        <span className="absolute top-1 right-1 w-2 h-2 bg-red-500 rounded-full"></span>
                    </button>

                    <button className="w-10 h-10 rounded-full bg-linear-to-br from-purple-400 to-pink-400 flex items-center justify-center text-white font-semibold">
                        JD
                    </button>
                </div>
            </div>
        </div>
    );
}
