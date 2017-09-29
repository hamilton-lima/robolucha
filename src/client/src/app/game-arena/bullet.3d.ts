
import { Luchador, Bullet } from './../../models/gamestate.model';
import { SharedConstants } from '../../models/shared.constants';

import * as BABYLON from 'babylonjs';

const SIZE = 2;
const HALF = SIZE / 2;

export class Bullet3D {

    id: string;
    private mesh: BABYLON.Mesh;
    private scene: BABYLON.Scene;

    constructor(scene: BABYLON.Scene, source: Bullet) {
        this.id = source.id;

        let material = new BABYLON.StandardMaterial("bullet-material" , this.scene);
        material.diffuseColor = BABYLON.Color3.FromHexString('#FFFFFF'); 
        
        this.mesh = BABYLON.MeshBuilder.CreateSphere("sphere", {diameter: SIZE}, scene);
        this.mesh.material = material;

        this.scene = scene;
        this.mesh.position.y = HALF;
        
        this.update(source);
    }

    update(source: Bullet) {
        this.mesh.position.x = source.x - HALF;
        this.mesh.position.z = source.y - HALF;
   }

   dispose(){
       this.scene.removeMesh(this.mesh);
       this.mesh.dispose();
   }

}