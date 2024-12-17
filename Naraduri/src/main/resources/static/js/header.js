// DOMContentLoaded 이벤트에서 초기화
document.addEventListener("DOMContentLoaded", function () {
    initializeHeader(); // 헤더 초기화
    handleResize(); // 반응형 처리
    window.addEventListener("resize", handleResize); // 창 크기 변경 이벤트 등록
    attachToMyPage();

    waitForElement("#loggedInUserName", UpdatedIdAndName);
    waitForElement("#loggedInId", UpdatedId);
    waitForElement("#loggedInName", UpdatedName);
    waitForElement("#loggedInIdShort", UpdatedIdShort);
    waitForElement("#loggedInNameShort", UpdatedNameShort);
});

// 헤더 초기화
function initializeHeader() {
    const headerPlaceholder = document.getElementById("header-placeholder");
    if (!headerPlaceholder) {
        console.log("헤더가 이 페이지에 포함되지 않았습니다.");
        return;
    }

    loadHeader(() => {
        attachMenuEventListeners();
        updateLoginState();
        waitForElement("#loggedInUserName", UpdatedIdAndName); // 요소 로드 후 호출
    });

    handleResize(); // 반응형 처리
    window.addEventListener("resize", handleResize);
}

// 헤더 동적 로드
function loadHeader(callback) {
    return fetch("/header.html")
        .then((response) => {
            if (!response.ok) throw new Error(`Failed to load header: ${response.status}`);
            return response.text();
        })
        .then((data) => {
            document.getElementById("header-placeholder").innerHTML = data;
            if (callback) callback();
        })
        .catch((error) => console.error("헤더 로드 실패:", error));
}

// 메뉴 이벤트 리스너 추가
function attachMenuEventListeners() {
    const menuIcon = document.getElementById("menuIcon");
    const sidebar = document.getElementById("sidebar");

    if (menuIcon && sidebar) {
        menuIcon.addEventListener("click", () => {
            sidebar.classList.toggle("active");
            console.log(sidebar.classList.contains("active") ? "사이드바가 열렸습니다." : "사이드바가 닫혔습니다.");
        });
    }
}

// 반응형 처리
function handleResize() {
    const loginBanner = document.getElementById("loginBanner");
    if (loginBanner) {
        loginBanner.style.display = window.innerWidth <= 978.9 ? "none" : "block";
    }
}

// 로그인 상태 업데이트
function updateLoginState() {
    fetch("/login/status")
        .then((response) => response.json())
        .then((data) => {
            const isLoggedIn = data.isLoggedIn;
            const username = data.username || null;

            if (isLoggedIn && username) {
                updateMenus(true, username); // 로그인 상태 업데이트
                updateLoginBanner(username, data.name); // 로그인 배너 업데이트
            } else {
                updateMenus(false); // 로그아웃 상태 업데이트
                updateLoginBanner(null, null); // 로그인 배너 숨김
            }
        })
        .catch((error) => console.error("로그인 상태 확인 실패:", error));
}

// 로그인 배너 업데이트
function updateLoginBanner(username, name) {
    const loginBanner = document.getElementById("loginBanner");
    const myPageLink = document.getElementById("myPageLink");
    const welcomeMessage = document.getElementById("welcomeMessage");

    if (loginBanner) {
        if (username && name) {
            loginBanner.style.display = "block";
            myPageLink.href = `/myPage/${username}`;
            welcomeMessage.textContent = `${name}님 환영합니다!`;
        } else {
            loginBanner.style.display = "none";
        }
    }
}

