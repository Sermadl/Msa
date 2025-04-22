/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './index.html',
    './src/**/*.{vue,js,ts,jsx,tsx}'
  ],
  theme: {
    extend: {
      colors: {
        primary: 'rgb(var(--color-primary) / <alpha-value>)',
        'primary-hover': 'rgb(var(--color-primary-hover) / <alpha-value>)',
        secondary: 'rgb(var(--color-secondary) / <alpha-value>)',
        text: 'rgb(var(--color-text) / <alpha-value>)',
        'light-text': 'rgb(var(--color-light-text) / <alpha-value>)',
        border: 'rgb(var(--color-border) / <alpha-value>)',
      },
    },
  },
  plugins: [],
}

