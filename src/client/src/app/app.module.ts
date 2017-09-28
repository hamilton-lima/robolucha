import { GameStateService } from './../services/gamestate.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { GameArenaComponent } from './game-arena/game-arena.component';

@NgModule({
  declarations: [
    AppComponent,
    GameArenaComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [GameStateService],
  bootstrap: [AppComponent]
})
export class AppModule { }
