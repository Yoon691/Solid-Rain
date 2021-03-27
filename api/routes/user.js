// eslint-disable-next-line no-unused-vars
class Id {
    constructor(id) {
        this.id = id;
    }
}

// eslint-disable-next-line no-unused-vars
class Url {
    constructor(url) {
        this.url = url;
    }
}

// eslint-disable-next-line no-unused-vars
class Role {
    constructor(role) {
        this.role = role;
    }
}

// eslint-disable-next-line no-unused-vars
class Ttl {
    constructor(role) {
        this.role = role;
    }
}

// eslint-disable-next-line no-unused-vars
class Trophys {
    constructor(trophys) {
        this.trophys = trophys;
    }
}


// eslint-disable-next-line no-unused-vars
class latLng {
    constructor(role) {
        this.role = role;
    }
}


// eslint-disable-next-line no-unused-vars
class Trophy {
    constructor(desc) {
        this.desc = desc;
    }
}


// eslint-disable-next-line no-unused-vars
class UserSurvivant {
    constructor(image,position,ttl,id) {
        this.image = image;
        this.position = position;
        this.ttl = ttl;
        this.id = id;

    }

}
// eslint-disable-next-line no-unused-vars
class UserAdmin{

    constructor(id) {

        this.id = id;

    }

}

// eslint-disable-next-line no-unused-vars
class Meteorite{

    constructor(impact,composition) {

        this.impact = impact;
        this.composition = composition;

    }

}

// eslint-disable-next-line no-unused-vars
const data = new UserSurvivant ('https://www.google.com/search?q=avatar+gamer&rlz=1C1CHBF_frFR922FR922&sxsrf=ALeKk00eKkWAZilFvBHgUxYbpWte-vDD0g:1616882435373&tbm=isch&source=iu&ictx=1&fir=6xVrtGXAYNWEtM%252Cgq9-7NPuRn1o0M%252C_&vet=1&usg=AI4_-kQMLinu-K9gpZZ34mVbHaTBncf0pg&sa=X&ved=2ahUKEwi0uNC5vNHvAhXIx4UKHYhCA4gQ9QF6BAgVEAE#imgrc=6xVrtGXAYNWEtM',[0,0],0,1)

// eslint-disable-next-line no-undef
module.exports = data
