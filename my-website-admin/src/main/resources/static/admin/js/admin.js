// ========== Admin JS - 国合后台管理系统 ==========

document.addEventListener('DOMContentLoaded', function() {
    // Auto-hide alerts after 3 seconds
    var alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            alert.style.opacity = '0';
            alert.style.transition = 'opacity 0.3s';
            setTimeout(function() { alert.remove(); }, 300);
        }, 3000);
    });

    // Confirm delete actions
    var deleteForms = document.querySelectorAll('form[data-confirm]');
    deleteForms.forEach(function(form) {
        form.addEventListener('submit', function(e) {
            var msg = form.getAttribute('data-confirm') || '确认删除此项？此操作不可撤销。';
            if (!confirm(msg)) {
                e.preventDefault();
            }
        });
    });

    // Active sidebar link highlighting
    var currentPath = window.location.pathname;
    var navItems = document.querySelectorAll('.nav-item');
    navItems.forEach(function(item) {
        if (item.getAttribute('href') === currentPath) {
            item.classList.add('active');
        }
    });
});
