// footer.js
function loadFooter() {
    fetch('/footer.html')  // header.html 경로
        .then(response => response.text())
        .then(data => {
            document.getElementById('footer-placeholder').innerHTML = data;
            attachMenuEventListeners(); // 메뉴 이벤트 리스너 추가
        })
        .catch(error => console.error('푸터 로드 실패:', error));
}

// 헤더에 있는 메뉴 이벤트 리스너 추가
function attachFooterEventListeners() {
    document.getElementById('menu-icon').addEventListener('click', function () {
        var menu = document.getElementById('menu-text-1');
        if (menu.style.display === 'block') {
            menu.style.display = 'none';
        } else {
            menu.style.display = 'block';
        }
    });
}

document.addEventListener('DOMContentLoaded', loadFooter);