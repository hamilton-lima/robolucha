import { GameState } from './gamestate.model';

export class Message{
    type: string;
    data: GameState;
}