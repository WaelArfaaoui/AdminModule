import { TestBed } from '@angular/core/testing';

import { GuardService } from './guard.service';
import {HttpClientModule} from "@angular/common/http";
import {InterceptorService} from "../interceptor/interceptor.service";

describe('GuardService', () => {
  let service: GuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [GuardService]
    });
    service = TestBed.inject(GuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
  /*it('should check validity of the token', () => {
    service.canActivate("")

  });*/
});
