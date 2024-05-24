import {resolve} from "path"
import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  build: {
    minify: false,
    lib: {
      formats: ["umd"],
      entry: resolve(__dirname, "index.tsx"),
      name: "http-comp",
      fileName: (format) => `out.${format}.js`
    },
    rollupOptions: {
      external: ["react"],
      output: {
        globals: {react: "React"}
      }
    }
  },
  define: {'process.env.NODE_ENV': '"production"'}
})
