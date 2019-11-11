import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PanelJuegosComponent } from './panel-juegos.component';

describe('PanelJuegosComponent', () => {
  let component: PanelJuegosComponent;
  let fixture: ComponentFixture<PanelJuegosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PanelJuegosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PanelJuegosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
