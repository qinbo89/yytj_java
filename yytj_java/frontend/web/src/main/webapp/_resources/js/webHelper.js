var ws;
var SocketCreated = false;
var isUserloggedout = false;
var time=0;

var sendData=function(){
   userId=$("#userId").val();
   ws.send(userId);
}

var sendInterval=function(){

     var t1 = window.setInterval("sendData()",5000);  

} 

var onclose=function(){
   SocketCreated=false;
}

function webSocketConnect() {
         if(SocketCreated==true){
            return false
         }
         
         if(ws!=null && ws.readyState==1){
            return false; 
         }
         
          try {
              if ("WebSocket" in window) {
		        ws = new WebSocket("ws://127.0.0.1:56789");
              }
              else if("MozWebSocket" in window) {
              	ws = new MozWebSocket("ws://127.0.0.1:56789");
              }
              
              SocketCreated = true;
              isUserloggedout = false;
          } catch (ex) {
              Log(ex, "ERROR");
              return;
          }
         
          ws.onopen = function(){
                
                sendInterval();
          
          };
          ws.onmessage = function(){
        	  if(event.data>100000000){time = Date.parse(new Date());}
              Log(event.data);  
          };
          ws.onclose = function(){
             onclose();
          };
          ws.onerror = function(){
              alert("远程链接中断");
          };
			
      
  };


  function Log(Text, MessageType) {
		
    // alert(Text)
     
  };
  
window.setInterval("webSocketConnect()",1000); 

 