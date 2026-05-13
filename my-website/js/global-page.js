// ===== Global page data loading =====

// Logistics tab switching
document.querySelectorAll('.log-tab').forEach(tab => {
  tab.addEventListener('click', () => {
    document.querySelectorAll('.log-tab').forEach(t => t.classList.remove('active'));
    tab.classList.add('active');
    const log = tab.dataset.log;
    document.querySelectorAll('.log-content').forEach(c => c.classList.remove('active'));
    document.getElementById('log-' + log).classList.add('active');
  });
});

// 加载物流和仓库数据
(async function() {
  await Promise.all([
    loadRoutes('RAIL'),
    loadRoutes('TIR'),
    loadRoutes('AIR'),
    loadRoutes('SEA'),
    loadWarehouses()
  ]);
  reinitAnimations();
})();

async function loadRoutes(type) {
  const data = await api.get('/api/global/routes?type=' + type);
  if (!data || data.length === 0) return;
  const el = document.getElementById('log-' + type.toLowerCase());

  if (type === 'RAIL') {
    el.innerHTML = data.map((r, i) => `
      <div class="rail-card fade-in" data-delay="${i * 100}">
        <div class="r-icon">${r.icon || '🚂'}</div>
        <h4>${r.name}</h4>
        ${r.cities ? '<div class="r-route">' + r.cities + '</div>' : ''}
        ${r.description ? '<p>' + r.description + '</p>' : ''}
      </div>
    `).join('');
  } else if (type === 'TIR') {
    const introItems = data.filter(r => r.description && r.description.length > 80);
    const routeItems = data.filter(r => !r.description || r.description.length <= 80);
    el.innerHTML = `
      <div class="tir-intro fade-in-left">
        <h3>TIR 国际公路运输</h3>
        ${introItems.map(r => '<p>' + r.description + '</p>').join('')}
        ${introItems.length === 0 ? data.map(r => r.description ? '<p>' + r.description + '</p>' : '').join('') : ''}
      </div>
      <div class="tir-routes fade-in-right">
        <h4 style="color:#fff;">主要运输路线</h4>
        ${(routeItems.length > 0 ? routeItems : data).map(r => `
          <div class="tir-route-item">
            <div class="route-name">${r.name}</div>
            ${r.cities ? '<div class="route-desc">' + r.cities + (r.transitTime ? '，约' + r.transitTime : '') + '</div>' : ''}
          </div>
        `).join('')}
      </div>
    `;
  } else if (type === 'AIR') {
    el.innerHTML = `
      <div class="air-intro fade-in-left">
        <h3>航空冷链运输</h3>
        ${data.filter(r => r.description).map(r => '<p>' + r.description + '</p>').join('')}
      </div>
      <div class="air-features fade-in-right">
        ${data.map(r => `
          <div class="air-feature">
            <div class="af-icon">${r.icon || '✈️'}</div>
            <div class="af-text">
              <div class="af-title">${r.name}</div>
              ${r.cities ? '<div class="af-desc">' + r.cities + '</div>' : ''}
            </div>
          </div>
        `).join('')}
      </div>
    `;
  } else if (type === 'SEA') {
    el.innerHTML = data.map((r, i) => `
      <div class="sea-card fade-in" data-delay="${i * 100}">
        <div class="s-icon">${r.icon || '🚢'}</div>
        <h4>${r.name}</h4>
        ${r.description ? '<p>' + r.description + '</p>' : ''}
      </div>
    `).join('');
  }
}

async function loadWarehouses() {
  const data = await api.get('/api/global/warehouses');
  if (!data || data.length === 0) return;
  document.getElementById('wh-grid').innerHTML = data.map((w, i) => `
    <div class="wh-card fade-in" data-delay="${i * 150}">
      <div class="wh-img">
        🏭
        ${w.flag ? '<span class="wh-flag">' + w.flag + '</span>' : ''}
      </div>
      <div class="wh-body">
        <h3>${w.city}${w.country ? ' · ' + w.country : ''}</h3>
        <span class="wh-size">${w.size}</span>
        ${w.description ? '<p>' + w.description + '</p>' : ''}
        ${w.features ? '<div class="wh-features">' + w.features.split(',').map(f => '<span class="wh-feature-tag">' + f.trim() + '</span>').join('') + '</div>' : ''}
      </div>
    </div>
  `).join('');
}
