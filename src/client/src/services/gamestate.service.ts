import { Message } from './../models/message.model';
import { GameState } from './../models/gamestate.model';
import { Subject } from 'rxjs/Rx';
import { constructDependencies } from '@angular/core/src/di/reflective_provider';
import { Injectable } from '@angular/core';
import * as io from 'socket.io-client';

@Injectable()
export class GameStateService {

    private url: string = 'http://localhost:3000';
    private socket;
    readonly onMessage: Subject<GameState> = new Subject<GameState>();

    constructor() {
        this.socket = io(this.url);
        this.socket.on('message', (message: Message) => {
            console.log('message received', message );
            this.onMessage.next(message.data);
        });
    }


}