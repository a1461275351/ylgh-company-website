// ===== News page data loading =====
const CATEGORY_MAP = { EXPORT: '出口动态', IMPORT: '进口业务', VEHICLE: '二手车出口', COMPANY: '公司动态', EVENT: '展会活动' };
const CATEGORY_CSS = { EXPORT: 'cat-export', IMPORT: 'cat-import', VEHICLE: 'cat-vehicle', COMPANY: 'cat-company', EVENT: 'cat-event' };
let allNews = [];

(async function() {
  await Promise.all([loadFeatured(), loadNewsList()]);
  initFilter();
  reinitAnimations();
})();

async function loadFeatured() {
  const data = await api.get('/api/news/featured');
  if (!data) return;
  document.getElementById('featured-article').innerHTML = `
    <div class="featured-img">
      ${data.emoji || '📰'}
      <span class="feat-date">${data.date || ''}</span>
      <span class="feat-badge">置顶要闻</span>
    </div>
    <div class="featured-body">
      <div class="f-cat">${CATEGORY_MAP[data.category] || ''}</div>
      <h2>${data.title}</h2>
      ${data.content ? data.content.split('\n').map(p => '<p>' + p + '</p>').join('') : (data.excerpt ? '<p>' + data.excerpt + '</p>' : '')}
      <a href="#" class="btn btn-green">阅读全文</a>
    </div>
  `;
}

async function loadNewsList(category) {
  const url = category ? '/api/news?category=' + category : '/api/news';
  const data = await api.get(url);
  if (!data) return;
  allNews = data;
  renderNews(data);
}

function renderNews(items) {
  document.getElementById('news-list-grid').innerHTML = items.map((n, i) => `
    <div class="news-item fade-in" data-delay="${(i % 3) * 100}" data-category="${(n.category || '').toLowerCase()}">
      <div class="ni-img">
        ${n.emoji || '📰'}
        <span class="ni-date">${n.date || ''}</span>
      </div>
      <div class="ni-body">
        <div class="ni-cat ${CATEGORY_CSS[n.category] || ''}">${CATEGORY_MAP[n.category] || n.category || ''}</div>
        <h4>${n.title}</h4>
        <p>${n.excerpt || ''}</p>
        <span class="ni-read">阅读更多</span>
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
