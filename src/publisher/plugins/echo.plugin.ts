import { Message } from '../models/message.model';
import { Subject } from 'rxjs/Subject';
import { ServerPlugin } from '../models/server.plugin';

export class EchoPlugin extends ServerPlugin{
    constructor(){
       super('echo'); 
    }

    public setup(input: Subject<Message>, output: Subject<Message>) {
        input.subscribe((message:Message)=>{
            message.type = this.name;
            output.next(message);
        });
    }
}

export default new EchoPlugin();
