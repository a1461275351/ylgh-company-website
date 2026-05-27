// ===== News page data loading =====
const CATEGORY_MAP = { EXPORT: '出口动态', IMPORT: '进口业务', VEHICLE: '二手车出口', COMPANY: '公司动态', EVENT: '展会活动' };
const CATEGORY_CSS = { EXPORT: 'cat-export', IMPORT: 'cat-import', VEHICLE: 'cat-vehicle', COMPANY: 'cat-company', EVENT: 'cat-event' };
let allNews = [];
let featuredNews = null;
let newsDetailInited = false;
let newsImageFallbackInited = false;

(async function() {
  await Promise.all([loadFeatured(), loadNewsList()]);
  initNewsDetail();
  initNewsImageFallback();
  initFilter();
  reinitAnimations();
})();

async function loadFeatured() {
  const data = await api.get('/api/news/featured');
  if (!data) return;
  featuredNews = data;
  document.getElementById('featured-article').innerHTML = `
    <div class="featured-img">
      ${renderNewsMedia(data)}
      <span class="feat-date">${data.date || ''}</span>
      <span class="feat-badge">置顶要闻</span>
    </div>
    <div class="featured-body">
      <div class="f-cat">${escapeHtml(CATEGORY_MAP[data.category] || '')}</div>
      <h2>${escapeHtml(data.title)}</h2>
      ${formatContent(data.excerpt || data.content || '')}
      <button type="button" class="btn btn-green news-detail-trigger" data-news-id="${data.id}">阅读全文</button>
    </div>
  `;
}

async function loadNewsList(category) {
  const url = category ? '/api/news?category=' + category : '/api/news';
  const data = await api.get(url);
  if (!data) return;
  allNews = sortNews(data);
  renderNews(allNews);
}

function renderNews(items) {
  document.getElementById('news-list-grid').innerHTML = items.map((n, i) => `
    <div class="news-item fade-in" data-delay="${(i % 3) * 100}" data-category="${(n.category || '').toLowerCase()}">
      <div class="ni-img">
        ${renderNewsMedia(n)}
        <span class="ni-date">${n.date || ''}</span>
      </div>
      <div class="ni-body">
        <div class="ni-cat ${CATEGORY_CSS[n.category] || ''}">${escapeHtml(CATEGORY_MAP[n.category] || n.category || '')}</div>
        <h4>${escapeHtml(n.title)}</h4>
        <p>${escapeHtml(n.excerpt || '')}</p>
        <button type="button" class="ni-read news-detail-trigger" data-news-id="${n.id}">阅读更多</button>
      </div>
    </div>
  `).join('');
  reinitAnimations();
}

function initFilter() {
  document.querySelectorAll('.filter-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      const filter = btn.dataset.filter;
      if (filter === 'all') {
        renderNews(allNews);
      } else {
        renderNews(allNews.filter(n => (n.category || '').toLowerCase() === filter));
      }
    });
  });
}

function initNewsDetail() {
  if (newsDetailInited) return;
  newsDetailInited = true;

  ensureNewsDetailModal();

  document.addEventListener('click', async function(e) {
    const trigger = e.target.closest('.news-detail-trigger');
    if (trigger) {
      e.preventDefault();
      await openNewsDetail(trigger.dataset.newsId);
      return;
    }

    if (e.target.closest('[data-news-modal-close]')) {
      closeNewsDetail();
    }
  });

  document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') closeNewsDetail();
  });
}

function ensureNewsDetailModal() {
  if (document.getElementById('news-detail-modal')) return;

  const modal = document.createElement('div');
  modal.id = 'news-detail-modal';
  modal.className = 'news-detail-modal';
  modal.hidden = true;
  modal.innerHTML = `
    <div class="news-detail-backdrop" data-news-modal-close></div>
    <article class="news-detail-panel" role="dialog" aria-modal="true" aria-labelledby="news-detail-title">
      <button type="button" class="news-detail-close" data-news-modal-close aria-label="关闭">×</button>
      <div class="news-detail-hero" id="news-detail-hero"></div>
      <div class="news-detail-body">
        <div class="news-detail-meta">
          <span id="news-detail-category"></span>
          <span id="news-detail-date"></span>
        </div>
        <h2 id="news-detail-title"></h2>
        <div class="news-detail-content" id="news-detail-content"></div>
      </div>
    </article>
  `;
  document.body.appendChild(modal);
}

