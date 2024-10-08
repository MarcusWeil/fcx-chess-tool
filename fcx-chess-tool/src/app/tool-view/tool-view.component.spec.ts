import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToolViewComponent } from './tool-view.component';

describe('ToolViewComponent', () => {
  let component: ToolViewComponent;
  let fixture: ComponentFixture<ToolViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ToolViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ToolViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
