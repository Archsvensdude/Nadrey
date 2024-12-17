document.addEventListener('DOMContentLoaded', function() {
    // header.html 로드
    fetch('header.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('header-placeholder').innerHTML = data;
            attachMenuEventListeners();  // 헤더 로드 후 이벤트 리스너 추가
        })
        .catch(error => console.error('헤더 로드 실패:', error));

    // main_logo 표시/숨김
    function checkWindowSize() {
        var windowWidth = window.innerWidth;
        var mainLogo = document.getElementById('main_logo');
        if (mainLogo) {
            mainLogo.style.display = windowWidth <= 180 ? 'none' : 'block';
        }
    }

    // menu-text-1 표시/숨김
    function checkMenuSize() {
        var windowWidth = window.innerWidth;
        var menuText = document.getElementById('menu-text-1');
        if (menuText) {
            menuText.style.display = windowWidth <= 978 ? 'none' : 'block';
        }
    }

    // 화면 크기 변경 시 이벤트 리스너 등록
    window.addEventListener('resize', checkWindowSize);
    window.addEventListener('resize', checkMenuSize);

    // 페이지 로드 시 초기 체크
    checkWindowSize();
    checkMenuSize();

    // 메뉴 아이콘 클릭 이벤트 추가
    const menuIcon = document.getElementById('menu-icon');
    const menuText = document.getElementById('menu-text-1');
    if (menuIcon && menuText) {
        menuIcon.addEventListener('click', function () {
            menuText.style.display = (menuText.style.display === 'block') ? 'none' : 'block';
        });
    }

    // 로그인 여부 확인 후 리다이렉트 처리
    function redirectToLoginIfNotLoggedIn(targetUrl) {
        checkLoginStatus().then(isLoggedIn => {
            if (!isLoggedIn) {
                window.location.href = '/login';
            } else {
                window.location.href = targetUrl;
            }
        }).catch(error => {
            console.error('로그인 상태 확인 실패:', error);
        });
    }

    // 로그인 필요 링크 클릭 시 리다이렉트 설정
    var loginRequiredLinks = document.querySelectorAll('a.requires-login');
    loginRequiredLinks.forEach(function(link) {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            var targetUrl = link.getAttribute('href');
            redirectToLoginIfNotLoggedIn(targetUrl);
        });
    });
});
