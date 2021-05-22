//MàJ de l'indicateur numérique du zoom
import mapfunc from "./map";
import * as $ from 'jquery'
const formfunc = (function(){
        let updateMap =  mapfunc.update()

       function updateZoomValue() {
           $('#zoomValue').html($('#zoom').val());
           mapfunc.update()
       }

// Abonnement aux événements de changement
       $('#lat').change(updateMap);
       $('#lon').change(updateMap);
       $('#zoom').change(updateZoomValue);



       //mapfunc.update()
       let mymap =  mapfunc.init()
       console.log(mymap)



       let popup = L.popup();

       function onMapClick(e) {
           popup
               .setLatLng(e.latlng)
               .setContent("You clicked the map at " + e.latlng.toString())
               .openOn(mymap);
           console.log(e.latlng.lat.toString())
           console.log(e.latlng.lng.toString())
           $('#lat1').val(e.latlng.lat.toString())
           $('#lon1').val(e.latlng.lng.toString())
       }

       function onMapdbClick(e) {
           popup
               .setLatLng(e.latlng)
               .setContent("You clicked the map at " + e.latlng.toString())
               .openOn(mymap);
           console.log(e.latlng.lat.toString())
           console.log(e.latlng.lng.toString())
           $('#lat2').val(e.latlng.lat.toString())
           $('#lon2').val(e.latlng.lng.toString())
       }

      mymap.on('click', onMapClick);


       function updateCorner1() {
           let $lat1 = $('#lat1').val()
           let $lon1 = $('#lon1').val()
           L.marker([$lat1, $lon1]).addTo(mymap).bindPopup('Corner1<br><strong>lat1lon1</strong>.').openPopup();
           mymap.off('click', onMapClick)
           mymap.on('click', onMapdbClick);

       }

       function updateCorner2() {
           let $lat2 = $('#lat2').val()
           let $lon2 = $('#lon2').val()
           L.marker([$lat2, $lon2]).addTo(mymap).bindPopup('Corner2<br><strong>lat2lon2</strong>.').openPopup();
           mymap.off('click', onMapdbClick)
           mymap.on('click', onMapClick);

       }

       function sendZrr() {
           let $lat1 = $('#lat1').val()
           let $lon1 = $('#lon1').val()
           let $lat2 = $('#lat2').val()
           let $lon2 = $('#lon2').val()
           L.polygon([
               [$lat1, $lon1],
               [$lat1, $lon2],
               [$lat2, $lon2],
               [$lat2, $lon1]
           ]).addTo(mymap);

       }


       $('#setCorner1').on('submit', function (e) {
           e.preventDefault();
           updateCorner1();
       })

       $('#setCorner2').on('submit', function (e) {
           e.preventDefault();
           updateCorner2();
       })

       $('#sendZrr').on('submit', function (e) {
           e.preventDefault();
           sendZrr();
           $.post(
               'http://localhost:3376/admin/zrr',
               {
                   lat1: $("#lat1").val(),
                   lon1: $("#lon1").val(),
                   lat2: $("#lat2").val(),
                   lon2: $("#lon2").val()
               },

               function (data) {
                   let dataParse = JSON.parse(JSON.stringify(data));
                   alert(dataParse.message)


               },

               'json'
           );

       });



    function setTtlf() {
        let $ttl = $('#ttl').val()
        console.log($ttl)
        return $ttl
    }

    $('#setTtl').on('submit', function (e) {
        e.preventDefault();
        let $ttl = setTtlf()
        $.post(
            'http://localhost:3376/admin/ttl',
            {
                ttl: $ttl
            },

            function (data) {
                let datajson = JSON.stringify(data);
                let dataParse = JSON.parse(datajson);
                alert(dataParse.message)

            },

            'json'
        );


    });

    $('#setMeteorType').on('submit', function (e) {
        e.preventDefault();
        $.post(
            'http://localhost:3376/admin/feu',
            {
                meteor: $('#pet-select').val(),
                startJeu: true
            },

            function (data) {
                let $typeMeteore = $('#pet-select').val()
                mymap.off('click', onMapClick)
                mymap.on('click', function (e) {
                    popup
                        .setLatLng(e.latlng)
                        .setContent("triggering of impact at " + e.latlng.toString())
                        .openOn(mymap);
                    L.marker([e.latlng.lat.toString(), e.latlng.lng.toString()]).addTo(mymap).bindPopup('Type de météorite : ' + $typeMeteore).openPopup();
                    let dataParse = JSON.parse(JSON.stringify(data));
                    alert(dataParse.message)
                })


            },

            'json'
        );

    });

    // $('#addUser').on('submit', function (e) {
    //     e.preventDefault();
    //     $.post(
    //         'http://localhost:3376/admin/addUser',
    //         {
    //             login: $('#login').val()
    //         },
    //
    //         function (data) {
    //
    //             let datajson = JSON.stringify(data);
    //             let dataParse = JSON.parse(datajson);
    //             console.log(dataParse.id)
    //             console.log(dataParse.image)
    //             console.log(dataParse.position)
    //             console.log(dataParse.ttl)
    //             console.log(dataParse.role)
    //             console.log(dataParse.trophys)
    //             let $lienimage = $('<a href="javascript:updateImage(\'Toto\');"><img src=dataParse.image class="icon"></a>')
    //             let $lienId = $('<a href="javascript:updateName(\'Toto\');">dataParse.id</a>')
    //             let $ttl = $('<strong>TTL</strong> : dataParse.ttl')
    //             let $trophy = $('<strong>Trophys</strong> : dataParse.trophys')
    //             //$('#userinitial').after('<ul><li><a href="javascript:updateImage('Toto');"><img src=dataParse.image class="icon"></a><a href="javascript:updateName('Toto');">dataParse.id</a><strong>TTL</strong> : dataParse.ttl <strong>Trophys</strong> : dataParse.trophys </li></ul>')
    //             $('#userinitial').after('<ul>', function () {
    //                 $(this).after('<li>', function () {
    //                     $(this).after('<a href="javascript:updateImage(\'Toto\');"><img src=dataParse.image class="icon"></a>', function () {
    //                         $(this).after('<a href="javascript:updateName(\'Toto\');">dataParse.id</a>', function () {
    //                             $(this).after('<strong>TTL</strong> : dataParse.ttl', function () {
    //                                 $(this).after('<strong>Trophys</strong> : dataParse.trophys', function () {
    //                                     $(this).after('</li>', function () {
    //                                         $(this).after('</ul>')
    //                                     })
    //                                 })
    //                             })
    //                         })
    //                     })
    //                 })
    //             });
    //
    //
    //         },
    //
    //         'json' // Nous souhaitons recevoir "Success" ou "Failed", donc on indique text !
    //     );
    //
    // });

})
export default formfunc;
