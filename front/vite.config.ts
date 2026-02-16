export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      '/rest': {
        target: 'http://localhost:9011',
        changeOrigin: true,
      },
    },
  },
})
