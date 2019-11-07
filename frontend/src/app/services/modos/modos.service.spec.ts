import { TestBed } from '@angular/core/testing';

import { ModosService } from './modos.service';

describe('ModosService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ModosService = TestBed.get(ModosService);
    expect(service).toBeTruthy();
  });
});
