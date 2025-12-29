import Link from 'next/link';
import Image from 'next/image';
import LoginForm from './LoginForm';
import LoginHero from './LoginHero';
import { FaGoogle } from "react-icons/fa";

export default function LoginPage() {
    return (
        <div className="min-h-screen bg-white flex flex-col lg:flex-row">
            {/* Left Section - Login Form */}
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
                        href={"/signup"}
                        className='flex items-center justify-center gap-2'
                    >
                        <p className="text-center text-gray-600">
                            Don't have an account?{' '}
                        </p>
                        <span className='text-[#3fd58a] font-semibold'>Sign up</span>
                    </Link>
                </div>

                    {/* Form Container */}
                <div className="flex-1 flex flex-col items-center justify-center gap-4 sm:min-w-md mx-auto">
                    <LoginForm />
                    {/* Footer */}
                <div className="flex items-center justify-center gap-4 text-xs text-gray-600">
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
                {/* <LoginHero /> */}
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
