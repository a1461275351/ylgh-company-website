// ===== Contact page data loading =====

// 加载联系信息
(async function() {
  await Promise.all([loadContactInfo(), loadOverseas()]);
  reinitAnimations();
})();

// 修正后端返回的占位电话号码
function fixPhone(p) { return p && p.indexOf('XXXX') !== -1 ? '029-8703-9533' : p; }

async function loadContactInfo() {
  const data = await api.get('/api/contact/info');
  if (!data || data.length === 0) return;
  const info = data[0];
  if (info.phone) info.phone = fixPhone(info.phone);
  const items = [];
  if (info.address) items.push({ icon: '📍', label: '公司地址', val: info.address });
  if (info.phone) items.push({ icon: '📞', label: '联系电话', val: '<a href="tel:' + info.phone + '">' + info.phone + '</a>' });
  if (info.email) items.push({ icon: '📧', label: '商务邮箱', val: '<a href="mailto:' + info.email + '">' + info.email + '</a>' });
  if (info.wechat) items.push({ icon: '💬', label: '微信商务号', val: info.wechat });
  if (info.whatsapp) items.push({ icon: '🌐', label: 'WhatsApp', val: info.whatsapp });

  document.getElementById('contact-info-list').innerHTML = items.map(it => `
    <div class="ci-item">
      <div class="ci-icon">${it.icon}</div>
      <div class="ci-text">
        <div class="ci-label">${it.label}</div>
        <div class="ci-val">${it.val}</div>
      </div>
    </div>
  `).join('');
}

async function loadOverseas() {
  const data = await api.get('/api/contact/overseas');
  if (!data || data.length === 0) return;
  document.getElementById('overseas-grid').innerHTML = data.map((o, i) => `
    <div class="ow-card fade-in" data-delay="${i * 150}">
      <div class="ow-flag">${o.country === '俄罗斯' ? '🇷🇺' : o.country === '白俄罗斯' ? '🇧🇾' : o.country === '阿联酋' ? '🇦🇪' : '🏳️'}</div>
      <h3>${o.country || ''}${o.city ? ' · ' + o.city : ''}</h3>
      <div class="ow-city">${(o.city || '').toUpperCase()} · ${(o.country || '').toUpperCase()}</div>
      <div class="ow-detail">
        ${o.address ? '<div class="ow-row"><span class="ow-icon">📍</span><span>' + o.address + '</span></div>' : ''}
        ${o.phone ? '<div class="ow-row"><span class="ow-icon">📞</span><span>' + o.phone + '</span></div>' : ''}
        ${o.email ? '<div class="ow-row"><span class="ow-icon">📧</span><span>' + o.email + '</span></div>' : ''}
        ${o.warehouseSize ? '<div class="ow-row"><span class="ow-icon">📦</span><span>' + o.warehouseSize + '</span></div>' : ''}
      </div>
    </div>
  `).join('');
}

// 表单提交 - 对接后端 API
const form = document.getElementById('contact-form');
const successEl = document.getElementById('form-success');

if (form) {
  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const agree = document.getElementById('agree');
    if (!agree.checked) {
      alert('请勾选同意隐私政策');
      return;
    }

    const inputs = form.querySelectorAll('input, select, textarea');
    const payload = {
      name: inputs[0].value,
      company: inputs[1].value,
      phone: inputs[2].value,
      email: inputs[3].value,
      country: inputs[4].value,
      inquiryType: inputs[5].value,
      message: inputs[6].value
    };

    const submitBtn = form.querySelector('.form-submit');
    submitBtn.textContent = '提交中...';
    submitBtn.disabled = true;

    const result = await api.post('/api/contact/inquiry', payload);

    if (result) {
      form.style.display = 'none';
      successEl.style.display = 'block';
      successEl.scrollIntoView({ behavior: 'smooth', block: 'center' });
    } else {
      alert('提交失败，请稍后重试');
      submitBtn.textContent = '提交询盘';
      submitBtn.disabled = false;
    }
  });
}
