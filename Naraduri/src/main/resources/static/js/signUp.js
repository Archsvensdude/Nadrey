// signUp.html에 포함할 JavaScript
document.querySelector(".check-button-1").addEventListener("click", function (e) {
    e.preventDefault();  // 폼 제출 방지

    const username = document.getElementById("username").value;
    fetch(`/login/check-username?username=${username}`)
        .then(response => response.json())
        .then(data => {
            if (data.available) {
                alert("사용 가능한 아이디입니다.");
            } else {
                alert("이미 사용 중인 아이디입니다.");
            }
        })
        .catch(error => console.error('Error:', error));
});

document.querySelector(".check-button-2").addEventListener("click", function (e) {
    e.preventDefault();  // 폼 제출 방지

    const email = document.getElementById("email").value;
    fetch(`/login/check-email?email=${email}`)
        .then(response => response.json())
        .then(data => {
            if (data.available) {
                alert("사용 가능한 이메일입니다.");
            } else {
                alert("이미 사용 중인 이메일입니다.");
            }
        })
        .catch(error => console.error('Error:', error));
});

document.querySelector("form").addEventListener("submit", function(event) {
    const year = document.getElementById("year").value;
    const month = document.getElementById("month").value;
    const day = document.getElementById("day").value;

    if (year && month && day) {
        // birth 형식: YYYY-MM-DD
        document.getElementById("birth").value = `${year}-${month}-${day}`;
    } else {
        event.preventDefault();
        alert("모든 생년월일 필드를 입력해 주세요.");
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const passwordInput = document.getElementById("password");
    const confirmPasswordInput = document.getElementById("confirmPassword");
    const passwordMatchMessage = document.getElementById("passwordMatchMessage");

    function validatePasswordMatch() {
        if (confirmPasswordInput.value === "") {
            passwordMatchMessage.textContent = ""; // 메시지 초기화
            return;
        }
        if (passwordInput.value === confirmPasswordInput.value) {
            passwordMatchMessage.textContent = "비밀번호가 일치합니다.";
            passwordMatchMessage.style.color = "green";
        } else {
            passwordMatchMessage.textContent = "비밀번호가 일치하지 않습니다.";
            passwordMatchMessage.style.color = "red";
        }
    }

    passwordInput.addEventListener("input", validatePasswordMatch);
    confirmPasswordInput.addEventListener("input", validatePasswordMatch);

    // 회원가입 버튼 클릭 시 비밀번호 일치 여부 확인
    const signUpForm = document.querySelector("form");
    signUpForm.addEventListener("submit", function (event) {
        if (passwordInput.value !== confirmPasswordInput.value) {
            event.preventDefault(); // 폼 제출 방지
            alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        }
    });
});