import { TestBed } from '@angular/core/testing';

import { NoticiasService } from './noticias/noticias.service';

describe('NoticiasService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NoticiasService = TestBed.get(NoticiasService);
    expect(service).toBeTruthy();
  });
});
