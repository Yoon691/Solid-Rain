const express = require('express');

const router = express.Router();

let ttl = 1
let demarerPartie = false
let typeMeteor = {}

const model = require('../model/DaoGeoRessources');
const zrr = require('../model/PerimetreJeu')

router.use(express.json());
router.use(express.urlencoded({ extended: true }));


// middlewars

router.post('/zrr', (req, res) => {
    let zrrjson = JSON.parse(JSON.stringify(req.body))
    let perimetreJeu = new zrr(zrrjson.lat1,zrrjson.lon1,zrrjson.lat2,zrrjson.lon2)

    console.log(perimetreJeu)
    //res.send("Success");
    res.status(200).json({ message: 'le périmètre de jeu est fixé' });});


router.post('/ttl', (req, res) => {
    let ttlParse = JSON.parse(JSON.stringify(req.body));
    ttl = ttlParse.ttl
    console.log(ttl)
    res.status(200).json({ message: 'le ttl est met a jour' });
});

router.post('/feu', (req, res) => {
    let data = JSON.parse(JSON.stringify(req.body));
    typeMeteor = data.meteor
    demarerPartie = data.startJeu
    console.log(typeMeteor)
    console.log(demarerPartie)
    res.status(200).json({ message: 'La partie  commence ',
                                      typeMeteor: typeMeteor});
});
router.post('/addUser', (req, res) => {
    let loginjson = JSON.stringify(req.body);
    let loginParse = JSON.parse(loginjson);
    res.status(200).json(model.getResoursebyId(loginParse.login))

});

module.exports = router;
