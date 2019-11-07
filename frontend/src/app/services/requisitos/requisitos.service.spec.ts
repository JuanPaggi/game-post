import { TestBed } from '@angular/core/testing';

import { RequisitosService } from './requisitos.service';

describe('RequisitosService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RequisitosService = TestBed.get(RequisitosService);
    expect(service).toBeTruthy();
  });
});
