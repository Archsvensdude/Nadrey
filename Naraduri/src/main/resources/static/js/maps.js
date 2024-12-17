if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
        var pos = new naver.maps.LatLng(position.coords.latitude, position.coords.longitude);
        map.setCenter(pos);  // 현재 위치로 지도의 중심 설정
    });
}