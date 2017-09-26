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
  private camera1: BABYLON.ArcRotateCamera;
  private light1: BABYLON.Light;  
  private light2: BABYLON.Light;

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

    this.camera1 = new BABYLON.ArcRotateCamera("camera1",  0, 0, 0, 
      new BABYLON.Vector3(0, 0, -10), this.scene);

    // target the camera to scene origin
    this.camera1.setTarget(BABYLON.Vector3.Zero());

    // attach the camera to the canvas
    this.camera1.attachControl(this.canvas.nativeElement, false);

    // create a basic light, aiming 0,1,0 - meaning, to the sky
    this.light1 = new BABYLON.HemisphericLight('light1', new BABYLON.Vector3(0, 20, 0), this.scene);
    this.light2 = new BABYLON.HemisphericLight('light2', new BABYLON.Vector3(0, -20, 0), this.scene);

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

}

