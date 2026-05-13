// ===== Products page data loading =====

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

// Products page category switching
document.querySelectorAll('.cat-btn').forEach(btn => {
  btn.addEventListener('click', () => {
    document.querySelectorAll('.cat-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    const cat = btn.dataset.cat;
    document.querySelectorAll('.cat-content').forEach(c => c.classList.remove('active'));
    document.getElementById('cat-' + cat).classList.add('active');
  });
});

// 加载产品数据
(async function() {
  await Promise.all([
    loadProductsByCategory('EXPORT', 'export-grid'),
    loadProductsByCategory('IMPORT', 'import-grid'),
    loadVehicleDestinations()
  ]);
  reinitAnimations();
})();

async function loadProductsByCategory(category, containerId) {
  const data = await api.get('/api/products?category=' + category);
  if (!data || data.length === 0) return;
  document.getElementById(containerId).innerHTML = data.map((p, i) => `
    <div class="detail-card fade-in" data-delay="${i * 100}">
      <div class="d-img">
        ${fixEmoji(p.emoji) || ''}
        ${p.badge ? '<span class="d-tag">' + p.badge + '</span>' : ''}
      </div>
      <div class="d-body">
        <h3>${p.name}</h3>
        ${p.origin ? '<div class="d-origin">' + p.origin + '</div>' : ''}
        ${p.description ? '<p>' + p.description + '</p>' : ''}
        ${p.tags ? '<div class="d-specs">' + p.tags.split(',').map(t => '<span class="d-spec-tag">' + t.trim() + '</span>').join('') + '</div>' : ''}
      </div>
    </div>
  `).join('');
}

async function loadVehicleDestinations() {
  const data = await api.get('/api/vehicles/destinations');
  if (!data || data.length === 0) return;
  document.getElementById('dest-grid').innerHTML = data.map(v => `
    <div class="dest-card">
      <div class="flag">${v.flag || '🏳️'}</div>
      <h4>${v.country}</h4>
      <p>${v.description || ''}</p>
    </div>
  `).join('');
}
