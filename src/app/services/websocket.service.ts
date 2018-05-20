import { Injectable } from "@angular/core";
import * as io from "socket.io-client";
import { Observable } from "rxjs/Observable";
import * as Rx from "rxjs/Rx";

@Injectable()
export class WebsocketService {
  private socket; // socket that connects to our socket.io server
  constructor() {}

  connect(): Rx.Subject<MessageEvent> {
    // Production
    this.socket = io("http://18.237.179.99:5000");

    // Dev
    // this.socket = io("http://localhost:5000");

    // Observable to check for any incoming messages to the socket.io server

    let observable = new Observable(observer => {
      this.socket.on("message", data => {
        console.log("Received message from Websocket Server");
        observer.next(data);
      });
      return () => {
        this.socket.disconnect();
      };
    });

    // observer is defined to listen to messages
    // from other components and send those messages back
    // to the socket server when the 'next()' method is called

    let observer = {
      next: (data: any) => {
        this.socket.emit("clientInfo", JSON.stringify(data.name));
        this.socket.emit("message", JSON.stringify(data.msg));
      }
    };



    // return the Rx.Subject, which combines
    // Observer and Observable
    return Rx.Subject.create(observer, observable);
  }


}
