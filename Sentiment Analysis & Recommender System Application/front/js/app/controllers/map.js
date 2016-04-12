ItemApp.controller('mapCtrl', ['item', function (item) {
        console.log('\nMap controller loads!');

        var map, marker, pos;
        var lon = item[0].long;
        var lat = item[0].lat;

        /** Initial options of the Map */
        var mapMapOptions = {
            zoom: 18, //Zoom level: 1 to 20
            center: new google.maps.LatLng(lat, lon) // MERCATOR
        };
        function initialize() {
            console.log('Map Init!');
            map = new google.maps.Map(document.getElementById('map'), mapMapOptions);

            /** Initial options of the Marker */
            pos = new google.maps.LatLng(lat,lon);
            var mapMarkerOptions = {
                position: pos,
                map: map
            };
            marker = new google.maps.Marker(mapMarkerOptions);
            map.panTo(pos);
        }

        google.maps.event.addDomListener(window, "load", initialize());
    }]);