// 메뉴 업데이트
function updateMenus(isLoggedIn, username = "") {
    const loginItem = document.getElementById("login-item");
    const myPageAndLogoutItem = document.getElementById("myPageAndLogout-item");
    const loginSideItem = document.getElementById("login-side-item");
    const myPageSideItem = document.getElementById("myPage-side-item");
    const logoutSideItem = document.getElementById("logout-side-item");
    const borderingIdAndNameItem = document.getElementById("vertical");

    // DOM 요소가 존재하는지 확인
    if (!loginItem || !myPageAndLogoutItem) {
        console.error("로그인/로그아웃 메뉴 DOM 요소를 찾을 수 없습니다.");
        return;
    }
    if (!loginSideItem || !myPageSideItem || !logoutSideItem) {
        console.error("로그인/로그아웃 메뉴 DOM 요소를 찾을 수 없습니다.");
        return;
    }

    if (isLoggedIn) {
        // 로그인 상태: [로그아웃] 버튼 표시, [로그인] 버튼 숨김
        loginItem.style.display = "none";
        loginSideItem.style.display = "none";
        myPageAndLogoutItem.style.display = "block";
        myPageSideItem.style.display = "block";
        logoutSideItem.style.display = "block";
    } else {
        // 로그아웃 상태: [로그인] 버튼 표시, [로그아웃] 버튼 숨김
        loginItem.style.display = "block";
        loginSideItem.style.display = "block";
        myPageAndLogoutItem.style.display = "none";
        myPageSideItem.style.display = "none";
        logoutSideItem.style.display = "none";
        borderingIdAndNameItem.style.display = "none";
    }
}

// 로그인 버튼 이벤트 연결
function attachLoginButtonEvent() {
    const loginButton = document.getElementById("loginButton");
    if (loginButton) {
        loginButton.addEventListener("click", login);
        console.log("로그인 버튼 이벤트 연결 완료");
    } else {
        console.log("로그인 버튼이 이 페이지에 포함되지 않았습니다.");
    }
}

// 로그인
function login() {
    const usernameInput = document.getElementById("username")?.value;
    const passwordInput = document.getElementById("password")?.value;

    if (!usernameInput || !passwordInput) {
        alert("아이디와 비밀번호를 입력해 주세요.");
        return;
    }

    fetch("/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username: usernameInput, password: passwordInput }),
    })
        .then((response) => response.json())
        .then((data) => {
            if (data.success) {
                console.log("로그인 성공:", data);

                // 로컬 스토리지에 로그인 상태 저장
                localStorage.setItem("isLoggedIn", "true");
                localStorage.setItem("username", data.username);
                localStorage.setItem("name", data.name);

                // 로그인 성공 시 즉시 메뉴 및 배너 업데이트
                updateMenus(true, data.username);
                updateLoginBanner(data.username, data.name);

                // 페이지 리다이렉트
                window.location.href = `/myPage/${data.username}`;
            } else {
                alert(`로그인 실패: ${data.message}`);
            }
        })
        .catch((error) => console.error("로그인 중 오류 발생:", error));
}

// 로그아웃
function logout() {
    fetch("/login/api/logout", { method: "POST" })
        .then(() => {
            // 로컬 스토리지 초기화
            localStorage.clear();

            // 로그아웃 시 메뉴 및 배너 즉시 업데이트
            updateMenus(false);
            updateLoginBanner(null, null);

            window.location.href = "/";
        })
        .catch((error) => console.error("로그아웃 실패:", error));
}

function attachToMyPage() {
    fetch("/login/status", {
        method: "GET",
        credentials: "include" // 세션 쿠키 포함
    })
        .then(response => response.json())
        .then(data => {
            if (data.isLoggedIn) {
                const username = data.username;
                const myPageLink = document.getElementById("sidebar_menu-text-2-2");

                if (myPageLink) {
                    // 동적으로 사용자 ID 기반 링크 설정
                    myPageLink.href = `/myPage/${username}`;
                    document.getElementById("myPage-side-item").style.display = "block"; // 버튼 표시
                }
            } else {
                console.log("로그인되지 않음.");
            }
        })
        .catch(error => console.error("로그인 상태 확인 중 오류 발생:", error));
}

function UpdatedIdAndName() {
    const userNameElement = document.getElementById("loggedInUserName");

    if (!userNameElement) {
        console.error("'loggedInUserName' 요소가 HTML에 존재하지 않습니다.");
        return;
    }

    fetch("/login/session")
        .then(response => {
            if (!response.ok) throw new Error("Session fetch failed");
            return response.json();
        })
        .then(data => {
            if (data.username && data.name) {
                userNameElement.textContent = `${data.name}님 (${data.username})`;
            } else {
                userNameElement.textContent = "알 수 없는 사용자";
            }
        })
        .catch(error => {
            console.error("로그인 세션 로드 실패:", error);
            userNameElement.textContent = "세션 로드 실패";
        });
}


