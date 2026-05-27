const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');
const path = require('path');

const app = express();
const PORT = 8080;

// API / 上传文件代理 → 后端 Spring Boot (localhost:8081)
// 注意：不能用 app.use('/api', ...) 因为 Express 会去掉前缀
app.use(createProxyMiddleware({
  target: 'http://localhost:8081',
  changeOrigin: true,
  pathFilter: ['/api', '/uploads']
}));

// 静态文件服务
app.use(express.static(path.join(__dirname), {
  extensions: ['html']
}));

app.listen(PORT, () => {
  console.log('');
  console.log('  前端开发服务器已启动:');
  console.log('  ➜ http://localhost:' + PORT);
  console.log('  ➜ API 代理: /api、/uploads → http://localhost:8081');
  console.log('');
});
