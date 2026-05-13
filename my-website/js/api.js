/* ============================================
   API 请求封装模块
   前后端分离 - 对接 Spring Boot 后端
   ============================================ */

// Vite 开发模式下通过代理转发，生产环境直接访问后端
const API_BASE = '';

const api = {
  async get(path) {
    try {
      const res = await fetch(API_BASE + path);
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      return await res.json();
    } catch (err) {
      console.error(`[API GET] ${path} failed:`, err);
      return null;
    }
  },

  async post(path, data) {
    try {
      const res = await fetch(API_BASE + path, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
      });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      return await res.json();
    } catch (err) {
      console.error(`[API POST] ${path} failed:`, err);
      return null;
    }
  }
};

/* 动态渲染后重新初始化动画观察器 */
function reinitAnimations() {
  const elements = document.querySelectorAll('.fade-in:not(.visible), .fade-in-left:not(.visible), .fade-in-right:not(.visible), .scale-in:not(.visible)');
  if (elements.length === 0) return;

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        const delay = entry.target.dataset.delay || 0;
        setTimeout(() => {
          entry.target.classList.add('visible');
        }, delay);
        observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.15, rootMargin: '0px 0px -50px 0px' });

  elements.forEach(el => observer.observe(el));
}

/* 动态渲染后重新初始化计数器 */
function reinitCounters() {
  const counters = document.querySelectorAll('.counter:not(.counted)');
  if (counters.length === 0) return;

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        entry.target.classList.add('counted');
        animateCounter(entry.target);
        observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.5 });

  counters.forEach(counter => observer.observe(counter));
}

function animateCounter(el) {
  const raw = String(el.dataset.target).replace(/,/g, '');  // 去掉千分位逗号
  const target = parseFloat(raw);
  const suffix = el.dataset.suffix || '';
  const hasComma = String(el.dataset.target).includes(',');  // 原始值有逗号则显示时也加
  const isDecimal = raw.includes('.');
  const decimals = isDecimal ? (raw.split('.')[1] || '').length : 0;
  const duration = 2000;
  const startTime = performance.now();

  function fmt(n) {
    var s = isDecimal ? n.toFixed(decimals) : String(Math.floor(n));
    if (hasComma) s = s.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    return s + suffix;
  }

  function update(currentTime) {
    const elapsed = currentTime - startTime;
    const progress = Math.min(elapsed / duration, 1);
    const eased = 1 - Math.pow(1 - progress, 3);
    const value = eased * target;

    el.textContent = fmt(value);

    if (progress < 1) {
      requestAnimationFrame(update);
    } else {
      el.textContent = fmt(target);
    }
  }

  requestAnimationFrame(update);
}
