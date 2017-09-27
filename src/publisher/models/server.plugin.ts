import { Message } from '../models/message.model';
import { Subject } from 'rxjs/Subject';

export abstract class ServerPlugin {
    name:string;

    abstract setup(input: Subject<Message>, output: Subject<Message>);
    
    constructor(name: string){
        this.name = name;
    }
}
