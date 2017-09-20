import { SharedConstants } from './shared.constants';

import * as BABYLON from 'babylonjs';

export class AnimatedBox {

    static lastBox: number = 1;
    static getName(): string {
        return 'box' + AnimatedBox.lastBox++;
    }

    // speed in units per second
    private speed: number = 5;
    private mesh: BABYLON.Mesh;
    private scene: BABYLON.Scene;

    constructor(scene: BABYLON.Scene, x: number, y: number, z: number) {
        
        this.mesh = BABYLON.MeshBuilder.CreateBox(AnimatedBox.getName(),
            { size: 1 }, scene);

        let material = new BABYLON.StandardMaterial("material" + this.mesh.name, scene);
        material.diffuseColor = new BABYLON.Color3(1, 0, 0);

        this.mesh.material = material;

        this.scene = scene;
        this.mesh.position.x = x;
        this.mesh.position.y = y;
        this.mesh.position.z = z;
    }

    // defin
    addX(x: number) {
        BABYLON.Vector3
        let animationBox = new BABYLON.Animation("movex", "position.x", SharedConstants.FPS,
            BABYLON.Animation.ANIMATIONTYPE_FLOAT,
            BABYLON.Animation.ANIMATIONLOOPMODE_CYCLE);

        let lastFrame = Math.abs( (x / this.speed) * SharedConstants.FPS );
        console.log('[AnimatedBox] lastFrame', lastFrame );

        var keys = [
            { frame: 0, value: this.mesh.position.x },
            { frame: lastFrame, value: this.mesh.position.x + x },
        ];

        animationBox.setKeys(keys);
        this.mesh.animations = [];
        this.mesh.animations.push(animationBox);

        this.scene.beginAnimation(this.mesh, 0, lastFrame, false);
    }
}