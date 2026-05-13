// ===== 全站三语切换公共框架 =====
// 每个页面只需定义 window.PAGE_TRANSLATIONS 和 window.applyPageLang(t, lang) 即可

(function() {
  'use strict';

  // 公共导航翻译
  const NAV_TRANSLATIONS = {
    zh: ['首页', '关于我们', '产品中心', '全球布局', '新闻中心', '联系我们'],
    en: ['Home', 'About', 'Products', 'Global', 'News', 'Contact'],
    ru: ['Главная', 'О нас', 'Продукция', 'География', 'Новости', 'Контакты']
  };

  // 公共 footer 翻译
  const FOOTER_TRANSLATIONS = {
    zh: {
      footerDesc: '杨凌现代农业国际合作集团有限公司全资子公司，专注于跨境农产品进出口贸易，依托杨凌自贸片区与上合组织农业基地，连接中国与世界。',
      footerNav: '快速导航',
      footerNavLinks: ['关于我们', '产品中心', '全球布局', '新闻中心', '联系我们'],
      footerProd: '核心产品',
      footerProdLinks: ['猕猴桃', '阳光玫瑰葡萄', '蓝莓', '苹果', '酥梨 / 冬枣'],
      footerContact: '联系方式',
      footerContactItems: ['陕西省杨凌示范区·杨凌综合保税区', 'info@ylguohe.com', '029-8703-9533', '周一至周五 9:00 - 18:00'],
      footerCopy: '© 2025 杨凌国合跨境贸易有限公司 版权所有',
      footerLinks: ['隐私政策', '使用条款']
    },
    en: {
      footerDesc: 'A wholly-owned subsidiary of Yangling Modern Agriculture International Cooperation Group, specializing in cross-border agricultural trade, connecting China with the world.',
      footerNav: 'Quick Links',
      footerNavLinks: ['About Us', 'Products', 'Global', 'News', 'Contact'],
      footerProd: 'Core Products',
      footerProdLinks: ['Kiwifruit', 'Shine Muscat Grape', 'Blueberry', 'Apple', 'Pear / Jujube'],
      footerContact: 'Contact Info',
      footerContactItems: ['Yangling Comprehensive Bonded Zone, Shaanxi, China', 'info@ylguohe.com', '029-8703-9533', 'Mon–Fri 9:00 – 18:00'],
      footerCopy: '© 2025 Yangling Guohe Cross-border Trade Co., Ltd. All rights reserved.',
      footerLinks: ['Privacy Policy', 'Terms of Use']
    },
    ru: {
      footerDesc: 'Дочерняя компания Yangling Modern Agriculture International Cooperation Group, специализирующаяся на трансграничной торговле сельхозпродукцией.',
      footerNav: 'Навигация',
      footerNavLinks: ['О нас', 'Продукция', 'География', 'Новости', 'Контакты'],
      footerProd: 'Основная продукция',
      footerProdLinks: ['Киви', 'Виноград Шайн Мускат', 'Голубика', 'Яблоки', 'Груша / Финик'],
      footerContact: 'Контакты',
      footerContactItems: ['Комплексная бондовая зона Янлин, Шэньси, Китай', 'info@ylguohe.com', '029-8703-9533', 'Пн–Пт 9:00 – 18:00'],
      footerCopy: '© 2025 Yangling Guohe Cross-border Trade Co., Ltd. Все права защищены.',
      footerLinks: ['Политика конфиденциальности', 'Условия использования']
    }
  };

  // 应用导航翻译
  function applyNav(lang) {
    var navLinks = document.querySelectorAll('.nav > a');
    var t = NAV_TRANSLATIONS[lang];
    if (!t) return;
    navLinks.forEach(function(a, i) {
      if (t[i]) a.textContent = t[i];
    });
  }

  // 应用 footer 翻译
  function applyFooter(lang) {
    var t = FOOTER_TRANSLATIONS[lang];
    if (!t) return;
    var footer = document.querySelector('.footer');
    if (!footer) return;

    var brandP = footer.querySelector('.footer-brand > p');
    if (brandP) brandP.textContent = t.footerDesc;

    var cols = footer.querySelectorAll('.footer-col');
    if (cols[0]) {
      var h4 = cols[0].querySelector('h4');
      if (h4) h4.textContent = t.footerNav;
      cols[0].querySelectorAll('a').forEach(function(a, i) {
        if (t.footerNavLinks[i]) a.textContent = t.footerNavLinks[i];
      });
    }
    if (cols[1]) {
      var h4 = cols[1].querySelector('h4');
      if (h4) h4.textContent = t.footerProd;
      cols[1].querySelectorAll('a').forEach(function(a, i) {
        if (t.footerProdLinks[i]) a.textContent = t.footerProdLinks[i];
      });
    }
    if (cols[2]) {
      var h4 = cols[2].querySelector('h4');
      if (h4) h4.textContent = t.footerContact;
      var items = cols[2].querySelectorAll('.footer-contact span:not(.icon)');
      items.forEach(function(span, i) {
        if (t.footerContactItems[i]) span.textContent = t.footerContactItems[i];
      });
    }

    var bottomSpans = footer.querySelectorAll('.footer-bottom > span');
    if (bottomSpans[0]) bottomSpans[0].innerHTML = t.footerCopy;
    if (bottomSpans[1]) {
      var links = bottomSpans[1].querySelectorAll('a');
      links.forEach(function(a, i) {
        if (t.footerLinks[i]) a.textContent = t.footerLinks[i];
      });
    }
  }

  // 核心切换函数
  function switchLang(lang) {
    // 设置 HTML lang 属性
    document.documentElement.lang = lang === 'zh' ? 'zh-CN' : lang === 'ru' ? 'ru' : 'en';

    // 公共部分：导航 + footer
    applyNav(lang);
    applyFooter(lang);

    // 页面专属翻译
    if (window.PAGE_TRANSLATIONS && window.applyPageLang) {
      var t = window.PAGE_TRANSLATIONS[lang];
      if (t) window.applyPageLang(t, lang);
    }
  }

  // 初始化语言按钮
  function initLangSwitcher() {
    document.querySelectorAll('.lang-btn').forEach(function(btn) {
      btn.addEventListener('click', function() {
        document.querySelectorAll('.lang-btn').forEach(function(b) {
          b.classList.remove('active');
        });
        this.classList.add('active');
        var lang = this.dataset.lang;
        switchLang(lang);
        localStorage.setItem('guohe-lang', lang);
      });
    });

    // 恢复上次选择的语言
    var saved = localStorage.getItem('guohe-lang');
    if (saved && saved !== 'zh') {
      var btn = document.querySelector('.lang-btn[data-lang="' + saved + '"]');
      if (btn) btn.click();
    }
  }

  // 等待 DOM 就绪
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initLangSwitcher);
  } else {
    initLangSwitcher();
  }

  // 暴露给页面使用
  window.i18nSwitchLang = switchLang;
})();
