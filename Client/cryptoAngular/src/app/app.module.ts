import { WindowRefService } from './services/window-ref.service';
import { Web3Service } from './services/web3.service';
import { GameService } from './services/game.service';
import { WebsocketService } from './services/websocket.service';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { LandingComponent } from './landing/landing.component';
import { ChatService } from './services/chat.service';


@NgModule({
  declarations: [
    AppComponent,
    LandingComponent,

  ],
  imports: [
    FormsModule,
    BrowserModule
  ],
  providers: [ChatService, WebsocketService, GameService, Web3Service,WindowRefService],
  bootstrap: [AppComponent]
})
export class AppModule { }
