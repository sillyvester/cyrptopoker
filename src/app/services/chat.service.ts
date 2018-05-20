import { Injectable } from '@angular/core';
import { WebsocketService } from './websocket.service';
import { Observable, Subject } from 'rxjs/Rx';
@Injectable()
export class ChatService {
  messages: Subject<any>;
  // calls the wsService connect method
  constructor(private wsService: WebsocketService) {
    this.messages = <Subject<any>>wsService
      .connect()
      .map((response: any): any => {
        return response;
      });

   }

   // interface to send messages back to
   // to the socket.io server

   sendMsg(msg, name) {
     let messageObject = {
       "msg": msg,
       "name": name
     };
     this.messages.next(messageObject);
   }



}
