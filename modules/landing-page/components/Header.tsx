export default function Header() {
  return (
    <header className="sticky top-0 z-50 bg-white/80 backdrop-blur-md border-b border-green-100">
      <div className="max-w-6xl mx-auto px-6 py-4 flex justify-between items-center">
        <h1 className="text-2xl font-bold text-primary">Lavendin</h1>
        
        <nav className="flex items-center gap-6">
          <a href="#features" className="text-gray-700 hover:text-primary transition-colors">Features</a>
          <a href="#pricing" className="text-gray-700 hover:text-primary transition-colors">Pricing</a>
          <a href="#contact" className="text-gray-700 hover:text-primary transition-colors">Contact</a>
        </nav>
      </div>
    </header>
  );
}
