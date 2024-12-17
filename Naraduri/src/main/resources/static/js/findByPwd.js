// 페이지가 로드된 후 실행
document.addEventListener("DOMContentLoaded", function () {
    const foundPasswordElement = document.querySelector("[th\\:text='${foundPassword}']");
    if (foundPasswordElement) {
        const originalPassword = foundPasswordElement.textContent;
        foundPasswordElement.textContent = maskPassword(originalPassword);
    }

    function maskPassword(password) {
        if (id.length <= 4) {
            return "*".repeat(password.length);
        }
        const visiblePart = password.substring(0, 4);
        const maskedPart = "*".repeat(password.length - 4);
        return visiblePart + maskedPart;
    }
});