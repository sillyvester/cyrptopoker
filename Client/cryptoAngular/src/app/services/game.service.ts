import { Injectable } from "@angular/core";
import { WebsocketService } from "./websocket.service";
import { Observable, Subject } from "rxjs/Rx";
@Injectable()
export class GameService {
  private gameSocket; // gameSocket determines when a person connects to the game application.
  // Production
  private gameURL = "http://18.237.179.99:5000/game";

  // Dev
  // private gameURL = "http://localhost:5000/game";
  // calls the wsService connect method
  constructor() {
  }

  // interface to send messages back to
  // to the socket.io server


  connectToGame(): any {
    this.gameSocket = io(this.gameURL);
  }

  checkNewPlayer(): Observable<any>{
    let observable = new Observable(observer => {
      this.gameSocket.on("newPlayer", data => {
        console.log("Received name from gameServer");
        observer.next(data);
      });
      return () => {
        this.gameSocket.disconnect();
      };
    });
    return observable;
  }


  addNewPlayer(data): any {
    this.gameSocket.emit("newPlayer", JSON.stringify(data));
  }

}
