import { TestBed } from '@angular/core/testing';

import { Polaflix } from './polaflix';

describe('Polaflix', () => {
  let service: Polaflix;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Polaflix);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
