import { SharedConstants } from './shared.constants';

import * as BABYLON from 'babylonjs';

export class Tank {

    static lastBox: number = 1;
    static getName(): string {
        return 'box' + Tank.lastBox++;
    }

    // speed in units per second
    private speed: number = 5;
    private meshes: Array<BABYLON.AbstractMesh> = [];
    private parent: BABYLON.Mesh;
    private scene: BABYLON.Scene;

    constructor(scene: BABYLON.Scene, x: number, y: number, z: number) {

        let material = new BABYLON.StandardMaterial("material", scene);
        material.diffuseColor = BABYLON.Color3.FromHexString('#FAA21D');

        this.parent = BABYLON.Mesh.CreateBox('parent', 1, scene);
        this.parent.isVisible = false;
        this.parent.position.x = x;
        this.parent.position.y = y;
        this.parent.position.z = z;
        this.parent.scaling = new BABYLON.Vector3(0.1, 0.1, 0.1);
        let self = this;

        BABYLON.SceneLoader.ImportMesh("", "assets/", "cartoon_tank.babylon", scene, function (newMeshes, particleSystems) {
            console.log('[Tank] imported messhes', newMeshes);
            self.meshes = newMeshes;

            newMeshes.forEach((mesh) => {
                mesh.parent = self.parent;
                mesh.material = material;
            });
        });

        this.scene = scene;
    }

    moveX(value: number) {
        this.animate("position.x", this.parent.position.x, this.parent.position.x + value);
    }

    moveY(value: number) {
        this.animate("position.y", this.parent.position.y, this.parent.position.y + value);
    }

    moveZ(value: number) {
        this.animate("position.z", this.parent.position.z, this.parent.position.z + value);
    }

    rotate(value: number) {
        this.animate("rotation.y", this.parent.rotation.y, this.parent.rotation.y + value);
    }

    animate(attribute: string, source: number, target: number) {
        BABYLON.Vector3
        let animationBox = new BABYLON.Animation("movex", attribute, SharedConstants.FPS,
            BABYLON.Animation.ANIMATIONTYPE_FLOAT,
            BABYLON.Animation.ANIMATIONLOOPMODE_CYCLE);

        let amount = Math.abs(target - source);
        let lastFrame = (amount / this.speed) * SharedConstants.FPS;

        var keys = [
            { frame: 0, value: source },
            { frame: lastFrame, value: target },
        ];

        animationBox.setKeys(keys);
        this.parent.animations = [];
        this.parent.animations.push(animationBox);
        this.scene.beginAnimation(this.parent, 0, lastFrame, false);
    }

}