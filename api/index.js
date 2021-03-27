// eslint-disable-next-line no-undef
const express = require('express')
const app = express()
const port = 3376
// eslint-disable-next-line no-undef
let api = require('./routes/api')

app.use('/api', api)


app.use('/static', express.static('public'))

app.use(function (req, res, next) {
    res.status(404).send("Sorry can't find that!")
})

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`)
})
