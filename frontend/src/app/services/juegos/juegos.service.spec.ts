import { TestBed } from '@angular/core/testing';

import { JuegosService } from './juegos.service';

describe('JuegosService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: JuegosService = TestBed.get(JuegosService);
    expect(service).toBeTruthy();
  });
});
