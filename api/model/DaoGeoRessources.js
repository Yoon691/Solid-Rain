const GeoResources = require('./GeoResources');

class DaoGeoRessources {
    static resource1 = new GeoResources(
        1,
        'myAvatar1.png',
        [0, 0],
        'player',
        10,
        'primer'
    );

    static resource2 = new GeoResources(
        2,
        'myAvatar2.png',
        [5, 5],
        'player',
        20,
        'second'
    );

    static resource3 = new GeoResources(
        3,
        'myAvatar3.png',
        [7, 7],
        'player',
        30,
        'therd'
    );

    static tabResources = [this.resource1, this.resource2, this.resource3];

    static getResoursebyId(id) {
        for (let i = 0; i <= this.tabResources.length; i += 1) {
            if (this.tabResources[i].id.toString() === id) {
                return this.tabResources[i];
            }
        }
    
    }

    static updatePosition(id, donne) {
        this.getResoursebyId(id).position = donne;
    }

    static updateImage(id, donne) {
        this.getResoursebyId(id).image = donne;
    }
}

module.exports = DaoGeoRessources;
