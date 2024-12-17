document.getElementById("searchButton").addEventListener("click", function() {
    const searchTerm = document.getElementById("searchInput").value.toLowerCase();
    const items = document.querySelectorAll(".post-item");

    items.forEach(item => {
        const title = item.getAttribute("data-title").toLowerCase();
        if (title.includes(searchTerm)) {
            item.style.display = "grid"; // 검색어가 포함된 경우 표시
        } else {
            item.style.display = "none"; // 검색어가 포함되지 않은 경우 숨김
        }
    });

    // DOM 업데이트 강제 적용
    requestAnimationFrame(() => {
        items.forEach(item => {
            const title = item.getAttribute("data-title").toLowerCase(); // title을 다시 정의
            if (!title.includes(searchTerm)) {
                item.style.display = "none";
                item.offsetHeight; // 리플로우 강제 트리거
            }
        });
    });
});
