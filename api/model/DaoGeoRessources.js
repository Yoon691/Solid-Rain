/** Normalement il faut creé les trois classes:
 * GeoResource
 * LatLng
 * Trophy
 **/
let georesources = require('./GeoResources');

class DaoGeoRessources{

    static resource1 = new georesources(1,'myAvatar1.png',[0,0],'player',10,'primer');
    static resource2 = new georesources(2,'myAvatar2.png',[5,5],'player',20,'second');
    static resource3 = new georesources(3,'myAvatar3.png',[7,7],'player',30,'therd');

    static tabResources = [this.resource1, this.resource2, this.resource3];

    static getResoursebyId(id){
        for (let i=0; i <= this.tabResources.length; i++) {
            if (this.tabResources[i].id.toString() === id){
                return this.tabResources[i];
            }
        }
    }
    static updatePosition(id,donne){
        this.getResoursebyId(id).position = donne;
    }

    static updateImage(id,donne){
        this.getResoursebyId(id).image = donne;
    }

}

module.exports = DaoGeoRessources;
