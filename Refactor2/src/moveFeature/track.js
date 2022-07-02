points = [
    {lat: 33.5, lon: 44.6},
    {lat: -13.5, lon: 22.6},
    {lat: 88.5, lon: 129.6},
]

result = trackSummary(points)
console.log(result)

function trackSummary(points) {
    const totalTime = calculateTime();
    const pace = totalTime / 60 / totalDistance(points);

    return {
        time: totalTime,
        distance: totalDistance(points),
        pace: pace
    };

//------------------------------------------------------------------------------   //
    function calculateTime() {
        return 10000;
    }

}

function totalDistance(points) {
    let result = 0;
    for (let i = 1; i < points.length; i++) {
        result += distance(points[i - 1], points[i]);
    }
    return result;
}

// 计算两个坐标经纬度的距离
function distance(p1, p2) {
    console.log('distance invoked')
    const EARTH_RADIUS = 3959;
    const dLat = radians(p2.lat) - radians(p1.lat);
    const dLon = radians(p1.lon) - radians(p2.lon);
    const a = Math.pow(Math.sin(dLat / 2), 2)
        + Math.cos(radians(p2.lat))
        * Math.cos(radians(p1.lat))
        * Math.pow(Math.sin(dLon / 2), 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return EARTH_RADIUS * c;
}

// 计算弧度
function radians(degree) {
    return degree * Math.PI / 180;
}