var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var bodyParser = require('body-parser');

var app = express();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var api = require('./routes/api');

app.set('view engine','html')

app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({'extended':'false'}));
app.use(express.static(path.join(__dirname, 'dist')));
app.use('/', express.static(path.join(__dirname, 'dist')));
app.use('/api', api);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.json({
    message: err.message,
    error: err
  });});

// testing socket chat services

var clients = [];
function findUserName(id){
  for(let x= 0;x<clients.length;x++){
    if(clients[x].clientID.slice(5)==id.slice(5)){
      return clients[x].alias;
    }
  }
  return null;
}
io.of('/game').on('connection',function(socket){
  console.log("gamesocket:" +socket.id.slice(5));
  socket.on('newPlayer',(data) => {
    console.log(" name received: " + data);
    let newPlayer = new Object()
    newPlayer.alias = data;
    newPlayer.clientID = socket.id;
    clients.push(newPlayer)

    io.of('/game').emit('newPlayer', {type: 'listOfPlayers', players: clients})
  })

  socket.on('disconnect' , ()=> {
    let name = findUserName(socket.id)
    console.log((name || "A Player")+ " has disconnected");
    clients.pop();
    io.of('/game').emit('newPlayer', {type: 'listOfPlayers', players: clients})
    io.of('/chat').emit('message', {type:'new-message', text: name+ " has disconnected from the game", alias: "Admin"})
  })



});
io.of('/chat').on('connection',function(socket){
  console.log("chatSocket:" + socket.id.slice(5))
  socket.on('message', (message) => {
    console.log("message received: " + JSON.stringify(message));
    io.of('/chat').emit('message', {type: 'new-message', text: message['msg'], alias: message['name']})
  })


});

module.exports = app;

http.listen(5000,() => {
  console.log('started on port 5000');
});
