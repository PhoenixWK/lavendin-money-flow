'use client';

import { useState } from 'react';
import { Eye, EyeOff } from 'lucide-react';
import { FaGoogle } from 'react-icons/fa6';

export default function SignupForm() {
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
    });
    const [isLoading, setIsLoading] = useState(false);
    const [errors, setErrors] = useState<Record<string, string>>({});

    const validateForm = () => {
        const newErrors: Record<string, string> = {};

        if (!formData.firstName.trim()) {
        newErrors.firstName = 'First name is required';
        }

        if (!formData.lastName.trim()) {
        newErrors.lastName = 'Last name is required';
        }

        if (!formData.email.trim()) {
        newErrors.email = 'Email is required';
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
        newErrors.email = 'Please enter a valid email';
        }

        if (!formData.password) {
        newErrors.password = 'Password is required';
        } else if (formData.password.length < 8) {
        newErrors.password = 'Password must be at least 8 characters';
        }

        if (!formData.confirmPassword) {
        newErrors.confirmPassword = 'Please confirm your password';
        } else if (formData.password !== formData.confirmPassword) {
        newErrors.confirmPassword = 'Passwords do not match';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!validateForm()) {
            return;
        }

        setIsLoading(true);
        try {
        // TODO: Add signup logic here
            console.log('Signup attempted with:', {
                firstName: formData.firstName,
                lastName: formData.lastName,
                email: formData.email,
            });
        } finally {
            setIsLoading(false);
        }
    };

    const handleGoogleSignup = () => {
        // TODO: Implement Google OAuth
        console.log('Google signup clicked');
    };

    const handleAppleSignup = () => {
        // TODO: Implement Apple OAuth
        console.log('Apple signup clicked');
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
            setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
        // Clear error for this field
        if (errors[name]) {
            setErrors((prev) => ({
                ...prev,
                [name]: '',
            }));
        }
    };

    return (
        <div className="w-full">
            <div className="mb-8 text-center">
                <h1 className="text-3xl font-bold text-gray-900 mb-2">Welcome to Lavendin</h1>
                <p className="text-gray-600">Enter your details to begin your journey</p>
            </div>

            {/* Social Signup Buttons */}
            <div className="space-y-3 mb-6">
                <button
                    onClick={handleGoogleSignup}
                    className="w-full py-3 px-4 border border-gray-300 rounded-lg font-medium text-gray-700 hover:bg-gray-50 transition-colors flex items-center justify-center gap-2"
                >
                <FaGoogle className="w-5 h-5" />
                    Sign up with Google
                </button>
            </div>

            {/* Divider */}
            <div className="flex items-center gap-4 mb-6">
                <div className="flex-1 border-t border-gray-300"></div>
                <span className="text-gray-500 text-sm">Or</span>
                <div className="flex-1 border-t border-gray-300"></div>
            </div>

            {/* Signup Form */}
            <form onSubmit={handleSubmit} className="space-y-4">
                {/* First and Last Name Fields */}
                <div className="grid grid-cols-2 gap-4">
                <div>
                    <label htmlFor="firstName" className="block text-sm font-medium text-gray-700 mb-2">
                    First name <span className="text-red-500">*</span>
                    </label>
                    <input
                    id="firstName"
                    type="text"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                    placeholder="John"
                    className={`w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent transition-all ${
                        errors.firstName ? 'border-red-500' : 'border-gray-300'
                    }`}
                    />
                    {errors.firstName && <p className="text-red-500 text-xs mt-1">{errors.firstName}</p>}
                </div>

                <div>
                    <label htmlFor="lastName" className="block text-sm font-medium text-gray-700 mb-2">
                    First name <span className="text-red-500">*</span>
                    </label>
                    <input
                    id="lastName"
                    type="text"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleChange}
                    placeholder="Doe"
                    className={`w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent transition-all ${
                        errors.lastName ? 'border-red-500' : 'border-gray-300'
                    }`}
                    />
                    {errors.lastName && <p className="text-red-500 text-xs mt-1">{errors.lastName}</p>}
                </div>
                </div>

                {/* Email Field */}
                <div>
                <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-2">
                    Email <span className="text-red-500">*</span>
                </label>
                <input
                    id="email"
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    placeholder="johndoe@company.com"
                    className={`w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent transition-all ${
                    errors.email ? 'border-red-500' : 'border-gray-300'
                    }`}
                />
                {errors.email && <p className="text-red-500 text-xs mt-1">{errors.email}</p>}
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
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Minimum 8 characters"
                    className={`w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent transition-all ${
                        errors.password ? 'border-red-500' : 'border-gray-300'
                    }`}
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
                {errors.password && <p className="text-red-500 text-xs mt-1">{errors.password}</p>}
                </div>

                {/* Confirm Password Field */}
                <div>
                <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700 mb-2">
                    Confirm Password <span className="text-red-500">*</span>
                </label>
                <div className="relative">
                    <input
                    id="confirmPassword"
                    type={showConfirmPassword ? 'text' : 'password'}
                    name="confirmPassword"
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    placeholder="Re-enter password"
                    className={`w-full px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent transition-all ${
                        errors.confirmPassword ? 'border-red-500' : 'border-gray-300'
                    }`}
                    />
                    <button
                    type="button"
                    onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                    className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-500 hover:text-gray-700 transition-colors"
                    >
                    {showConfirmPassword ? (
                        <EyeOff className="w-5 h-5" />
                    ) : (
                        <Eye className="w-5 h-5" />
                    )}
                    </button>
                </div>
                {errors.confirmPassword && (
                    <p className="text-red-500 text-xs mt-1">{errors.confirmPassword}</p>
                )}
                </div>

                {/* Create Account Button */}
                <button
                type="submit"
                disabled={isLoading}
                className="w-full py-3 bg-green-500 hover:bg-green-600 disabled:bg-green-400 text-white font-semibold rounded-lg transition-colors mt-6"
                >
                {isLoading ? 'Creating Account...' : 'Create Account'}
                </button>
            </form>

            {/* Terms and Privacy */}
            <p className="text-center text-xs text-gray-600 mt-4">
                By click Sign Up, you agree to our{' '}
                <a href="#" className="underline hover:text-gray-900 transition-colors">
                Terms of Use
                </a>{' '}
                and that you have read our{' '}
                <a href="#" className="underline hover:text-gray-900 transition-colors">
                Privacy Policy
                </a>
            </p>
        </div>
    );
}
