// PeerJS server location
var SERVER_IP = '192.168.1.110';
var SERVER_PORT = 8080;

document.addEventListener('DOMContentLoaded', function () {

  // DOM elements manipulated as user interacts with the app
  var messageBox = document.querySelector('#messages');
  var callerIdEntry = document.querySelector('#caller-id');
  var connectBtn = document.querySelector('#connect');
  var recipientIdEntry = document.querySelector('#recipient-id');
  var dialBtn = document.querySelector('#dial');
  var finishBtn = document.querySelector('#undial');
  var remoteVideo = document.querySelector('#remote-video');
  var localVideo = document.querySelector('#local-video');

  // the ID set for this client
  var callerId = null;
  var busy = false;
  // PeerJS object, instantiated when this client connects with its
  // caller ID
  var peer = null;

  // the local video stream captured with getUserMedia()
  var localStream = null;
  var currentCall = null;
  // DOM utilities
  var makePara = function (text) {
    var p = document.createElement('p');
    p.innerText = text;
    return p;
  };

  var addMessage = function (para) {
    if (messageBox.firstChild) {
      messageBox.insertBefore(para, messageBox.firstChild);
    }
    else {
      messageBox.appendChild(para);
    }
  };

  var logError = function (text) {
    var p = makePara('ERROR: ' + text);
    p.style.color = 'red';
    addMessage(p);
  };

  var logMessage = function (text) {
    addMessage(makePara(text));
  };

  // get the local video and audio stream and show preview in the
  // "LOCAL" video element
  // successCb: has the signature successCb(stream); receives
  // the local video stream as an argument
  var getLocalStream = function (successCb) {
    if (localStream && successCb) {
      successCb(localStream);
    }
    else {
      navigator.webkitGetUserMedia(
        {
          audio: true,
          video: false
        },

        function (stream) {
          localStream = stream;

          localVideo.src = window.URL.createObjectURL(stream);

          if (successCb) {
            successCb(stream);
          }
        },

        function (err) {
          logError('No se pudo acceder a la camara');
          logError(err.message);
        }
      );
    }
  };

  // set the "REMOTE" video element source
  var showRemoteStream = function (stream) {
    remoteVideo.src = window.URL.createObjectURL(stream);
  };

  // set caller ID and connect to the PeerJS server
  var connect = function () {
    callerId = callerIdEntry.value;

    if (!callerId) {
      logError('Ingrese un contacto valido');
      return;
    }

    try {
      // create connection to the ID server
      peer = new Peer(callerId, {host: SERVER_IP, port: SERVER_PORT});

      // hack to get around the fact that if a server connection cannot
      // be established, the peer and its socket property both still have
      // open === true; instead, listen to the wrapped WebSocket
      // and show an error if its readyState becomes CLOSED
      peer.socket._socket.onclose = function () {
        logError('No se conecto al servidor');
        peer = null;
      };

      // get local stream ready for incoming calls once the wrapped
      // WebSocket is open
      peer.socket._socket.onopen = function () {
        getLocalStream();
      };

      // handle events representing incoming calls
      peer.on('call', answer);
    }
    catch (e) {
      peer = null;
      logError('Error al conectar al servidor');
    }
  };

  var undial = function(){
    document.getElementById('undial').style.display = 'none';
    document.getElementById('remoteUser').style.display = 'none';
    currentCall.close();
  }

  // make an outgoing call
  var dial = function () {
    if (!peer) {
      logError('Conectese antes');
      return;
    }

    if (!localStream) {
      logError('No se inicio llamada porque no se encontro camara');
      return
    }

    var recipientId = recipientIdEntry.value;

    if (!recipientId) {
      logError('No especifico destinatario');
      return;
    }

    getLocalStream(function (stream) {
      logMessage('Se inicio llamada');

      var call = peer.call(recipientId, stream);
      currentCall = call;

      busy = true;

      if(busy==true){
        document.getElementById('undial').style.display = '';
        document.getElementById('remoteUser').style.display = '';
      }
      else{
        document.getElementById('undial').style.display = 'none';
        document.getElementById('remoteUser').style.display = 'none';
      }
      call.on('stream', showRemoteStream);

      call.on('error', function (e) {
        logError('Error al llamar');
        logError(e.message);
        busy = false;
      });

      call.on('close', function(){
              call.close();
              currentCall.close();
              currentCall=null;
              busy=false;
              document.getElementById('undial').style.display = 'none';
              document.getElementById('remoteUser').style.display = 'none';
            });

    });
  };

  // answer an incoming call
  var answer = function (call) {
    if (!peer) {
      logError('No se puede contestar sino esta conectado');
      return;
    }


    if (!localStream) {
      logError('No se inicio el stream');
      return;
    }
    if(busy==true){
        call.close();
    }
    else{
        var confirmar = confirm("Responder llamada?");

        if(confirmar==true){
            logMessage('Llamada aceptada');
            call.answer(localStream);
            currentCall = call;
            busy = true;
            //user clicked "ok"
            logMessage('Llamada aceptada');

            call.on('stream', showRemoteStream);
            call.on('close', function(){
                call.close();
                currentCall.close();
                currentCall= null;
                logMessage('Me cortaron');
                document.getElementById('undial').style.display = 'none';
                document.getElementById('remoteUser').style.display = 'none';
                busy = false;
            });

        }else{
            call.close();
            busy=false;
        }
        
        if(busy==true){
            document.getElementById('undial').style.display = '';
            document.getElementById('remoteUser').style.display = '';
        }
        else{
            document.getElementById('undial').style.display = 'none';
            document.getElementById('remoteUser').style.display = 'none';
        }
    }
  };

  // wire up button events
  connectBtn.addEventListener('click', connect);
  dialBtn.addEventListener('click', dial);
  finishBtn.addEventListener('click', undial);
});

function connect(){
    document.getElementById('connect').click();
}


function select_user(user){
    document.getElementById('recipient-id').value = user;
    document.getElementById('dial').click();
}

function myFunc(){
    var label1 = document.getElementById('prueba');
    var response = '';
    $.ajax({ type: "GET",
             url: "http://"+SERVER_IP+":"+SERVER_PORT+"/peerjs/peers",
             async: false,
             success : function(text)
             {
                 response = text;
             }
    });
//    label1.value = Object.prototype.toString.call(response);
    tableContacts = document.getElementById('tableContacts');
    response.sort();

    tableContacts.innerHTML = "";
    var newRow = document.createElement("tr");
    tableContacts.appendChild(newRow);
    var row = tableContacts.insertRow(0);
    var header = document.createElement("th");
    var arrayLength = response.length;
    if(arrayLength==1)    header.innerHTML = "NO HAY USUARIOS CONECTADOS";
    else    header.innerHTML = "LISTA DE USUARIOS CONECTADOS";
    newRow.appendChild(header);


    for (var i = 0; i < arrayLength; i++) {
        if(response[i]!=document.getElementById('caller-id').value){
            $('#tableContacts').append( "<tr><td onclick=\"select_user('"+response[i]+"')\">"+response[i]+"</td></tr>" );
        }
    }
}
setInterval(myFunc, 5000);
