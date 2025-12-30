'use client';

import { useState } from 'react';
import Header from './Header';
import Sidebar from './Sidebar';
import OverviewCard from './OverviewCard';
import WeeklySummary from './WeeklySummary';
import RevenueChart from './RevenueChart';
import LatestTransactions from './LatestTransactions';
import SalesByLocations from './SalesByLocations';
import ScheduledPayments from './ScheduledPayments';

export default function DashboardLayout() {
    const [sidebarOpen, setSidebarOpen] = useState(false);

    return (
        <div className="flex h-screen bg-gray-50">
            <Sidebar isOpen={sidebarOpen} onClose={() => setSidebarOpen(false)} />

            <div className="flex-1 flex flex-col overflow-hidden lg:ml-0">
                <Header onMenuClick={() => setSidebarOpen(!sidebarOpen)} />

                <main className="flex-1 overflow-y-auto">
                <div className="p-4 lg:p-8">
                    {/* Overview Cards */}
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
                    <OverviewCard
                        title="Total Balance"
                        amount="$22,908"
                        change="24%"
                        changeType="positive"
                        bgColor="bg-pink-100"
                    />
                    <OverviewCard
                        title="Total Income"
                        amount="$29,092"
                        change="16%"
                        changeType="positive"
                        bgColor="bg-green-100"
                    />
                    <OverviewCard
                        title="Total Outcome"
                        amount="$6,184"
                        change="33%"
                        changeType="negative"
                        bgColor="bg-red-100"
                    />
                    <WeeklySummary />
                    </div>

                    {/* Revenue Chart */}
                    <div className="mb-8">
                    <RevenueChart />
                    </div>

                    {/* Bottom Section - Transactions and Locations */}
                    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                    <div className="lg:col-span-2">
                        <LatestTransactions />
                    </div>
                    <div className="space-y-8">
                        <SalesByLocations />
                        <ScheduledPayments />
                    </div>
                    </div>
                </div>
                </main>
            </div>
        </div>
    );
}
