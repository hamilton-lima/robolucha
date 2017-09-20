import { Component, OnInit, ViewChild } from '@angular/core';
import * as BABYLON from 'babylonjs';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
  @ViewChild('game') canvas;

  private engine: BABYLON.Engine;
  private scene: BABYLON.Scene;
  private camera: BABYLON.FreeCamera;
  private light: BABYLON.Light;

  constructor() { }

  ngOnInit() {
    this.engine = new BABYLON.Engine(this.canvas.nativeElement, true);
    this.createScene();
    this.animate();
  }

  createScene(): void {
    // create a basic BJS Scene object
    this.scene = new BABYLON.Scene(this.engine);

    // create a FreeCamera, and set its position to (x:0, y:5, z:-10)
    this.camera = new BABYLON.FreeCamera('camera1', new BABYLON.Vector3(0, 5, -10), this.scene);

    // target the camera to scene origin
    this.camera.setTarget(BABYLON.Vector3.Zero());

    // attach the camera to the canvas
    this.camera.attachControl(this.canvas.nativeElement, false);

    // create a basic light, aiming 0,1,0 - meaning, to the sky
    this.light = new BABYLON.HemisphericLight('light1', new BABYLON.Vector3(0, 1, 0), this.scene);

    // create a built-in "sphere" shape; with 16 segments and diameter of 2
    let sphere = BABYLON.MeshBuilder.CreateSphere('sphere1',
      { segments: 16, diameter: 2 }, this.scene);

    // move the sphere upward 1/2 of its height
    sphere.position.y = 1;

    // create a built-in "sphere" shape; with 16 segments and diameter of 2
    let cube = BABYLON.MeshBuilder.CreateBox('box1',
    { size: 1 }, this.scene);
    cube.position.x = 2;
    cube.position.y = 1;

    // create a built-in "ground" shape
    let ground = BABYLON.MeshBuilder.CreateGround('ground1',
      { width: 6, height: 6, subdivisions: 2 }, this.scene);
  }

  animate(): void {
    // run the render loop
    this.engine.runRenderLoop(() => {
      this.scene.render();
    });
  }

}

