import { ServerPlugin } from './models/server.plugin';

export class PluginManager{
    private plugins: Array<ServerPlugin> = [];

    constructor(){
        try {
            let pluginlist:Array<String> = require('./plugins.json');
            pluginlist.forEach((pluginName:string) => {
                let Def = require('./plugins/' + pluginName + '.ts');
                let plugin = Def.default;
                
                console.log('=== plugin loaded', plugin.name);
                this.plugins.push(plugin);
            });
            
        } catch(error){
            console.error('Error loading plugins.json file', error);
        }
    }

    getPlugins(){
        return this.plugins;
    }
}