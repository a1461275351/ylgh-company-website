/* ============================================
   杨凌国合跨境贸易有限公司 - 官方网站脚本
   Yangling Guohe Cross-border Trade Co., Ltd.
   ============================================ */

document.addEventListener('DOMContentLoaded', () => {
  initHeader();
  initHeroSlider();
  initScrollAnimations();
  initCounters();
  initProductTabs();
  initBackToTop();
  initMobileMenu();
});

/* ---------- Header Scroll Effect ---------- */
function initHeader() {
  const header = document.querySelector('.header');
  if (!header) return;

  function updateHeader() {
    if (window.scrollY > 60) {
      header.classList.add('scrolled');
      header.classList.remove('transparent');
    } else {
      header.classList.remove('scrolled');
      // Only add transparent on pages with hero
      if (document.querySelector('.hero')) {
        header.classList.add('transparent');
      }
    }
  }

  updateHeader();
  window.addEventListener('scroll', updateHeader, { passive: true });
}

/* ---------- Hero Banner Slider ---------- */
let _heroInterval;
function initHeroSlider() {
  if (_heroInterval) clearInterval(_heroInterval);
  const slides = document.querySelectorAll('.hero-slide');
  const dots = document.querySelectorAll('.hero-indicators .dot');
  if (slides.length === 0) return;

  let current = 0;
  let interval;

  function goToSlide(index) {
    slides[current].classList.remove('active');
    if (dots[current]) dots[current].classList.remove('active');

    current = index;
    if (current >= slides.length) current = 0;
    if (current < 0) current = slides.length - 1;

    slides[current].classList.add('active');
    if (dots[current]) dots[current].classList.add('active');
  }

  function startAutoPlay() {
    interval = setInterval(() => {
      goToSlide(current + 1);
    }, 6000);
    _heroInterval = interval;
  }

  function stopAutoPlay() {
    clearInterval(interval);
  }

  // Dot click
  dots.forEach((dot, i) => {
    dot.addEventListener('click', () => {
      stopAutoPlay();
      goToSlide(i);
      startAutoPlay();
    });
  });

  startAutoPlay();

  // Pause on hover
  const hero = document.querySelector('.hero');
  if (hero) {
    hero.addEventListener('mouseenter', stopAutoPlay);
    hero.addEventListener('mouseleave', startAutoPlay);
  }
}

/* ---------- Scroll Animations ---------- */
function initScrollAnimations() {
  const elements = document.querySelectorAll('.fade-in, .fade-in-left, .fade-in-right, .scale-in');
  if (elements.length === 0) return;

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        // Stagger children if data-stagger
        const delay = entry.target.dataset.delay || 0;
        setTimeout(() => {
          entry.target.classList.add('visible');
        }, delay);
        observer.unobserve(entry.target);
      }
    });
  }, {
    threshold: 0.15,
    rootMargin: '0px 0px -50px 0px'
  });

  elements.forEach(el => observer.observe(el));
}

/* ---------- Number Counter Animation ---------- */
function initCounters() {
  const counters = document.querySelectorAll('.counter');
  if (counters.length === 0) return;

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        animateCounter(entry.target);
        observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.5 });

  counters.forEach(counter => observer.observe(counter));
}

function animateCounter(el) {
  const target = parseFloat(el.dataset.target);
  const suffix = el.dataset.suffix || '';
  const isDecimal = String(el.dataset.target).includes('.');
  const decimals = isDecimal ? (String(el.dataset.target).split('.')[1] || '').length : 0;
  const duration = 2000;
  const startTime = performance.now();

  function update(currentTime) {
    const elapsed = currentTime - startTime;
    const progress = Math.min(elapsed / duration, 1);
    // Ease out cubic
    const eased = 1 - Math.pow(1 - progress, 3);
    const value = eased * target;

    if (isDecimal) {
      el.textContent = value.toFixed(decimals) + suffix;
    } else {
      el.textContent = Math.floor(value) + suffix;
    }

    if (progress < 1) {
      requestAnimationFrame(update);
    } else {
      if (isDecimal) {
        el.textContent = target.toFixed(decimals) + suffix;
      } else {
        el.textContent = target + suffix;
      }
    }
  }

  requestAnimationFrame(update);
}

/* ---------- Product Tabs ---------- */
let _productTabsInit = false;
function initProductTabs() {
  if (_productTabsInit) return;
  const tabs = document.querySelectorAll('.products-tabs .tab-btn');
  const grids = document.querySelectorAll('.products-tab-content');
  if (tabs.length === 0) return;

  _productTabsInit = true;
  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      const target = tab.dataset.tab;

      tabs.forEach(t => t.classList.remove('active'));
      tab.classList.add('active');

      grids.forEach(grid => {
        grid.style.display = grid.dataset.tab === target ? 'grid' : 'none';
      });
    });
  });
}

/* ---------- Back to Top ---------- */
function initBackToTop() {
  const btn = document.querySelector('.back-to-top');
  if (!btn) return;

  window.addEventListener('scroll', () => {
    if (window.scrollY > 500) {
      btn.classList.add('visible');
    } else {
      btn.classList.remove('visible');
    }
  }, { passive: true });

  btn.addEventListener('click', () => {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  });
}

/* ---------- Mobile Menu ---------- */
function initMobileMenu() {
  const toggle = document.querySelector('.menu-toggle');
  const nav = document.querySelector('.nav');
  const overlay = document.querySelector('.mobile-overlay');
  if (!toggle || !nav) return;

  function openMenu() {
    toggle.classList.add('active');
    nav.classList.add('open');
    if (overlay) overlay.classList.add('active');
    document.body.style.overflow = 'hidden';
  }

  function closeMenu() {
    toggle.classList.remove('active');
    nav.classList.remove('open');
    if (overlay) overlay.classList.remove('active');
    document.body.style.overflow = '';
  }

  toggle.addEventListener('click', () => {
    if (nav.classList.contains('open')) {
      closeMenu();
    } else {
      openMenu();
    }
  });

  if (overlay) {
    overlay.addEventListener('click', closeMenu);
  }

  // Close on nav link click
  nav.querySelectorAll('a').forEach(link => {
    link.addEventListener('click', closeMenu);
  });
}

/* ---------- Smooth Scroll for anchor links ---------- */
document.addEventListener('click', (e) => {
  const link = e.target.closest('a[href^="#"]');
  if (!link) return;

  const targetId = link.getAttribute('href');
  if (targetId === '#') return;

  const target = document.querySelector(targetId);
  if (target) {
    e.preventDefault();
    const offset = 80;
    const top = target.getBoundingClientRect().top + window.scrollY - offset;
    window.scrollTo({ top, behavior: 'smooth' });
  }
});
