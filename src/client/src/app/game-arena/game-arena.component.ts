import { Subscription } from 'rxjs/Rx';
import { GameState } from './../../models/gamestate.model';
import { GameStateService } from '../../services/gamestate.service';
import { Component, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-game-arena',
  templateUrl: './game-arena.component.html',
  styleUrls: ['./game-arena.component.css']
})
export class GameArenaComponent implements OnInit, OnDestroy {

  state: GameState;
  subscription: Subscription;

  constructor(private service: GameStateService) { }

  ngOnInit() {
    this.subscription = this.service.onMessage.subscribe((state) => {
      this.state = state;
    });
  }

  public ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
