//import './css/style.css'
// export * from './css/fonts'

import mapfunc from './js/map'
import {formfunc} from './js/form'
import {apiPath} from "./js/form";


//let apiPathbuild = ;

//require('./css/fonts')

$(document).ready(function (){
    mapfunc.init;
    console.log(mapfunc.init)
    new formfunc;
    //apiPath = "http://localhost:3376"


})


