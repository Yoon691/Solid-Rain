const express = require('express');

const router = express.Router();

const model = require('../model/DaoGeoRessources');

router.use(express.json());
router.use(express.urlencoded({ extended: true }));

// middlewars

router.get('/resources', (req, res) => {
    res.json(model.tabResources);
});

router.put('/resources/:resourceId/position', (req, res) => {
    res.status(204).json({ message: 'successful operation' });
    model.updatePosition(req.params.resourceId, req.body);
});

router.put('/resources/:resourceId/images', (req, res) => {
    res.status(204).json({ message: 'successful operation' });
    const urlimage = JSON.stringify(req.body);
    const imageurl = JSON.parse(urlimage);
    model.updateImage(req.params.resourceId, imageurl.image);
});

module.exports = router;
