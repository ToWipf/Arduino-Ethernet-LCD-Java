import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WipfComponent } from './wipf.component';

describe('WipfComponent', () => {
  let component: WipfComponent;
  let fixture: ComponentFixture<WipfComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WipfComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WipfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
