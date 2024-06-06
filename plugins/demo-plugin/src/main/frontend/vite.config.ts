import react from '@vitejs/plugin-react-swc';
import {resolve} from 'path';
import {defineConfig} from 'vite';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  build: {
    minify: false,
    lib: {
      formats: ['umd'],
      entry: resolve(__dirname, 'src/components/index.tsx'),
      fileName: format => `index.${format}.js`,
      name: 'plugin',
    },
    rollupOptions: {external: ['react', 'reactflow'], output: {globals: {react: 'React'}}},
  },
  define: {'process.env.NODE_ENV': '"production"'},
});
