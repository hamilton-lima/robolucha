import { SharedConstants } from './shared.constants';

import * as BABYLON from 'babylonjs';

export class AnimatedBox {

    static readonly SIZE = 3;
    static lastBox: number = 1;
    static getName(): string {
        return 'box' + AnimatedBox.lastBox++;
    }

    // speed in units per second
    private speed: number = 5;
    private mesh: BABYLON.Mesh;
    private scene: BABYLON.Scene;

    constructor(scene: BABYLON.Scene, x: number, y: number, z: number) {

        var mat0 = new BABYLON.StandardMaterial("mat0", scene);
        mat0.ambientTexture = new BABYLON.Texture("assets/texture6.png", scene);

        var faceUV = new Array(6);

        let height = 2;
        let width = 3;

        // u is the ratio of the image width, between 0 and 1
        // V is the ratio of the image height, between 0 and 1

        let u = 1 / width;
        let v = 1 / height;

        let sprite1 = new BABYLON.Vector4(0, v, u, 2 * v);
        let sprite2 = new BABYLON.Vector4(u, v, 2 * u, 2 * v);
        let sprite3 = new BABYLON.Vector4(2 * u, v, 3 * u, 2 * v);

        let sprite4 = new BABYLON.Vector4(0, 0, u, v);
        let sprite5 = new BABYLON.Vector4(u, 0, 2 * u, v);
        let sprite6 = new BABYLON.Vector4(2 * u, 0, 3 * u, v);

        faceUV = [sprite1, sprite2, sprite3, sprite4, sprite5, sprite6];

        var options = {
            size: AnimatedBox.SIZE,
            faceUV: faceUV
        };

        this.mesh = BABYLON.MeshBuilder.CreateBox(AnimatedBox.getName(),
            options, scene);

        this.mesh.material = mat0;

        let half = AnimatedBox.SIZE / 2;
        this.scene = scene;
        this.mesh.position.x = x - half;
        this.mesh.position.y = y;
        this.mesh.position.z = z - half;
    }

}