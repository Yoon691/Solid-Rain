const express = require('express')
const app = express()
const port = 3376

let api = require('./routes/apiRouter')

app.use('/api', api)

app.use('/static', express.static('public'))

app.use(function (req, res, next) {
    res.status(404).send("Sorry can't find that!")
})

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`)
})
if(console.error()){
let ze = true
}