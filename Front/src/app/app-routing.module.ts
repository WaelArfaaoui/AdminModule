import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppLayoutComponent } from './layout/app.layout.component';
import {AllUsersComponent} from "./components/all-users/all-users.component";
import {NewRuleComponent} from "./components/new-rule/new-rule.component";

import {DashbordComponent} from "./components/dashbord/dashbord.component";

import {LoginComponent} from "./pages/login/login.component";
import {AllRulesComponent} from "./components/all-rules/all-rules.component";
import {AddUserComponent} from "./components/add-user/add-user.component";
import {GuardService} from "./services/gurad/guard.service";


const routes: Routes = [
  {
    path: 'signIn',
    component: LoginComponent
  },
  {
    path: '',
    component: AppLayoutComponent , canActivate: [GuardService],
    children: [
      { path: '', component: DashbordComponent },
      { path: 'all', component: AllUsersComponent },
      { path: 'users', component: AllUsersComponent } ,
      { path: 'addrule', component: NewRuleComponent },
      { path: 'rules', component: AllRulesComponent } ,
      { path: 'adduser', component: AddUserComponent },



    ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
