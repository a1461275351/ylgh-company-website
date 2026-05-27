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

    // Image upload fields
    function getUploadField(el) {
        return el.closest('.image-upload-field');
    }

    function getTargetInput(el) {
        var selector = el.getAttribute('data-target');
        return selector ? document.querySelector(selector) : null;
    }

    function setUploadStatus(field, message, type) {
        var status = field ? field.querySelector('.js-upload-status') : null;
        if (!status) return;

        status.textContent = message || '';
        status.className = 'upload-status js-upload-status';
        if (type) {
            status.classList.add('is-' + type);
        }
    }

    function renderImagePreview(input) {
        var field = getUploadField(input);
        var preview = field ? field.querySelector('.js-image-preview') : null;
        if (!preview) return;

        var url = input.value.trim();
        preview.innerHTML = '';
        preview.classList.remove('has-image');

        if (!url) return;

        var image = document.createElement('img');
        image.src = url;
        image.alt = '图片预览';
        preview.appendChild(image);
        preview.classList.add('has-image');
    }

    var imagePathInputs = document.querySelectorAll('.js-image-path');
    imagePathInputs.forEach(function(input) {
        renderImagePreview(input);
        input.addEventListener('input', function() {
            renderImagePreview(input);
        });
    });

    var imageUploadInputs = document.querySelectorAll('.js-image-upload-input');
    imageUploadInputs.forEach(function(input) {
        input.addEventListener('change', function() {
            var field = getUploadField(input);
            var targetInput = getTargetInput(input);
            var file = input.files && input.files[0];

            if (!file || !field || !targetInput) return;

            if (!file.type || file.type.indexOf('image/') !== 0) {
                setUploadStatus(field, '请选择图片文件', 'error');
                input.value = '';
                return;
            }

            if (file.size > 10 * 1024 * 1024) {
                setUploadStatus(field, '图片不能超过 10MB', 'error');
                input.value = '';
                return;
            }

            var formData = new FormData();
            formData.append('file', file);

            input.disabled = true;
            setUploadStatus(field, '正在上传...', 'loading');

            fetch('/admin/api/files/upload', {
                method: 'POST',
                body: formData
            })
                .then(function(res) {
                    if (!res.ok) {
                        throw new Error('HTTP ' + res.status);
                    }
                    return res.json();
                })
                .then(function(data) {
                    if (!data || !data.url) {
                        throw new Error('上传结果缺少图片地址');
                    }

                    targetInput.value = data.url;
                    renderImagePreview(targetInput);
                    setUploadStatus(field, '上传成功', 'success');
                })
                .catch(function(err) {
                    console.error('Image upload failed:', err);
                    setUploadStatus(field, '上传失败，请稍后重试', 'error');
                })
                .finally(function() {
                    input.disabled = false;
                    input.value = '';
                });
        });
    });

    var clearImageButtons = document.querySelectorAll('.js-clear-image');
    clearImageButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            var field = getUploadField(button);
            var targetInput = getTargetInput(button);
            if (!targetInput) return;

            targetInput.value = '';
            renderImagePreview(targetInput);
            setUploadStatus(field, '', null);
        });
    });
});
