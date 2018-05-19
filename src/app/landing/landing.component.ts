import { ChatService } from './../services/chat.service';
import { Component, OnInit } from "@angular/core";
declare var Deck: any;
@Component({
  selector: "app-landing",
  templateUrl: "./landing.component.html",
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {
  constructor(private chat: ChatService) {}
  deck: any;
  cardCount: number;
  players: number;
  xPositions: number[] = [-3, 20, 35, 35, 15, -20, -40, -40, -25];
  yPositions: number[] = [30, 30, 10, -20, -35, -35, -20, 10, 30];
  raise: number;
  pot: number;
  smallBlind: 1;
  bigBlind: 2;
  stack: number;
  message:string;
  messages:any[]=[];

  ngOnInit() {
    this.deck = new Deck();
    let $container = document.getElementById('container');
    this.deck.mount($container);
    this.cardCount = 0;
    this.players = 9;
    this.raise = 0;
    this.pot = 0 ;
    this.stack = 200;


    this.chat.messages.subscribe(msg=> {

      this.messages.push(msg.text.replace(/['"]+/g, ''));
      console.log(msg);
    })
  }

  sendMessage() {
    this.chat.sendMsg(this.message);
    this.message=null;
  }
  public fanCards() {
    this.deck.flip();
    this.deck.fan();
  }
  public deal() {
    for (let player = 0; player < this.players; player++) {
      for (let x = 0; x < 2; x++) {
        this.deck.cards[this.cardCount].animateTo({
          delay: 200,
          duration: 500,
          ease: "quartOut",

          x: x * 5.5 + this.xPositions[player],
          y: this.yPositions[player]
        });
        console.log(this.deck.cards[x]);
        this.cardCount++;
      }
    }
  }
  public flipCards() {
    this.deck.flip();
  }
  public changeRaise(x) {
    this.raise = this.stack * x;
  }
  public bet() {
    this.pot += this.raise;
    this.stack -= this.raise;
    this.raise = 0;
  }
  public shuffle() {
    this.deck.shuffle();
  }
}
