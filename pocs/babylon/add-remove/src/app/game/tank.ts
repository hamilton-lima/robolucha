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
    private material: BABYLON.StandardMaterial;

    constructor(scene: BABYLON.Scene, x: number, y: number, z: number) {

        this.material = new BABYLON.StandardMaterial("material", scene);
        this.material.diffuseColor = BABYLON.Color3.FromHexString('#FAA21D');

        this.parent = BABYLON.Mesh.CreateBox('parent', 1, scene);
        this.parent.isVisible = false;
        this.parent.position.x = x;
        this.parent.position.y = y;
        this.parent.position.z = z;
        this.parent.scaling = new BABYLON.Vector3(0.1, 0.1, 0.1);

        this.scene = scene;
        let self = this;

        BABYLON.SceneLoader.ImportMesh("", "assets/", "cartoon_tank.babylon", scene, function (newMeshes, particleSystems) {
            console.log('[Tank] imported messhes', newMeshes);
            self.meshes = newMeshes;

            newMeshes.forEach((mesh) => {
                mesh.parent = self.parent;
                mesh.material = self.material;
            });
        });
    }

    getId(){
        return this.parent.uniqueId;
    }

    moveX(value: number) {
        this.animate(this.parent, "position.x", this.parent.position.x, this.parent.position.x + value);
    }

    moveY(value: number) {
        this.animate(this.parent, "position.y", this.parent.position.y, this.parent.position.y + value);
    }

    moveZ(value: number) {
        this.animate(this.parent, "position.z", this.parent.position.z, this.parent.position.z + value);
    }

    rotate(value: number) {
        this.animate(this.parent, "rotation.y", this.parent.rotation.y, this.parent.rotation.y + value);
    }

    fadeOut(speed: number = 2.5) {
        this.meshes.forEach((mesh)=>{
            this.animate(mesh, "visibility", 1, 0, speed);
        });
    }

    animate(mesh: BABYLON.AbstractMesh, attribute: string, source: number, target: number, speed = this.speed) {
        BABYLON.Vector3
        let animationBox = new BABYLON.Animation("movex", attribute, SharedConstants.FPS,
            BABYLON.Animation.ANIMATIONTYPE_FLOAT,
            BABYLON.Animation.ANIMATIONLOOPMODE_CYCLE);

        let amount = Math.abs(target - source);
        let lastFrame = (amount / speed) * SharedConstants.FPS;

        var keys = [
            { frame: 0, value: source },
            { frame: lastFrame, value: target },
        ];

        animationBox.setKeys(keys);

        mesh.animations = [];
        mesh.animations.push(animationBox);
        this.scene.beginAnimation(mesh, 0, lastFrame, false);
    }

}