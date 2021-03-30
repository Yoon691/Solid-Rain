
let trophy = require('./Trophy')
let latlng = require('./LatLng')

class UserSurvivant {
    constructor(id,image, position, role, ttl, trophys) {
        this.id = id;
        this.image = image;
        this.position = new latlng(position);
        this.ttl = ttl;
        this.role = role;
        this.trophys =  new trophy(trophys, id);

    }
}

module.exports =  UserSurvivant
