// ===== 首页 API 数据加载 =====
const CATEGORY_MAP = { EXPORT: '出口动态', IMPORT: '进口业务', VEHICLE: '二手车出口', COMPANY: '企业新闻', EVENT: '展会活动' };

// Emoji 修正：将不兼容 emoji 替换为 Twemoji 图片（解决 Windows 显示问题）
const EMOJI_IMG = {
  '\u{1FAED}': '1fad0',  // 后端错误 emoji → 蓝莓
  '🫐': '1fad0'           // 正确蓝莓 emoji（Windows 也可能不支持）
};
function fixEmoji(s) {
  if (!s) return s;
  var code = EMOJI_IMG[s];
  if (code) return '<img src="https://cdn.jsdelivr.net/gh/twitter/twemoji@14.0.2/assets/svg/' + code + '.svg" alt="🫐" class="emoji-img">';
  return s;
}

function resolveImageUrl(url) {
  if (!url) return '';
  if (/^(https?:)?\/\//i.test(url) || /^data:/i.test(url)) return url;
  if (url.charAt(0) !== '/') return url;

  if (url.indexOf('/uploads/') === 0 &&
      location.hostname === 'localhost' &&
      location.port === '8080') {
    return 'http://localhost:8081' + url;
  }

  return url;
}

function escapeAttr(value) {
  return String(value || '')
    .replace(/&/g, '&amp;')
    .replace(/"/g, '&quot;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;');
}

function renderNewsMedia(item) {
  if (item && item.image) {
    return '<img class="news-card-image" src="' + escapeAttr(resolveImageUrl(item.image)) + '" alt="' + escapeAttr(item.title || 'news image') + '" loading="lazy">';
  }

  return '<span class="news-card-emoji">' + (item && item.emoji ? item.emoji : '馃摪') + '</span>';
}

function initNewsImageFallback() {
  var grid = document.getElementById('news-grid');
  if (!grid) return;
  grid.addEventListener('error', function(e) {
    var image = e.target;
    if (!image || !image.classList || !image.classList.contains('news-card-image')) return;
    var fallback = document.createElement('span');
    fallback.className = 'news-card-emoji';
    fallback.textContent = '馃摪';
    image.replaceWith(fallback);
  }, true);
}

(async function loadHomepageData() {
  initNewsImageFallback();
  await Promise.all([
    loadBanners(),
    loadStatistics(),
    loadGrowth(),
    loadProducts(),
    loadLatestNews()
  ]);
  reinitAnimations();
  reinitCounters();
  initHeroSlider();
  initProductTabs();
  createHeroParticles();
  initHeroParallax();
})();

async function loadBanners() {
  const data = await api.get('/api/homepage/banners');
  if (!data || data.length === 0) return;
  const slidesEl = document.getElementById('hero-slides');
  const dotsEl = document.getElementById('hero-indicators');
  slidesEl.innerHTML = data.map((b, i) => `
    <div class="hero-slide ${i === 0 ? 'active' : ''}">
      <div class="slide-bg" style="${b.bgStyle || 'background: linear-gradient(135deg, #1a3a2a 0%, #2d5a3f 50%, #1a4a32 100%);'}"></div>
      <div class="slide-overlay"></div>
      <div class="hero-content">
        <span class="tag">${b.tag}</span>
        <h1>${b.title}</h1>
        ${b.description ? '<p>' + b.description + '</p>' : ''}
        <div class="hero-btns">
          ${b.ctaText ? '<a href="' + (b.ctaLink || '#') + '" class="btn btn-primary">' + b.ctaText + '</a>' : ''}
          ${b.ctaText2 ? '<a href="' + (b.ctaLink2 || '#') + '" class="btn btn-outline">' + b.ctaText2 + '</a>' : ''}
        </div>
      </div>
    </div>
  `).join('');
  dotsEl.innerHTML = data.map((_, i) => '<span class="dot ' + (i === 0 ? 'active' : '') + '"></span>').join('');
}

async function loadStatistics() {
  const data = await api.get('/api/homepage/statistics');
  if (!data || data.length === 0) return;
  document.getElementById('numbers-grid').innerHTML = data.map((s, i) => `
    <div class="number-item fade-in" data-delay="${i * 100}">
      <div class="icon">${s.icon || ''}</div>
      <div class="num"><span class="counter" data-target="${s.value}" data-suffix="${s.unit || ''}">0</span></div>
      <div class="label">${s.label}</div>
    </div>
  `).join('');
}

async function loadGrowth() {
  const data = await api.get('/api/homepage/growth');
  if (!data || data.length === 0) return;
  document.getElementById('growth-items').innerHTML = data.map((g, i) => `
    ${i > 0 ? '<div class="growth-divider"></div>' : ''}
    <div class="growth-item fade-in" data-delay="${i * 100}">
      <span class="growth-label">${g.label}</span>
      <span class="growth-value">${g.value}${g.unit || ''}</span>
    </div>
  `).join('');
}

async function loadProducts() {
  const [exportData, importData, vehicleData] = await Promise.all([
    api.get('/api/products?category=EXPORT'),
    api.get('/api/products?category=IMPORT'),
    api.get('/api/vehicles/destinations')
  ]);

  function renderCards(items, containerId) {
    if (!items) return;
    document.getElementById(containerId).innerHTML = items.map((p, i) => `
      <div class="product-card fade-in" data-delay="${i * 100}">
        <div class="card-img">${fixEmoji(p.emoji) || ''}</div>
        <div class="card-body">
          <h4>${p.name}</h4>
          <p class="origin">${p.origin || ''}</p>
        </div>
      </div>
    `).join('');
  }

  renderCards(exportData, 'products-export');
  renderCards(importData, 'products-import');

  if (vehicleData && vehicleData.length > 0) {
    document.getElementById('products-vehicle').innerHTML = vehicleData.map((v, i) => `
      <div class="product-card fade-in" data-delay="${i * 100}">
        <div class="card-img" style="font-size:3.5rem;">${v.flag || '🚗'}</div>
        <div class="card-body">
          <h4>${v.country}</h4>
          <p class="origin">${v.description || ''}</p>
        </div>
      </div>
    `).join('');
  }
}

function renderHomepageNewsImages(items) {
  var cards = document.querySelectorAll('#news-grid .news-card');
  items.forEach(function(item, index) {
    if (!item || !item.image || !cards[index]) return;

    var media = cards[index].querySelector('.card-img');
    if (!media) return;

    var dateBadge = media.querySelector('.date-badge');
    media.textContent = '';
    media.insertAdjacentHTML('afterbegin', renderNewsMedia(item));
    if (dateBadge) media.appendChild(dateBadge);
  });
}

async function loadLatestNews() {
  const data = await api.get('/api/news/latest');
  if (!data || data.length === 0) return;
  document.getElementById('news-grid').innerHTML = data.map((n, i) => `
    <a href="news.html" class="news-card fade-in" data-delay="${i * 150}">
      <div class="card-img">
        ${n.emoji || '📰'}
        <span class="date-badge">${n.date || ''}</span>
      </div>
      <div class="card-body">
        <div class="category">${CATEGORY_MAP[n.category] || n.category || ''}</div>
        <h4>${n.title}</h4>
        <p>${n.excerpt || ''}</p>
        <span class="read-more">阅读更多</span>
      </div>
    </a>
  `).join('');
  renderHomepageNewsImages(data);
}

// ===== Antigravity 失重动画 =====

/**
 * 在 hero 区域生成漂浮粒子
 * 移动端减少数量
 */
function createHeroParticles() {
  const container = document.getElementById('hero-particles');
  if (!container) return;

  const isMobile = window.innerWidth <= 768;
  const count = isMobile ? 8 : 18;
  const animations = ['float-particle-1', 'float-particle-2', 'float-particle-3'];

  for (let i = 0; i < count; i++) {
    const p = document.createElement('div');
    p.className = 'particle';

    // 随机大小 4px ~ 20px
    const size = Math.random() * 16 + 4;
    p.style.width = size + 'px';
    p.style.height = size + 'px';

    // 随机位置
    p.style.left = Math.random() * 100 + '%';
    p.style.top = (Math.random() * 80 + 10) + '%';

    // 随机选一个动画 + 随机时长/延迟
    const anim = animations[Math.floor(Math.random() * animations.length)];
    const duration = Math.random() * 8 + 6;   // 6~14s
    const delay = Math.random() * 6;           // 0~6s 延迟

    p.style.animation = anim + ' ' + duration + 's ease-in-out ' + delay + 's infinite';

    // 部分粒子用白色光斑代替金色
    if (Math.random() > 0.6) {
      p.style.background = 'radial-gradient(circle, rgba(255,255,255,0.4) 0%, rgba(255,255,255,0) 70%)';
    }

    container.appendChild(p);
  }
}

/**
 * 鼠标视差跟踪：鼠标移动时 hero 内容轻微偏移
 * 移动端自动禁用
 */
function initHeroParallax() {
  if (window.innerWidth <= 768) return;

  const hero = document.querySelector('.hero');
  if (!hero) return;

  hero.addEventListener('mousemove', function(e) {
    const rect = hero.getBoundingClientRect();
    // 计算鼠标在 hero 区域中的归一化坐标 (-0.5 ~ 0.5)
    const x = (e.clientX - rect.left) / rect.width - 0.5;
    const y = (e.clientY - rect.top) / rect.height - 0.5;

    // 对所有可见 slide 中的 hero-content 应用偏移
    const contents = hero.querySelectorAll('.hero-slide.active .hero-content');
    contents.forEach(function(el) {
      el.style.transform = 'translate(' + (x * -20) + 'px, ' + (y * -15) + 'px)';
    });

    // 粒子层反方向轻微偏移增强层次感
    const particles = document.getElementById('hero-particles');
    if (particles) {
      particles.style.transform = 'translate(' + (x * 10) + 'px, ' + (y * 8) + 'px)';
    }
  });

  hero.addEventListener('mouseleave', function() {
    const contents = hero.querySelectorAll('.hero-content');
    contents.forEach(function(el) {
      el.style.transform = '';
    });
    const particles = document.getElementById('hero-particles');
    if (particles) {
      particles.style.transform = '';
    }
  });
}
