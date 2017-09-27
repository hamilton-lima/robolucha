import { Message } from './../models/message.model';
import { Subject } from 'rxjs/Subject';
import { EchoPlugin } from '../plugins/echo.plugin';
import { expect } from 'chai';

import * as server from '../server';
import * as mocha from 'mocha';

describe('Echo plugin', () => {

  before((done) => {
    server.default.run();
    done();
  });

  it('should build with the correct name', () => {
    let echo = new EchoPlugin();
    expect(echo.name).to.equal('echo-plugin');
  });

  it('should send message correctly', (done) => {
    let original = 'foo.bar';
    let updated = 'foo.bar=ECHO';

    // check if echo plugin updated the message
    server.default.output.subscribe((message) => {
      expect(message.data).to.equal(updated);
      done();
    });

    let message = new Message();
    message.data = original;
    // simulate an arriving message
    server.default.input.next(message);
  });
  
});