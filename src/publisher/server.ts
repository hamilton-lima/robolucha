import * as express from 'express';
import * as http from 'http';
import * as socketIo from 'socket.io';

import { ServerPlugin } from './models/server.plugin';
import { PluginManager } from './plugin.manager';
import { Message } from './models/message.model';
import { Subject } from 'rxjs/Subject';

export class Server {
    static readonly instance: Server = new Server();
    static getInstance(){
        return Server.instance;
    }

    readonly input: Subject<Message> = new Subject<Message>();
    readonly output: Subject<Message> = new Subject<Message>();

    private app: any;
    private server: any;
    private port: string;
    private io: any;
    private pluginManager: PluginManager;
    
    constructor() {
        this.app = express();
        this.server = http.createServer(this.app);
        this.port = process.env.PORT || '3000';
        this.io = socketIo(this.server);
        this.pluginManager = new PluginManager();
    }

    run() {
        this.pluginManager.getPlugins().forEach((plugin:ServerPlugin)=>{
            plugin.setup(this.input, this.output);
        });

        this.server.listen(this.port, () => {
            console.log('Running server on port %s', this.port);
        });

        this.io.on('connect', (socket: any) => {
            console.log('Connected client on port %s.', this.port);

            socket.on('message', (message: Message) => {
                this.input.next(message);
                console.log('[server](message): %s', JSON.stringify(message));
            });

            let subscription = this.output.subscribe((message: Message) => {
                this.io.emit('message', message);
            });

            socket.on('disconnect', () => {
                console.log('Client disconnected');
                if (subscription) {
                    subscription.unsubscribe();
                }
            });
        });
    }
}

export default Server.getInstance();
