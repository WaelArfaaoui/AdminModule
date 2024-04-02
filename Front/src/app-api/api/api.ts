export * from './authenticationController.service';
import { AuthenticationControllerService } from './authenticationController.service';
export * from './userController.service';
import { UserControllerService } from './userController.service';
export const APIS = [AuthenticationControllerService, UserControllerService];
