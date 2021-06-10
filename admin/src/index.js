//import './css/style.css'
// export * from './css/fonts'

import mapfunc from './js/map'
import formfunc from './js/form'


//require('./css/fonts')

$(document).ready(function (){
    mapfunc.init;
    console.log(mapfunc.init)
    new formfunc;

})

require('./css/style.css')
