// 페이지가 로드된 후 실행
document.addEventListener("DOMContentLoaded", function () {
    const foundIdElement = document.querySelector("[th\\:text='${foundId}']");
    if (foundIdElement) {
        const originalId = foundIdElement.textContent;
        foundIdElement.textContent = maskId(originalId);
    }

    function maskId(id) {
        if (id.length <= 4) {
            return "*".repeat(id.length);
        }
        const visiblePart = id.substring(0, 4);
        const maskedPart = "*".repeat(id.length - 4);
        return visiblePart + maskedPart;
    }
});