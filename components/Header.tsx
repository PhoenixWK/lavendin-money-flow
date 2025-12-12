import { useLanguage } from "@/hooks/useLanguage";
import { Globe } from "lucide-react";

export default function Header() {
  const { language, setLanguage } = useLanguage();

  return (
    <header className="sticky top-0 z-50 bg-white/80 backdrop-blur-md border-b border-green-100">
      <div className="max-w-6xl mx-auto px-6 py-4 flex justify-between items-center">
        <h1 className="text-2xl font-bold text-primary">Lavendin</h1>

        <div className="flex items-center gap-6">
          <button
            onClick={() => setLanguage(language === "en" ? "vi" : "en")}
            className="flex items-center gap-2 px-4 py-2 rounded-lg bg-green-100 text-primary hover:bg-primary hover:text-white transition-all duration-300 font-medium text-sm"
          >
            <Globe className="w-4 h-4" />
            {language === "en" ? "Tiếng Việt" : "English"}
          </button>
        </div>
      </div>
    </header>
  );
}
