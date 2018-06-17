import { BrowserModule } from '@angular/platform-browser';
import { MatTabsModule } from '@angular/material/tabs';
import { WindowRefService } from './services/window-ref.service';
import { Web3Service } from './services/web3.service';
import { GameService } from './services/game.service';
import { WebsocketService } from './services/websocket.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule, TemplateRef } from '@angular/core';
import { AppComponent } from './app.component';
import { LandingComponent } from './landing/landing.component';
import { ChatService } from './services/chat.service';
import { HomepageComponent } from './homepage/homepage.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ParticlesModule } from 'angular-particle';
import { PageHeaderComponent } from './pageHeader/page-header.component';
import { ModalModule } from 'ngx-bootstrap';
import { BsModalService,  BsModalRef } from 'ngx-bootstrap/modal';
import { NgSelectModule } from '@ng-select/ng-select';
@NgModule({
  declarations: [
    AppComponent,
    LandingComponent,
    HomepageComponent,
    PageHeaderComponent,
  ],
  imports: [
    BrowserModule,
    MatTabsModule,
    NgbModule,
    ParticlesModule,
    ModalModule.forRoot(),
    NgSelectModule,
    FormsModule,
    ReactiveFormsModule,
    FormsModule,
  ],
  providers: [ChatService, WebsocketService, GameService, Web3Service, WindowRefService],
  bootstrap: [AppComponent]
})
export class AppModule { }
