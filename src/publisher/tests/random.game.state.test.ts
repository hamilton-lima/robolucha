import { RandomGameState } from '../plugins/random.game.state';
import { GameState } from './../models/gamestate.model';
import { Message } from './../models/message.model';
import { Subject } from 'rxjs/Subject';
import { EchoPlugin } from '../plugins/echo.plugin';
import { expect } from 'chai';

import * as server from '../server';
import * as mocha from 'mocha';

let isValidLuchador = (luchador)=>{
  expect(luchador.x).to.be.greaterThan(0);
  expect(luchador.y).to.be.greaterThan(0);
  expect(luchador.rotation).to.be.greaterThan(0);
  expect(luchador.gunRotation).to.be.greaterThan(0);
  
  expect(luchador.x).to.be.not.null;
  expect(luchador.y).to.be.not.null;
  expect(luchador.rotation).to.be.not.null;
  expect(luchador.gunRotation).to.be.not.null;
}

let isValidBullet = (bullet)=>{
  expect(bullet.x).to.be.greaterThan(0);
  expect(bullet.y).to.be.greaterThan(0);
  expect(bullet.rotation).to.be.greaterThan(0);
  
  expect(bullet.x).to.be.not.null;
  expect(bullet.y).to.be.not.null;
  expect(bullet.rotation).to.be.not.null;
}

describe('Random GameState plugin', () => {

  it('should build with the correct name', () => {
    let random = new RandomGameState();
    expect(random.name).to.equal('random-game');
  });

  it('should generate luchador with valid state', () => {
    let random = new RandomGameState();
    let luchador = random.createLuchador();
    isValidLuchador(luchador);
  });

  it('should generate bullet with valid state', () => {
    let random = new RandomGameState();
    let bullet = random.createLuchador();
    isValidBullet(bullet);
  });

  it('should move luchador and keep valid', () => {
    let random = new RandomGameState();
    let luchador = random.createLuchador();
    random.move(luchador);
    isValidLuchador(luchador);
  });

  it('should move bullet and keep valid', () => {
    let random = new RandomGameState();
    let bullet = random.createLuchador();
    random.move(bullet);
    isValidBullet(bullet);
  });
  
});