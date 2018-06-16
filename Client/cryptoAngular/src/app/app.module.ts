import { BrowserModule } from '@angular/platform-browser';
import { MatTabsModule } from '@angular/material/tabs';
import { WindowRefService } from './services/window-ref.service';
import { Web3Service } from './services/web3.service';
import { GameService } from './services/game.service';
import { WebsocketService } from './services/websocket.service';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { LandingComponent } from './landing/landing.component';
import { ChatService } from './services/chat.service';
import { HomepageComponent } from './homepage/homepage.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ParticlesModule } from 'angular-particle';
import { PageHeaderComponent } from './pageHeader/page-header.component';

@NgModule({
  declarations: [
    AppComponent,
    LandingComponent,
    HomepageComponent,
    PageHeaderComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    MatTabsModule,
    NgbModule,
    ParticlesModule
  ],
  providers: [ChatService, WebsocketService, GameService, Web3Service, WindowRefService],
  bootstrap: [AppComponent]
})
export class AppModule { }
