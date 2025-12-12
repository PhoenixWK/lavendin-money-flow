import { useState, useEffect } from "react";

type Language = "en" | "vi";

export function useLanguage() {
  const [language, setLanguageState] = useState<Language>(() => {
    if (typeof window !== "undefined") {
      return (localStorage.getItem("language") as Language) || "en";
    }
    return "en";
  });

  const setLanguage = (lang: Language) => {
    setLanguageState(lang);
    if (typeof window !== "undefined") {
      localStorage.setItem("language", lang);
    }
  };

  useEffect(() => {
    if (typeof window !== "undefined") {
      const handleStorageChange = () => {
        const stored = (localStorage.getItem("language") as Language) || "en";
        setLanguageState(stored);
      };
      window.addEventListener("storage", handleStorageChange);
      return () => window.removeEventListener("storage", handleStorageChange);
    }
  }, []);

  return { language, setLanguage };
}
