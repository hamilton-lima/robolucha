import { AnimatedBox } from './animatedbox';
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
  private box: AnimatedBox;
  
  constructor() { }

  ngOnInit() {
    this.engine = new BABYLON.Engine(this.canvas.nativeElement, true);
    this.createScene();
    this.render();
    this.addBoxes();
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


    // create a built-in "ground" shape
    let ground = BABYLON.MeshBuilder.CreateGround('ground1',
      { width: 10, height: 10, subdivisions: 8 }, this.scene);

      let material = new BABYLON.StandardMaterial("ground-material" , this.scene);
      material.diffuseColor =  BABYLON.Color3.Random(); 
      ground.material = material;
 
    }

  render(): void {
    // run the render loop
    this.engine.runRenderLoop(() => {
      this.scene.render();
    });
  }

  addBoxes(){
    this.box = new AnimatedBox(this.scene, 1,0.5,1);
  }

  moveBox(amount:number){
    this.box.addX(amount);
  }

}

