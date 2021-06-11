class PerimetreJeu{
    constructor(lat1,lon1,lat2,lon2) {
        this.largeur = lat1 - lat2
        this.longeur = lon2 - lon1
        this.perimetre = this.largeur * this.longeur
    }
}
module.exports = PerimetreJeu