async function openNewsDetail(id) {
  let article = findCachedNews(id);
  if (!article && id) {
    article = await api.get('/api/news/' + encodeURIComponent(id));
  }
  if (!article) return;

  const modal = document.getElementById('news-detail-modal');
  document.getElementById('news-detail-hero').innerHTML = renderNewsMedia(article);
  document.getElementById('news-detail-category').textContent = CATEGORY_MAP[article.category] || article.category || '';
  document.getElementById('news-detail-date').textContent = article.date || '';
  document.getElementById('news-detail-title').textContent = article.title || '';
  document.getElementById('news-detail-content').innerHTML = formatContent(article.content || article.excerpt || '暂无详情内容。');

  modal.hidden = false;
  document.body.classList.add('news-modal-open');
}

function closeNewsDetail() {
  const modal = document.getElementById('news-detail-modal');
  if (!modal || modal.hidden) return;

  modal.hidden = true;
  document.body.classList.remove('news-modal-open');
}

function findCachedNews(id) {
  const idText = String(id || '');
  return [featuredNews].concat(allNews).find(function(item) {
    return item && String(item.id) === idText;
  });
}

function renderNewsMedia(item) {
  if (item && item.image) {
    return '<img class="news-media-img" src="' + escapeAttr(resolveImageUrl(item.image)) + '" alt="' + escapeAttr(item.title || '新闻图片') + '" loading="lazy">';
  }
  return '<span class="news-emoji">' + escapeHtml((item && item.emoji) || '📰') + '</span>';
}

function initNewsImageFallback() {
  if (newsImageFallbackInited) return;
  newsImageFallbackInited = true;

  document.addEventListener('error', function(e) {
    const image = e.target;
    if (!image || !image.classList || !image.classList.contains('news-media-img')) return;

    const fallback = document.createElement('span');
    fallback.className = 'news-emoji';
    fallback.textContent = '📰';
    image.replaceWith(fallback);
  }, true);
}

function resolveImageUrl(url) {
  const raw = String(url || '').trim();
  if (!raw) return '';
  if (/^(https?:|data:|blob:)/i.test(raw)) return raw;

  const path = raw.charAt(0) === '/' ? raw : '/' + raw;
  const isLocalFrontend = ['localhost', '127.0.0.1'].includes(window.location.hostname) && window.location.port === '8080';
  if (isLocalFrontend && path.indexOf('/uploads/') === 0) {
    return 'http://localhost:8081' + path;
  }

  return path;
}

function sortNews(items) {
  return (items || []).slice().sort(function(a, b) {
    const diff = getNewsSortValue(b) - getNewsSortValue(a);
    if (diff !== 0) return diff;
    return Number(b.id || 0) - Number(a.id || 0);
  });
}

function getNewsSortValue(item) {
  return parseDateText(item && item.date)
    || parseBackendTime(item && item.createdAt)
    || parseBackendTime(item && item.updatedAt)
    || Number(item && item.id) || 0;
}

function parseDateText(value) {
  const text = String(value || '');
  const match = text.match(/(\d{4})(?:\D+(\d{1,2}))?(?:\D+(\d{1,2}))?/);
  if (!match) return 0;

  const year = Number(match[1]);
  const month = Number(match[2] || 1);
  const day = Number(match[3] || 1);
  if (!year || month < 1 || month > 12 || day < 1 || day > 31) return 0;

  return Date.UTC(year, month - 1, day);
}

function parseBackendTime(value) {
  const time = Date.parse(value || '');
  return Number.isNaN(time) ? 0 : time;
}

function formatContent(text) {
  const paragraphs = String(text || '')
    .split(/\n+/)
    .map(function(p) { return p.trim(); })
    .filter(Boolean);

  if (paragraphs.length === 0) return '';
  return paragraphs.map(function(p) {
    return '<p>' + escapeHtml(p) + '</p>';
  }).join('');
}

function escapeHtml(value) {
  return String(value == null ? '' : value)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#39;');
}

function escapeAttr(value) {
  return escapeHtml(value).replace(/`/g, '&#96;');
}
