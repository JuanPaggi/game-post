import { TestBed } from '@angular/core/testing';

import { AnalisisService } from './analisis.service';

describe('AnalisisService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AnalisisService = TestBed.get(AnalisisService);
    expect(service).toBeTruthy();
  });
});
