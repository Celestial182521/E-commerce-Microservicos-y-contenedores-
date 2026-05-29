const express = require('express');
const app = express();

app.get('/', (req, res) => {
  res.send(`
    <!DOCTYPE html>
    <html>
    <head><title>Mi App en Docker</title></head>
    <body>
      <h1>🎉 ¡Funciona en un container!</h1>
      <p>Hostname: ${require('os').hostname()}</p>
      <p>Timestamp: ${new Date().toISOString()}</p>
    </body>
    </html>
  `);
});

app.listen(3000, () => {
  console.log('✅ Servidor escuchando en puerto 3000');
});

