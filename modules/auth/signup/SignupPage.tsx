
import Image from 'next/image';
import Link from 'next/link';

import SignupForm from './SignupForm';
import LoginHero from '../login/LoginHero';

export default function SignupPage() {
    return (
        <div className="min-h-screen bg-white flex">
            {/* Left Section - Signup Form */}
            <div className="w-full lg:w-1/2 flex flex-col justify-between p-8 lg:p-12">
                {/* Header */}
                <div className="flex items-center justify-between mb-8">
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
                    <Link
                        href={"/login"}
                        className='flex items-center justify-center gap-2'
                    >
                        <p className="text-center text-gray-600">
                            Already have an account?{' '}
                        </p>
                        <span className='text-[#3fd58a] font-semibold'>Log in</span>
                    </Link>
                </div>

                {/* Form Container */}
                <div className="flex-1 flex flex-col gap-4 items-center justify-center min-w-md mx-auto">
                    <SignupForm />
                    {/* Footer */}
                    <div className="flex gap-4 text-xs text-gray-600">
                        <a href="#" className="hover:text-gray-900 transition-colors">
                            Terms
                        </a>
                        <a href="#" className="hover:text-gray-900 transition-colors">
                            Privacy policy
                        </a>
                    </div>
                </div>          
            </div>

            {/* Right Section - Hero */}
            <div className="hidden lg:flex w-1/2 p-8">
                <Image 
                    src={"/auth-page/login/main.png"}
                    alt="Login Hero"
                    width={0}
                    height={0}
                    sizes='100vw'
                    loading='lazy'
                    className='w-auto h-auto'
                />
            </div>
        </div>
    );
}
