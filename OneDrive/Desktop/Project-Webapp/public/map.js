var map = L.map('map').setView([13.7563, 100.5018], 6);



function getColor(d) {
    return d > 80  ? '#f28e8e' :
           d > 60  ? '#f2cc50' : 
           d > 40  ? '#5ed68b' : 
                     '#ffeda0';
}

function style(feature) {
    return {
        fillColor: getColor(feature.properties.price_index), 
        weight: 1,
        opacity: 1,
        color: 'white', 
        fillOpacity: 0.7
    };
}

fetch('../thai-map/th.json')
    .then(res => res.json())
    .then(data => {
        L.geoJSON(data, {
            style: function(feature){
                return{
                    fillColor: getColor(),
                    weight: 1,
                    opacity: 1,
                    color: 'white',
                    fillOpacity: 0.8
                };
            },
            onEachFeature: function(feature, layer){
                if(feature.properties && feature.properties.name){
                    layer.bindTooltip(feature.properties.name, {
                        permanent: true,
                        direction: 'center',
                        className: 'province-label'
                    }).openTooltip();
                }
            }
        }).addTo(map);
    })
    .catch(err => console.error("Error loading GeoJSON:", err));