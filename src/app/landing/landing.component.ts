import { Component, OnInit } from '@angular/core';
declare var Deck: any;
@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {

  constructor() { }
  deck: any;
  ngOnInit() {
    this.deck = new Deck();
    let $container = document.getElementById('container');
    this.deck.mount($container);

  }

  public fanCards() {
    this.deck.flip();
    this.deck.fan();
  }


}
