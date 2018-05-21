import { Injectable } from "@angular/core";
import * as io from "socket.io-client";
import { Observable } from "rxjs/Observable";
import * as Rx from "rxjs/Rx";

@Injectable()
export class WebsocketService {
  private chatSocket; // chatSocket that connects to our chatSocket.io server

  // Production
  // private chatURL = "http://18.237.179.99:5000/chat";

  // Dev
  private chatURL = "http://localhost:5000/chat";
  constructor() {}

  connectToChat(): Rx.Subject<MessageEvent> {
    // Production
    this.chatSocket = io("http://18.237.179.99:5000");

    // Dev
    // this.chatSocket = io(this.chatURL);

    // Observable to check for any incoming messages to the chatSocket.io server
    let observable = new Observable(observer => {
      this.chatSocket.on("message", data => {
        console.log("Received message from WebchatSocket Server: ");
        observer.next(data);
      });
      return () => {
        this.chatSocket.disconnect();
      };
    });

    // observer is defined to listen to messages
    // from other components and send those messages back
    // to the chatSocket server when the 'next()' method is called
    let observer = {
      next: (data: any) => {
        this.chatSocket.emit("message", data);
      }
    };
    // return the Rx.Subject, which combines
    // Observer and Observable
    return Rx.Subject.create(observer, observable);
  }






}
