import { Subscription } from 'rxjs/Rx';
import { GameState } from './../../models/gamestate.model';
import { GameStateService } from '../../services/gamestate.service';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Luchador3D } from 'app/game-arena/luchador.3d';
import * as BABYLON from 'babylonjs';

@Component({
  selector: 'app-game-arena',
  templateUrl: './game-arena.component.html',
  styleUrls: ['./game-arena.component.css']
})
export class GameArenaComponent implements OnInit, OnDestroy {

  @ViewChild('game') canvas;
  private GROUND_SIZE = 100;

  state: GameState;
  subscription: Subscription;

  private engine: BABYLON.Engine;
  private scene: BABYLON.Scene;
  private camera1: BABYLON.ArcRotateCamera;
  private light1: BABYLON.Light;
  private light2: BABYLON.Light;

  private luchadores: Map<string, Luchador3D>;
  //private bullets: Map<string,Luchador3D>;

  constructor(private service: GameStateService) {
    this.luchadores = new Map<string, Luchador3D>();
    //   this.bullets = new Map<string,Bullerts3D>();

  }

  ngOnInit() {
    this.engine = new BABYLON.Engine(this.canvas.nativeElement, true);
    this.createScene();
    this.render();

    this.subscription = this.service.onMessage.subscribe((state) => {
      this.update(state);
    });
  }

  public ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  createScene(): void {
    // create a basic BJS Scene object
    this.scene = new BABYLON.Scene(this.engine);

    this.camera1 = new BABYLON.ArcRotateCamera("camera1", 0, 0, 0,
      new BABYLON.Vector3(0, 80, -100), this.scene);

    // target the camera to scene origin
    this.camera1.setTarget(BABYLON.Vector3.Zero());

    // attach the camera to the canvas
    this.camera1.attachControl(this.canvas.nativeElement, false);

    // create a basic light, aiming 0,1,0 - meaning, to the sky
    this.light1 = new BABYLON.HemisphericLight('light1', new BABYLON.Vector3(0, 20, 0), this.scene);

    // create a built-in "ground" shape
    let ground = BABYLON.MeshBuilder.CreateGround('ground1',
      { width: this.GROUND_SIZE, height: this.GROUND_SIZE, subdivisions: 8 }, this.scene);

    let material = new BABYLON.StandardMaterial("ground-material", this.scene);
    material.diffuseColor = BABYLON.Color3.FromHexString('#2C401B'); // BABYLON.Color3.Random(); 
    ground.material = material;

  }

  render(): void {
    // run the render loop
    this.engine.runRenderLoop(() => {
      this.scene.render();
    });
  }

  update(state: GameState) {

    state.luchadores.forEach((luchador) => {
      if (!this.luchadores.has(luchador.id)) {
        let add = new Luchador3D(this.scene, luchador);
        this.luchadores.set(luchador.id, add);
      } else {
        this.luchadores.get(luchador.id).update(luchador);
      }

    });
  }

}
