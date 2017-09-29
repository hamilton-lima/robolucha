export class Component {
    id: string;
    x: number;
    y: number;
    rotation: number;
    tag: string;
    toRotate: number = 0;
    lastBullet: number = 0;
}

export class Luchador extends Component {
    gunRotation: number;
}

export class Bullet extends Component {
}

export class GameState {
    luchadores:Array<Luchador> = [];
    bullets: Array<Bullet> = [];
}