function UpdatedId() {
    fetch("/login/session")
        .then(response => {
            if (!response.ok) throw new Error("Session fetch failed");
            return response.json();
        })
        .then(data => {
            const userNameElement = document.getElementById("loggedInId");
            if (!userNameElement) {
                console.error("'loggedInId' 요소를 찾을 수 없습니다.");
                return;
            }

            if (data.username && data.name) {
                userNameElement.textContent = `${data.username}`;
            } else {
                userNameElement.textContent = "";
            }
        })
        .catch(error => {
            console.error("로그인 세션 로드 실패:", error);
            const userNameElement = document.getElementById("loggedInId");
            if (userNameElement) {
                userNameElement.textContent = "";
            }
        });
}

function UpdatedName() {
    fetch("/login/session")
        .then(response => {
            if (!response.ok) throw new Error("Session fetch failed");
            return response.json();
        })
        .then(data => {
            const userNameElement = document.getElementById("loggedInName");
            if (!userNameElement) {
                console.error("'loggedInName' 요소를 찾을 수 없습니다.");
                return;
            }

            if (data.username && data.name) {
                userNameElement.textContent = `${data.name}`;
            } else {
                userNameElement.textContent = "";
            }
        })
        .catch(error => {
            console.error("로그인 세션 로드 실패:", error);
            const userNameElement = document.getElementById("loggedInName");
            if (userNameElement) {
                userNameElement.textContent = "";
            }
        });
}

function UpdatedIdShort() {
    fetch("/login/session")
        .then(response => {
            if (!response.ok) throw new Error("Session fetch failed");
            return response.json();
        })
        .then(data => {
            const userNameElement = document.getElementById("loggedInIdShort");
            if (!userNameElement) {
                console.error("'loggedInIdShort' 요소를 찾을 수 없습니다.");
                return;
            }

            if (data.username && data.name) {
                userNameElement.textContent = `${data.username}`;
            } else {
                userNameElement.textContent = "";
            }
        })
        .catch(error => {
            console.error("로그인 세션 로드 실패:", error);
            const userNameElement = document.getElementById("loggedInIdShort");
            if (userNameElement) {
                userNameElement.textContent = "";
            }
        });
}

function UpdatedNameShort() {
    fetch("/login/session")
        .then(response => {
            if (!response.ok) throw new Error("Session fetch failed");
            return response.json();
        })
        .then(data => {
            const userNameElement = document.getElementById("loggedInNameShort");
            if (!userNameElement) {
                console.error("'loggedInNameShort' 요소를 찾을 수 없습니다.");
                return;
            }

            if (data.username && data.name) {
                userNameElement.textContent = `${data.name}`;
            } else {
                userNameElement.textContent = "";
            }
        })
        .catch(error => {
            console.error("로그인 세션 로드 실패:", error);
            const userNameElement = document.getElementById("loggedInNameShort");
            if (userNameElement) {
                userNameElement.textContent = "";
            }
        });
}

function waitForElement(selector, callback) {
    const observer = new MutationObserver((mutations, obs) => {
        if (document.querySelector(selector)) {
            callback(); // 요소가 발견되면 콜백 호출
            obs.disconnect(); // MutationObserver 종료
        }
    });
    observer.observe(document.body, {
        childList: true,
        subtree: true, // 하위 요소를 모두 감시
    });
}


document.addEventListener("DOMContentLoaded", function () {
    waitForElement("#loggedInUserName", UpdatedIdAndName);
    waitForElement("#loggedInId", UpdatedId);
    waitForElement("#loggedInName", UpdatedName);
    waitForElement("#loggedInIdShort", UpdatedIdShort);
    waitForElement("#loggedInNameShort", UpdatedNameShort);
});
