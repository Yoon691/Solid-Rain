const express = require('express');

const app = express();
const port = 3376;

const api = require('./routes/apiRouter');

app.get('/', (req, res) => {
    res.send(
        'Vous étes a la racine utilisez ce lien http://localhost/api/resources pour accéder aux resources, sinon http://localhost/static pour accéder aux fichiers static du dossier public '
    );
});

app.use('/api', api);

app.use('/static', express.static('public'));

app.use((req, res) => {
    res.status(404).send("Sorry can't find that!");
});

app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`);
});
