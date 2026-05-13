// ===== About page API data loading =====
(async function() {
  await Promise.all([loadTimeline(), loadQualifications()]);
  reinitAnimations();
})();

async function loadTimeline() {
  const data = await api.get('/api/company/timeline');
  if (!data || data.length === 0) return;
  const el = document.getElementById('timeline-container');
  el.innerHTML = data.map((item, i) => {
    const isOdd = i % 2 === 0;
    const dotStyle = item.highlight
      ? ' style="background:var(--accent);box-shadow:0 0 0 3px var(--accent);"' : '';
    const yearStyle = item.highlight
      ? ' style="color:var(--primary);font-size:0.85rem;"' : '';
    return `
    <div class="timeline-item fade-in">
      ${isOdd ? `
        <div class="t-content">
          <h4>${item.title}</h4>
          ${item.description ? '<p>' + item.description + '</p>' : ''}
        </div>
        <div class="t-dot">
          <div class="t-dot-circle"${dotStyle}></div>
          <div class="t-year"${yearStyle}>${item.year}</div>
        </div>
        <div class="t-empty"></div>
      ` : `
        <div class="t-empty"></div>
        <div class="t-dot">
          <div class="t-dot-circle"${dotStyle}></div>
          <div class="t-year"${yearStyle}>${item.year}</div>
        </div>
        <div class="t-content">
          <h4>${item.title}</h4>
          ${item.description ? '<p>' + item.description + '</p>' : ''}
        </div>
      `}
    </div>`;
  }).join('');
}

async function loadQualifications() {
  const data = await api.get('/api/company/qualifications');
  if (!data || data.length === 0) return;
  document.getElementById('qual-grid').innerHTML = data.map((q, i) => `
    <div class="qual-card fade-in" data-delay="${i * 100}">
      <div class="q-icon">${q.icon || '📋'}</div>
      <h4>${q.title}</h4>
      ${q.description ? '<p>' + q.description + '</p>' : ''}
    </div>
  `).join('');
}
