import { Bullet, Component } from './../models/gamestate.model';
import { GameState, Luchador } from '../models/gamestate.model';
import { Message } from '../models/message.model';
import { Subject } from 'rxjs/Subject';
import { ServerPlugin } from '../models/server.plugin';
import { Observable } from 'rxjs';

const FPS: number = 30;
const INTERVAL: number = 1000 / FPS;
const DELTA: number = INTERVAL / 1000;

const MAX_X: number = 50;
const MAX_Y: number = 50;
const MAX_ROTATION: number = 360;

const SPEED: number = 5; 
const BULLET_SPEED: number = 15;

const MAX_LUCHADORES: number = 3;
const MAX_BULLETS: number = 5;

export class RandomGameState extends ServerPlugin {

    static lastComponentId: number = 1;
    static getComponentId(): string {
        RandomGameState.lastComponentId++;
        return RandomGameState.lastComponentId.toString();
    }

    constructor() {
        super('random-game');
    }

    input: Subject<Message>;
    output: Subject<Message>;
    gameState: GameState;

    public setup(input: Subject<Message>, output: Subject<Message>) {
        this.input = input;
        this.output = output;
        this.gameState = new GameState();
        this.gameState.luchadors.push(this.createLuchador());

        input.subscribe((message) => {
            console.log(this.name, 'message received', JSON.stringify(message));
        });

        Observable.interval(INTERVAL).subscribe((number) => {
            this.update();
            let message: Message = new Message();
            message.type = this.name;
            message.data = this.gameState;
            this.output.next(message);
        });
    }

    update(){

        this.gameState.luchadors.forEach((luchador)=>{
            this.move(luchador);
            this.tag2Remove(luchador);
        });

        this.gameState.bullets.forEach((bullet)=>{
            this.move(bullet);
            this.tag2Remove(bullet);
        });

        if( this.gameState.luchadors.length < MAX_LUCHADORES ){
            this.gameState.luchadors.push( this.createLuchador() );
        }

        if( this.gameState.bullets.length < MAX_BULLETS ){
            this.gameState.bullets.push( this.createBullet() );
        }

        this.gameState.luchadors = this.gameState.luchadors.filter(this.filterNotOutside);
        this.gameState.bullets = this.gameState.bullets.filter(this.filterNotOutside);

    }

    filterNotOutside(component:Component) {
        return component.tag != 'outside';
    }

    tag2Remove(component:Component){
        if( Math.abs(component.x) > MAX_X || Math.abs(component.y) > MAX_Y ){
            component.tag = 'outside';
        }
    }

    move(component:Component){
        let radians = (Math.PI / 180) * component.rotation;
        let x = Math.cos(radians) * SPEED * DELTA;
        let y = Math.sin(radians) * SPEED * DELTA;

        component.x += x; 
        component.y += y;  
    }

    createLuchador(): Luchador {
        let result: Luchador = this.addComponentRandomData(new Luchador()) as Luchador;
        result.gunRotation = Math.random() * MAX_ROTATION;
        return result;
    }

    createBullet(): Bullet {
        return this.addComponentRandomData(new Bullet());
    }

    addComponentRandomData(object: Component): Component {
        let result: Bullet = new Bullet();
        result.x = Math.random() * MAX_X;
        result.y = Math.random() * MAX_Y;
        result.rotation = Math.random() * MAX_ROTATION;
        result.id = RandomGameState.getComponentId();
        return result;
    }
}

export default new RandomGameState();
