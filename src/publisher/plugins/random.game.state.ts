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
const ROTATION_ON_HITWALL = 10;

const ROTATION_SPEED: number = 280;
const SPEED: number = 20;
const BULLET_SPEED: number = 60;

const MAX_LUCHADORES: number = 10;
const MAX_BULLETS: number = 20;
const BORDER = 15;
const BULLET_INTERVAL = 2000;

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
        this.gameState.luchadores.push(this.createLuchador());

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

    update() {

        this.gameState.luchadores.forEach((luchador) => {
            if (luchador.toRotate > 0) {
                this.rotate(luchador);
            }
            this.move(luchador, SPEED);

            if (this.isOutside(luchador) && luchador.toRotate <= 0) {
                luchador.toRotate = ROTATION_ON_HITWALL + (ROTATION_ON_HITWALL * Math.random());
            }
            
            let elapsed = new Date().getTime() - luchador.lastBullet;
            if( elapsed > BULLET_INTERVAL ){
                this.gameState.bullets.push(this.createBullet(luchador));
                luchador.lastBullet = new Date().getTime();
            }

        });

        this.gameState.bullets.forEach((bullet) => {
            this.move(bullet, BULLET_SPEED);
            if (this.isOutside(bullet)) {
                bullet.tag = 'remove';
            }
        });

        if (this.gameState.luchadores.length < MAX_LUCHADORES) {
            this.gameState.luchadores.push(this.createLuchador());
        }

        this.gameState.luchadores = this.gameState.luchadores.filter(this.filterNotOutside);
        this.gameState.bullets = this.gameState.bullets.filter(this.filterNotOutside);
    }

    filterNotOutside(component: Component) {
        return component.tag != 'remove';
    }

    isOutside(component: Component) {
        return Math.abs(component.x) + BORDER > MAX_X || Math.abs(component.y) + BORDER > MAX_Y;
    }

    rotate(component: Component) {
        let rotation = ROTATION_SPEED * DELTA;
        component.toRotate -= rotation;
        component.rotation += rotation;
    }

    move(component: Component, speed: number) {
        let radians = (Math.PI / 180) * component.rotation;
        let x = Math.sin(radians) * speed * DELTA;
        let y = Math.cos(radians) * speed * DELTA;

        component.x += x;
        component.y += y;
    }

    createLuchador(): Luchador {
        let result: Luchador = this.addComponentRandomData(new Luchador()) as Luchador;
        result.gunRotation = Math.random() * MAX_ROTATION;
        return result;
    }

    createBullet(luchador:Luchador): Bullet {
        let result: Bullet = new Bullet();
        result.x = luchador.x;
        result.y = luchador.y;
        result.rotation = luchador.rotation;
        result.id = RandomGameState.getComponentId();
        return result;
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
