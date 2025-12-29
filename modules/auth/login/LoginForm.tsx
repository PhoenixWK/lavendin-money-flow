'use client';

import { useState } from 'react';
import { Eye, EyeOff } from 'lucide-react';
import { FaGoogle } from 'react-icons/fa6';

export default function LoginForm() {
    const [showPassword, setShowPassword] = useState(false);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        // Handle login logic here
        try {
        // TODO: Add authentication logic
        console.log('Login attempted with:', { email, password });
        } finally {
        setIsLoading(false);
        }
    };

    const handleGoogleLogin = () => {
        // TODO: Implement Google OAuth
        console.log('Google login clicked');
    };

    const handleAppleLogin = () => {
        // TODO: Implement Apple OAuth
        console.log('Apple login clicked');
    };

    return (
        <div className="w-full">
            <div className="mb-8">
                <h1 className="text-3xl font-bold text-gray-900 mb-2">Login</h1>
                <p className="text-gray-600">
                    Enter your credentials below to access your account
                </p>
            </div>

            {/* Social Login Buttons */}
            <div className="space-y-3 mb-6">
                <button
                    onClick={handleGoogleLogin}
                    className="w-full py-3 px-4 border border-gray-300 rounded-lg font-medium text-gray-700 hover:bg-gray-50 transition-colors flex items-center justify-center gap-2"
                >
                <FaGoogle className="w-5 h-5" />
                Login with Google
                </button>
            </div>

            {/* Divider */}
            <div className="flex items-center gap-4 mb-6">
                <div className="flex-1 border-t border-gray-300"></div>
                <span className="text-gray-500 text-sm">Or</span>
                <div className="flex-1 border-t border-gray-300"></div>
            </div>

            {/* Login Form */}
            <form onSubmit={handleSubmit} className="space-y-4">
                {/* Email Field */}
                <div>
                <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-2">
                    Email <span className="text-red-500">*</span>
                </label>
                <input
                    id="email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="johndoe@company.com"
                    className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent transition-all"
                    required
                />
                </div>

                {/* Password Field */}
                <div>
                <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-2">
                    Password <span className="text-red-500">*</span>
                </label>
                <div className="relative">
                    <input
                    id="password"
                    type={showPassword ? 'text' : 'password'}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Enter your password"
                    className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent transition-all"
                    required
                    />
                    <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700 transition-colors"
                    >
                    {showPassword ? (
                        <EyeOff className="w-5 h-5" />
                    ) : (
                        <Eye className="w-5 h-5" />
                    )}
                    </button>
                </div>
                </div>

                {/* Forgot Password Link */}
                <div className="flex justify-end">
                <a
                    href="/auth/forgot-password"
                    className="text-sm text-green-600 hover:text-green-700 font-medium transition-colors"
                >
                    Forgot your password? Reset password
                </a>
                </div>

                {/* Login Button */}
                <button
                type="submit"
                disabled={isLoading}
                className="w-full py-3 bg-[#47EB98] hover:bg-green-600 disabled:bg-green-400 text-black font-semibold rounded-lg transition-colors"
                >
                {isLoading ? 'Logging in...' : 'Login'}
                </button>
            </form>

            {/* Sign Up Link */}
            <p className="text-center text-gray-600 mt-6">
                Don't have an account?{' '}
                <a href="/auth/signup" className="text-green-600 hover:text-green-700 font-medium transition-colors">
                Sign up
                </a>
            </p>
        </div>
    );
}
