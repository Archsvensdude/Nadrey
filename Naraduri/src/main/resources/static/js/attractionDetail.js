// attractionDetail.js
document.addEventListener("DOMContentLoaded", function () {
    const likeButton = document.querySelector(".editButton");
    if (likeButton) {
        likeButton.addEventListener("click", function (event) {
            event.preventDefault();

            const site = likeButton.dataset.site; // 데이터 속성으로 관광지 ID 가져오기
            fetch(`/attraction/site/${site}/like`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then((response) => {
                    if (!response.ok) {
                        throw new Error("좋아요 요청 실패");
                    }
                    return response.json();
                })
                .then((data) => {
                    if (data.likes !== undefined) {
                        document.querySelector(".post-likes").textContent = `👍 ${data.likes}`;
                    } else {
                        alert(data.message);
                    }
                })
                .catch((error) => {
                    console.error("좋아요 처리 중 오류:", error);
                    alert("로그인이 필요합니다.");
                });
        });
    }
});

function updateMainImage(thumbnail) {
    const mainImage = document.querySelector('.main-image');
    mainImage.src = thumbnail.src;
}