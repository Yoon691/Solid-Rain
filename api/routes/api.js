// eslint-disable-next-line no-undef
let express = require('express')
let router = express.Router()
// eslint-disable-next-line no-undef,no-unused-vars
const model = require('./user')
// eslint-disable-next-line no-undef,no-unused-vars
const bodyParser = require('body-parser')

router.use(bodyParser.json());
//middleware that is speciic to this router
//Get /resources
router.get('/resources', (req, res) =>{
    //res.send({model})
    res.json(model)
});

//Put /resources/{resourcesId}/position
// eslint-disable-next-line no-unused-vars
router.put('/resources/:resourceId/position', (req, res) => {
    //const user = model.Setposition(req.params.position)
    //console.log(req)
    if (req.params.resourceId ===  model.id.toString()){
        // console.log(req.body);

        res.status(201).json({message : 'position modofier'})
        model.position =  req.body
    }
    //if (!user) return res.status(404).json({})
    // console.log(req.body);
    //
    // res.status(201).json({message : 'position modofier'})
    // //model.position = req.params.position
    // // eslint-disable-next-line no-undef
    // model.position =  req.body
    // //res.send('seccefull opération')

})

//Put /resources/{resourcesId}/images
// eslint-disable-next-line no-unused-vars
router.put('/resources/:resourceId/images', (req, res) =>{

    if (req.params.resourceId ===  model.id.toString()) {

        res.status(201).json({message: 'image modifier'})
        model.image = req.body
    }
})

// eslint-disable-next-line no-undef
module.exports = router
