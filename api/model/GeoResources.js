const Trophy = require('./Trophy');
const Latlng = require('./LatLng');

class GeoResources {
    constructor(id, image, position, role, ttl, trophys) {
        this.id = id;
        this.image = image;
        this.position = new Latlng(position);
        this.ttl = ttl;
        this.role = role;
        this.trophys = new Trophy(trophys, id);
    }
}

module.exports = GeoResources;
